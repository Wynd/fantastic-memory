package application.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import application.dao.DateDao;
import application.dao.EmailDao;
import application.dao.UserDao;
import application.model.User;


public class LoginService {

	
	 private Connection con;
	 
	 private LoginService(){
	        try{
	        	DBConnectionService dbcs=new DBConnectionService();
	        	con=dbcs.connect();
	        }catch(Exception e){
	        
	        }
	    
	    }
	    
	    private static final class SingletonHolder{
	        private static final LoginService INSTANCE=new LoginService();
	    } 

	    public static LoginService getInstance(){
	        return SingletonHolder.INSTANCE;
	    }
	    
	    
	    public String cryptWithMD5(String pass){
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] passBytes = pass.getBytes();
	            md.reset();
	            byte[] digested = md.digest(passBytes);
	            StringBuilder sb = new StringBuilder();
	            for(int i=0;i<digested.length;i++){
	                sb.append(Integer.toHexString(0xff & digested[i]));
	            }
	            return sb.toString();
	        } catch (NoSuchAlgorithmException ex) {
	            ex.printStackTrace();
	        }
	            return null;


	       }
	    
	    public boolean registration(String username,String pass,String email) {
	    	
	    	 UserDao userDao=new UserDao(con);
	    	 DateDao dateDao=new DateDao(con);
	         boolean rez=false;
	         try{
	             Optional<User> op=userDao.findUser(username);
	             if(!op.isPresent()){
	            	 dateDao.insertDate(LocalDateTime.now(), LocalDateTime.now());
	            	 int id_date=dateDao.returnIdDate();
	            	 pass=cryptWithMD5(pass);
	                 userDao.addUser(username,pass,email,id_date);
	                 rez=true;
	             }
	            
	         }catch (SQLException e){
	             e.printStackTrace();
	         }
	         return rez;
	    	  
	    }
	    
	    public Optional<User> login (String username,String password){
	        
	        UserDao userDao=new UserDao(con);
	        password=cryptWithMD5(password);
	        try{
	               
	        	Optional<User> op=userDao.findUser(username);
	                 if(op.isPresent()&&op.get().getPassword().equals(password)){
	                      return op;  
	                 }
	        }catch (SQLException e){
	                e.printStackTrace();
	            }
	        return Optional.empty();
	    }
	    
	    public boolean changePassword(String oldPassword,String newPassword,String username) {
	    	 UserDao userDao=new UserDao(con);
	    	 oldPassword=cryptWithMD5(oldPassword);
	    	 boolean changed=false;
	    	 try{
	    		
		        	Optional<User> op=userDao.findUser(username);
		                 if(op.isPresent()&&op.get().getPassword().equals(oldPassword)){
		                       newPassword=cryptWithMD5(newPassword);
		                       userDao.updatePassword(username, newPassword);
		                       DateDao datelDao=new DateDao(con);
		  				     datelDao.updateDate(LocalDateTime.now(), op.get().getId_date());
		                 }
		        }catch (SQLException e){
		                e.printStackTrace();
		            }
		        return changed;
		 }
	    
	    public void changeEmail(String email,String username) {
	    	
	    	 UserDao userDao=new UserDao(con);
		     userDao.updateEmail(username, email);
		     Optional<User> user;
			try {
				user = userDao.findUser(username);
				 if(user.isPresent()) {
				     DateDao datelDao=new DateDao(con);
				     datelDao.updateDate(LocalDateTime.now(), user.get().getId_date());}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		    
		           
		 }
}

