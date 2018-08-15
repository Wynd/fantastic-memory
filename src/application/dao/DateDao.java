package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DateDao {

	private Connection connection;
    PreparedStatement ps1,ps2,ps3;
    
    public DateDao(Connection connection) {	
		this.connection=connection;
		try {
			ps1=this.connection.prepareStatement("insert into dates values (null,?,?)");
			ps2=this.connection.prepareStatement("update dates set updated_date=? where id=?");
			ps3=this.connection.prepareStatement("select id from dates order by created_date desc");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
    
    public void insertDate(LocalDateTime created_date,LocalDateTime updated_date) {
    	
    	try {
			ps1.setObject(1, created_date);
			ps1.setObject(2,updated_date);
			ps1.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    }
    
    public void updateDate(LocalDateTime updated_date,int id) {
    	
    	try {
			
			ps2.setObject(1,updated_date);
			ps2.setInt(2, id);
			ps2.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    public int returnIdDate() {
    	int id=0;
    	try {
			ResultSet rs=ps3.executeQuery();
			if(rs.next()) {
				
				id=rs.getInt(1);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	return id;
    }
}
