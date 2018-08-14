package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.Note;

public class NoteDao {
	
	 private Connection connection;
     PreparedStatement ps1,ps2,ps3,ps4,ps5,ps6;
     
     public NoteDao(Connection connection) {
    	 this.connection=connection;
    	 try {
 			ps1=this.connection.prepareStatement("insert into notes values (null,?,?,?,?,?,?)");
 			ps2=this.connection.prepareStatement("select * from notes where id_user=? and id_type=?");
 			ps3=this.connection.prepareStatement("select * from notes where id_user=?  and id_type=? and checked=?");
 			ps4=this.connection.prepareStatement("delete * from notes where id=?");
 			ps5=this.connection.prepareStatement("select * from notes where id=?");
 			ps6=this.connection.prepareStatement("update notes set title=? and message=? and checked=?");
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
     }
    	 
     public void addNotes(int id_user,String title,String message,int id_type,boolean checked,int id_date) {
	    		 
	     try {
			 ps1.setInt(1, id_user);
		     ps1.setString(2, title);
		     ps1.setString(3, message);
		     ps1.setInt(4,id_type);
		     ps1.setBoolean(5, checked);
		     ps1.setInt(6, id_date);
		     ps1.executeUpdate();
		     	 
	     } catch (SQLException e) {
	 		
	 		e.printStackTrace();
	 	}
     }
     
     public List<Note> returnList(int id_user,int id_type) {
    	 List<Note> notes=new ArrayList<>();
    	 try {
			ps2.setInt(1, id_user);
			ps2.setInt(2, id_type);
			ResultSet rs=ps2.executeQuery();
			 while(rs.next())
	            {
	            int id=rs.getInt(1);
	            String title=rs.getString(3);
	            String message=rs.getString(4);
	            boolean checked=rs.getBoolean(6);
	            int id_date=rs.getInt(7);
	            Note note=new Note();
	            note.setId(id);
	            note.setId_user(id_user);
	            note.setTitle(title);
	            note.setMessage(message);
	            note.setId_type(id_type);
	            note.setChecked(checked);
	            note.setId_date(id_date);
	            notes.add(note);
	            
	            }
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	 return notes;
    	 
     }
     public List<Note> returnSelectedList(int id_user,int id_type,boolean checked) {
    	 List<Note> notes=new ArrayList<>();
    	 try {
			ps3.setInt(1, id_user);
			ps3.setInt(2, id_type);
			ps3.setBoolean(3, checked);
			ResultSet rs=ps3.executeQuery();
			 while(rs.next())
	            {
	            int id=rs.getInt(1);
	            String title=rs.getString(3);
	            String message=rs.getString(4);
	            int id_date=rs.getInt(7);
	            Note note=new Note();
	            note.setId(id);
	            note.setId_user(id_user);
	            note.setTitle(title);
	            note.setMessage(message);
	            note.setId_type(id_type);
	            note.setChecked(checked);
	            note.setId_date(id_date);
	            notes.add(note);
	            
	            }
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
    	 return notes;
    	 
     }
     
     public void deleteDate(int id) {
    	 
    	
    	 try {
    		 ps4.setInt(1, id);
			ps4.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
     }
     
     public void updateDate(String title,String message,boolean checked) {
    	 
    	 try {
    		 ps6.setString(1, title);
    		 ps6.setString(2, message);
    		 ps6.setBoolean(3, checked);
			ps6.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
     }
}
