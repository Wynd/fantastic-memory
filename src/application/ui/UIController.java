package application.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UIController implements Initializable
{

	public static UIController instance = new UIController();
	
    @FXML
    private AnchorPane paneLoginRoot;
	
    @FXML
    private TextField fieldUsername, fieldEmail;

    @FXML
    private PasswordField fieldPassword, fieldPasswordRepeat;

    @FXML
    private Hyperlink hyperlinkPasswordRevovery;
    
    @FXML
    private Button buttonLogin, buttonRegister, buttonCancelRegister, buttonConfirmRegister, buttonAddNewNote;

    @FXML
    private Text labelMyListsTitle;

    @FXML
    private VBox paneListsMenu;
    
    // Buttons for the initial main menu, where the user will be able to choose between different types of lists
    private Button shoppingLists, todoLists, about, backToMenu;

    // List with all the buttons "queued" to be added in the list, will get cleared each time a new set of buttons will be added
    private ArrayList<Button> buttonsToAdd = new ArrayList<Button>();
    
    private final Button[] mainMenuButtons = new Button[] 
    		{
    				shoppingLists = new Button("Shopping Lists"), 
    				todoLists = new Button("To Do Lists"), 
    				about = new Button("About")
    		};
    
    private final Button[] shoppingListButtons = new Button[] 
    		{
    				new Button("My Shopping List"), 
    				backToMenu = new Button("Back To Menu")
    		};
    
    private final Button[] toDoListButtons = new Button[] 
    		{
    				new Button("My To Do List"), 
    				backToMenu = new Button("Back To Menu")
    		};
    
	public void initialize(URL url, ResourceBundle bundle) 
	{
		String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1, url.getFile().length() );
		String uiName = fileName.substring(0, fileName.lastIndexOf('.'));

		if(uiName.equalsIgnoreCase(UIScreen.LISTSMENU.getPath()))
		{
			initButtons(mainMenuButtons);
		}
	}
	
	private void initButtons(Button[] list)
	{	
		this.paneListsMenu.getChildren().clear();
		
		try
		{
			for(Button btn : list)
			{
				btn.setFont(new Font("System", 23));
				btn.setTextFill(Paint.valueOf("#FFFFFF"));
				btn.setPrefSize(300, 70);
				btn.setMinWidth(paneListsMenu.getPrefWidth());
				btn.setAlignment(Pos.CENTER_LEFT);
				
				btn.setOnAction(new EventHandler<ActionEvent>() 
				{
		            public void handle(ActionEvent e) {handleClicks(e);}
				});
				
				this.paneListsMenu.getChildren().add(btn);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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

	@FXML
	public void handleClicks(ActionEvent event)
	{
		if(event.getSource() == buttonLogin)
		{ 
			/** TODO Verify if the account exists and if the password is correct */
			this.showScreen(buttonLogin, UIScreen.LISTSMENU);				
		}
		else if(event.getSource() == buttonRegister)
		{
			/** TODO Send data for account creation */
			this.showScreen(buttonRegister, UIScreen.REGISTER);
		}
		else if(event.getSource() == hyperlinkPasswordRevovery)
		{
			
		}
		else if(event.getSource() == buttonConfirmRegister)
		{
			
		}
		else if(event.getSource() == buttonCancelRegister)
		{
			this.showScreen(buttonCancelRegister, UIScreen.LOGIN);
		}
		else if(event.getSource() == shoppingLists)
		{
			((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - My Shopping Lists ");
			
			buttonsToAdd.clear();
			initButtons(shoppingListButtons);
		}
		else if(event.getSource() == todoLists)
		{
			((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - My To Do Lists ");
			
			buttonsToAdd.clear();
			initButtons(toDoListButtons);
		}
		else if(event.getSource() == backToMenu)
		{
			System.out.println("@@@");
			((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - My Lists ");
			
			buttonsToAdd.clear();
			initButtons(mainMenuButtons);
		}
	}

}
