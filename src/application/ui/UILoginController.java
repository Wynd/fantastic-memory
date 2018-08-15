package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.User;
import application.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UILoginController implements Initializable
{

    @FXML
    private AnchorPane paneLoginRoot;

    @FXML
    private TextField fieldUsername;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private Button buttonRegister, buttonLogin;

    @FXML
    private Label labelLoginError;
    
	public void initialize(URL url, ResourceBundle bundle) 
	{
		labelLoginError.setVisible(false);
	}
	
	@FXML
	public void handleLogin(ActionEvent event)
	{
		if(!this.fieldUsername.getText().isEmpty() && !this.fieldPassword.getText().isEmpty())
		{
			User usr = LoginService.getInstance().login(this.fieldUsername.getText(), this.fieldPassword.getText()).orElse(null);
			
			if(usr != null)
			{
				UIManager.instance.showScreen(buttonRegister, UIScreen.LISTSMENU);
			}
			else
			{
				this.labelLoginError.setText(UIManager.ERROR_LOGINFAILED);
				this.labelLoginError.setVisible(true);
			}
		}
		else
		{
			this.labelLoginError.setText(UIManager.ERROR_FILLOUTFIELDS);
			this.labelLoginError.setVisible(true);
		}
	}

	@FXML
	public void handleOpenRegistrationForm(ActionEvent event)
	{
		UIManager.instance.showScreen(buttonRegister, UIScreen.REGISTER);	
	}
}
