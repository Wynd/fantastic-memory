package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application 
{
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("ui/UIList.fxml"));

        Scene scene = new Scene(root, 300, 275);
        
        //primaryStage.centerOnScreen();
        primaryStage.setTitle("My Lists");
        primaryStage.setScene(scene);
        primaryStage.show();
	    
		/*try 
		{
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}*/
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
