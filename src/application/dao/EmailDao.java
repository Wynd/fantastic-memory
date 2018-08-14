package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import application.model.Email;


public class EmailDao {

	private Connection connection;
    PreparedStatement ps1,ps2,ps3,ps4,ps5;
    
    public EmailDao(Connection connection) {	
		this.connection=connection;
		try {
			ps1=this.connection.prepareStatement("insert into emails values (null,?,?,?)");
			ps2=this.connection.prepareStatement("select * from emails where date_scheduled=? and is_sent=?");
			ps3=this.connection.prepareStatement("update emails set date_scheduled=? and is_sent=? where id_note=?");
			ps4=this.connection.prepareStatement("delete from emails where id_note=?");
			ps5=this.connection.prepareStatement("delete from emails where is_sent=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
    
    public void addEmail(int id_note,LocalDateTime date_scheduled,boolean is_sent) {
    	
    	try {
    		
    		ps1.setInt(1, id_note);
    		ps1.setObject(2, date_scheduled);
    		ps1.setBoolean(3, is_sent);
    		ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public List<Email> returnEmail(LocalDateTime date_scheduled,boolean is_sent){
    	
    	List<Email> emails=new ArrayList<>();
    	try {
    		
    		ps2.setObject(1, date_scheduled);
    		ps2.setBoolean(2, is_sent);
    		ResultSet rs=ps2.executeQuery();
    		 while(rs.next()){
	            
    			 int id=rs.getInt(1);
	             int id_note=rs.getInt(1);
	             Email email=new Email();
	             email.setId(id);
	             email.setId_note(id_note);
	             email.setDate_scheduled(date_scheduled);
	             email.setIs_sent(is_sent);
	             emails.add(email);
	            
	         }
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return emails;
    	
    }
    
    public void updateEmail(LocalDateTime date_scheduled,boolean is_sent,int id_date) {
    	
    	try {
    		ps3.setObject(1, date_scheduled);
    		ps3.setBoolean(2, is_sent);
    		ps3.setInt(3, id_date);
    	
    		ps3.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    public void deleteEmail(int id_date) {
    	
    	try {
    		
    		ps4.setInt(3, id_date);
    		ps4.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
 public void deleteSentEmail() {
    	
    	try {
    		
    		ps5.setBoolean(1,true);
    		ps5.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
