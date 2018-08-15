package application.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class UIManager 
{
	public static UIManager instance = new UIManager();
	
    public static final String ERROR_FILLOUTFIELDS = "Please fill out all the fields";
    public static final String ERROR_LOGINFAILED = "Incorrect username or password";
    public static final String ERROR_DIFFERENTREPEATPASSWORD = "'Repeat Password' must match 'Password'";
    public static final String ERROR_ACCOUNTOWNED = "An account with this username already exists !";
	
	public void showScreen(Control child, UIScreen ui)
	{
		showScreen((Stage) child.getScene().getWindow(), ui);
	}
	
	public void showScreen(Stage stage, UIScreen ui)
	{
		try 
		{
			Parent rootUI = FXMLLoader.load(getClass().getResource(ui.getPath() + ".fxml"));
						
			Scene scene = new Scene(rootUI);
			
	        stage.setTitle(ui.getTitle());
			stage.setScene(scene);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
