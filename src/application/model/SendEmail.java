package application.model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendEmail {

	final String senderEmailID = "-"; 
	final String senderPassword = "-"; 
	final String emailSMTPserver = "smtp.gmail.com";
	final String emailServerPort = "465"; 
	
	public SendEmail(String receiverEmailID,String Subject, String Body){ 
		// Receiver Email Address this.receiverEmailID=receiverEmailID; 
		// Subject this.emailSubject=Subject; 
		// Body this.emailBody=Body; 
		Properties props = new Properties();
		props.put("mail.smtp.user",senderEmailID);
		props.put("mail.smtp.host", emailSMTPserver); 
		props.put("mail.smtp.port", emailServerPort); 
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.socketFactory.port", emailServerPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		props.put("mail.smtp.socketFactory.fallback", "false"); 
		SecurityManager security = System.getSecurityManager();
		
		class SMTPAuthenticator extends javax.mail.Authenticator {
			public PasswordAuthentication getPasswordAuthentication() { 
				return new PasswordAuthentication(senderEmailID, senderPassword); } }
		
		try{ 
			Authenticator auth = new SMTPAuthenticator(); 
			Session session = Session.getInstance(props, auth); 
			MimeMessage msg = new MimeMessage(session);
			msg.setText(Body);
			msg.setSubject(Subject);
			msg.setFrom(new InternetAddress(senderEmailID));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailID)); 
			Transport.send(msg); 
			System.out.println("Message send Successfully:)"); } 
		catch (Exception mex){ 
			mex.printStackTrace();
		} 
	} 
}

