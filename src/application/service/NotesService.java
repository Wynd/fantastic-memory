package application.service;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import application.dao.EmailDao;
import application.dao.NoteDao;
import application.model.Note;

public class NotesService {

	
 private Connection con;
	 
	 public NotesService(){
	        try{
	        	DBConnectionService dbcs=new DBConnectionService();
	        	con=dbcs.connect();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	    
	 }
	 
	 public void addNote(int id_user,String title,String message,int id_type,boolean checked,int id_date) {
		 
		 NoteDao noteDao=new NoteDao(con);
		 noteDao.addNotes(id_user, title, message, id_type, checked, id_date);
		 
	 }
	 
	 public void deleteNote(int id) {
		 
		 NoteDao noteDao=new NoteDao(con);
		 noteDao.deleteNotes(id);
	 }
	 
	 public List<Note> getListOfNotes(int id_user,int id_type){
		 
		 NoteDao noteDao=new NoteDao(con);
		 return noteDao.returnList(id_user, id_type);
	 }
	 
	 public void changeNote(Note note) {
		 
		 NoteDao noteDao=new NoteDao(con);
		 noteDao.updateDate(note.getTitle(),note.getMessage(),note.isChecked(),note.getId());
	 }
	 
	 public void createReminder(int id_note,LocalDateTime date_scheduled ) {
		 
		 EmailDao emailDao=new EmailDao(con);
		 emailDao.addEmail(id_note, date_scheduled, false);
		 
	 }
	 
}
