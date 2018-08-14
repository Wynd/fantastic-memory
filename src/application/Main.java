package application;
	
import java.io.IOException;

import application.ui.UIController;
import application.ui.UIScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application 
{
	
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons8-page-50.png")));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		UIController.instance.showScreen(primaryStage, UIScreen.LOGIN);	
		
		primaryStage.show();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
