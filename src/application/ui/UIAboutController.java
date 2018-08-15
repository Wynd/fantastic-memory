package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class UIAboutController implements Initializable
{

	public void initialize(URL url, ResourceBundle bundle) {}

    @FXML
    void handleCancel(ActionEvent event) 
    {
    	((Stage)((Control)event.getSource()).getScene().getWindow()).close();
    }
}
