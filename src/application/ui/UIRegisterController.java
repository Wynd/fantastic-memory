package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import application.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UIRegisterController implements Initializable
{
    @FXML
    private AnchorPane paneLoginRoot;

    @FXML
    private TextField fieldUsername, fieldEmail;

    @FXML
    private PasswordField fieldPassword, fieldPasswordRepeat;

    @FXML
    private Button buttonCancelRegister, buttonConfirmRegister;

    @FXML
    private Label labelRegError;
	
	public void initialize(URL url, ResourceBundle bundle) 
	{
		labelRegError.setVisible(false);
	}
	
	@FXML
	public void handleConfirmRegistration(ActionEvent event)
	{
		if(!this.fieldUsername.getText().isEmpty() && !this.fieldPassword.getText().isEmpty() && !this.fieldPasswordRepeat.getText().isEmpty() && !this.fieldEmail.getText().isEmpty())
		{
			if(this.fieldPassword.getText().equalsIgnoreCase(this.fieldPasswordRepeat.getText()))
			{
				boolean reg = LoginService.getInstance().registration(this.fieldUsername.getText(), this.fieldPassword.getText(), this.fieldEmail.getText());
				
				if(reg)
				{
					UIManager.instance.showScreen(buttonConfirmRegister, UIScreen.LISTSMENU);
				}
				else
				{
					this.labelRegError.setText(UIManager.ERROR_ACCOUNTOWNED);
					this.labelRegError.setVisible(true);
				}
			}
			else
			{
				this.labelRegError.setText(UIManager.ERROR_DIFFERENTREPEATPASSWORD);
				this.labelRegError.setVisible(true);
			}
		}
		else
		{
			this.labelRegError.setText(UIManager.ERROR_FILLOUTFIELDS);
			this.labelRegError.setVisible(true);
		}
	}
		
	@FXML
	public void handleCloseRegistrationForm(ActionEvent event)
	{
		UIManager.instance.showScreen(buttonCancelRegister, UIScreen.LOGIN);
	}
}
