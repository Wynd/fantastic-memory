package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import application.model.User;

public class UserDao {
	
	 private Connection connection;
     PreparedStatement ps1,ps2,ps3,ps4,ps5;
	public UserDao(Connection connection) {	
		this.connection=connection;
		try {
			ps1=this.connection.prepareStatement("insert into users values (null,?,?,?,?)");
			ps2=this.connection.prepareStatement("select * from users where username=?");
			ps3=this.connection.prepareStatement("update users set password=? where username=?");
			ps4=this.connection.prepareStatement("update users set email=? where username=?");
			ps5=this.connection.prepareStatement("select * from users where id=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addUser(String username,String password,String email,int id_date) {
		
		try {
			ps1.setString(1, username);
			ps1.setString(2, password);
			ps1.setString(3, email);
			ps1.setInt(4,id_date);
			ps1.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
			
	}
	
	

	public Optional<User> findUser(String username) throws SQLException{
        User user=null;
      
           ps2.setString(1,username);
           
           try(ResultSet rs=ps2.executeQuery()){
               if(rs.next()){
                  user=new User();
                   user.setId(rs.getInt(1));
                   user.setUsername(rs.getString(2));
                   user.setPassword(rs.getString(3));
                   user.setEmail(rs.getString(4));
                   user.setId_date(rs.getInt(5));
               }
           }catch(Exception ex){
               ex.printStackTrace();
           }
       return Optional.ofNullable(user);
   }
	
	
	public void updatePassword(String username,String password) {
		
		try {
			ps3.setString(1, password);
			ps3.setString(2, username);
			ps3.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void updateEmail(String username,String email) {
		
		try {
			ps4.setString(1,email);
			ps4.setString(2, username);
			ps4.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public Optional<User> findUserAfterId(int id) {
        User user=null;
      
         
           
           try {
        		   ps5.setInt(1,id);
        		   ResultSet rs=ps5.executeQuery();
               if(rs.next()){
                  user=new User();
                   user.setId(id);
                   user.setUsername(rs.getString(2));
                   user.setPassword(rs.getString(3));
                   user.setEmail(rs.getString(4));
                   user.setId_date(rs.getInt(5));
               }
           }catch(Exception ex){
               ex.printStackTrace();
           }
       return Optional.ofNullable(user);
   }
}

