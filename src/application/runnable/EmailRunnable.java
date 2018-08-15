package application.runnable;

import java.util.List;

import application.model.Email;
import application.service.EmailsService;

public class EmailRunnable implements Runnable {

	@Override
	public void run() {
		EmailsService es=new EmailsService();
		List<Email> list=es.getUnsentEmails();
		for(Email e : list) {
			es.sendEmail(e.getId());	
		}
		es.deleteEmails();
		
		
	}

}
