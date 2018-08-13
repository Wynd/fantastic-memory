package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UIController implements Initializable
{

    @FXML
    private TextField fieldUsername;

    @FXML
    private TextField fieldPassword;

    @FXML
    private Button buttonRegister;

    @FXML
    private Button buttonLogin;
	
    
	public void initialize(URL url, ResourceBundle bundle) 
	{
		
	}
	
	public void handleClicks(ActionEvent event)
	{
		if(event.getSource() == buttonLogin)
		{
			
		}
		else if(event.getSource() == buttonRegister)
		{
			
		}
		else if(event.getSource() == fieldUsername)
		{
			
		}
		else if(event.getSource() == fieldPassword)
		{
			
		}
	}

}
