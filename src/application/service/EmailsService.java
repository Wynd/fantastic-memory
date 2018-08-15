package application.service;

import java.sql.Connection;
import java.util.Optional;

import application.dao.NoteDao;
import application.dao.UserDao;
import application.model.Note;
import application.model.SendEmail;
import application.model.User;

public class EmailsService {
	
	
	private Connection con;
	 
	 public EmailsService(){
	        try{
	        	DBConnectionService dbcs=new DBConnectionService();
	        	con=dbcs.connect();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	    
	 }
	 
	 public void sendEmail(int id){
		 
		 NoteDao noteDao=new NoteDao(con);
		 Optional<Note> note=noteDao.getSelectedNote(id);
		 if(note.isPresent()) {
			 UserDao userDao=new UserDao(con);
			 Optional<User> user=userDao.findUserAfterId(note.get().getId_user());
			 if(user.isPresent())
			 {
				String email=user.get().getEmail();
				String title="Reminder";
				String message="Dear "+user.get().getUsername()+" "
								+ "\nDo not forget about the "+note.get().getTitle()+
								" from your list!";
				SendEmail sendEmail=new SendEmail(email,title,message);
			 }
		 }
			 
			  
	 }

}
