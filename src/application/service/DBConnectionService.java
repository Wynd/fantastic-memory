package application.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionService {

	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mylistproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET";
	private static final String USERNAME ="root";
	private static final String PASSWORD ="";


	
	private Connection connection;
	private Properties properties;

	/**
	 * Functie ce va fi responsabila cu atribuirea proprietatilor conexiunii ce urmeaza a fi stabilita. Va returna un obiect de tip properties ce va contine datele folosite de catre utilizator la logare.
	 * @param - functia nu contine argumente in semnatura insa va returna obiectul creat cu username-ul si parola utilizatorului
	 * @return - un obiect de tip properties 
	 */
	private Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", USERNAME);
			properties.setProperty("password", PASSWORD);
		}
		return properties;
	}

	/**
	 * Metoda connect() - va returna un obiect de tip connection ce va fi format din obiectulul anterior 
	 * creat de tip properties ce va contine link-ul catre baza de date de tip MySQL, username-ul si
	 * parola obtinute prin preluarea din interfata grafica a continutului textField-ului corespunzator
	 * la logarea in aplicatie
	 * 
	 * 
	 * @param - niciun argument nu va fi inclus ca argument al metodei insa tipul obiectului returnat va fi Connection
	 * @return - un obiect de tip connetion care va fi atribuit si folosit ulterior in cod
	 * @throws - 2 obiecte in functie de caz, ClassNotFoundException sau SQLException
	 * 
	 */
	
	
	public Connection connect() {
		if (connection == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				connection = DriverManager.getConnection(DATABASE_URL,getProperties());
			} catch (ClassNotFoundException |SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	/**
	 * Functia disconnect - Functia va fi responsabila cu inchiderea conexiunii cu baza de date. Metoda este de clasa astfel va fi nevoie de un obiect de tip Connection pentru a o putea apela
	 * @param - none
	 * @return - nu va avea un tip de date returnat.
	 * @throws - SQLException  
	 */
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
