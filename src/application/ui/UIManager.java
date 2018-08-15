package application.ui;

import application.model.Note;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UIManager 
{
	public static UIManager instance = new UIManager();
	
    public static final String ERROR_FILLOUTFIELDS = "Please fill out all the fields";
    public static final String ERROR_LOGINFAILED = "Incorrect username or password";
    public static final String ERROR_DIFFERENTREPEATPASSWORD = "'Repeat Password' must match 'Password'";
    public static final String ERROR_ACCOUNTOWNED = "An account with this username already exists !";
	
    //Used for reminders to set them for a specific note, will be set to null when UIReminderTime window is closed
    private Note currentlySelectedNote;
    
    private int typeSelected = 0;
    
    private int userId = -1;
    
    public void setNoteForReminder(Note note)
    {
    	this.currentlySelectedNote = note;
    }
    
    public Note getNoteForReminder(Note note)
    {
    	return this.currentlySelectedNote;
    }
    
    public void setTypeForNotes(int typeId)
    {
    	this.typeSelected = typeId;
    }
    
    public int getTypeForNotes()
    {
    	return this.typeSelected;
    }
    
    public void setUserId(int id)
    {
    	this.userId = id;
    }
    
    public int getUserId()
    {
    	return this.userId;
    }
    
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
	
	public Button createButtonWithImage(int layoutX, int layoutY, Image img, int fitWidth, int fitHeight, ImageView genericImage, int sizeWidth, int sizeHeight)
	{
		Button genericBtn = new Button();
		genericBtn.setPrefSize(sizeWidth, sizeHeight);
		genericBtn.setLayoutX(layoutX);
		genericBtn.setLayoutY(layoutY);
		genericImage.setFitWidth(fitWidth);
		genericImage.setFitHeight(fitHeight);
		try
		{
			genericImage.setImage(img);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		genericBtn.setGraphic(genericImage);
		
		return genericBtn;
	}
}
