package application;
	
import application.ui.UIManager;
import application.ui.UIScreen;
import javafx.application.Application;
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
		
		UIManager.instance.showScreen(primaryStage, UIScreen.LOGIN);	
		
		primaryStage.show();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
