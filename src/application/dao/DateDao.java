package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DateDao {

	private Connection connection;
    PreparedStatement ps1,ps2;
    
    public DateDao(Connection connection) {	
		this.connection=connection;
		try {
			ps1=this.connection.prepareStatement("insert into dates values (null,?,?)");
			ps2=this.connection.prepareStatement("update dates set updated_date=? where id=?");
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
			ps1.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
}
