package application.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import application.model.Note;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
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
    private Text labelMyListsTitle, labelListTitle;

    @FXML
    private VBox paneListsMenu, paneListNotes;
    
    // Buttons for the dynamic menus, these will be added based on which current menu the user has open, where the user will be able to choose between different types of lists
    private Button shoppingLists = new Button("Shopping Lists"), 
    			todoLists = new Button("To Do Lists"), 
    			about = new Button("About"), 
    	    	quit = new Button("Quit"), 
    			backToMenu = new Button("Back To Menu");

    // List with all the buttons "queued" to be added in the list, will get cleared each time a new set of buttons will be added
    private ArrayList<Button> buttonsToAdd = new ArrayList<Button>();
    
    private final LinkedHashMap<Button, EventHandler<ActionEvent>> mainMenuButtons = new LinkedHashMap<Button, EventHandler<ActionEvent>>();
    private final LinkedHashMap<Button, EventHandler<ActionEvent>> shoppingListButtons = new LinkedHashMap<Button, EventHandler<ActionEvent>>();
    private final LinkedHashMap<Button, EventHandler<ActionEvent>> todoListButtons = new LinkedHashMap<Button, EventHandler<ActionEvent>>();

    
	public void initialize(URL url, ResourceBundle bundle) 
	{
		String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1, url.getFile().length() );
		String uiName = fileName.substring(0, fileName.lastIndexOf('.'));
		
		// Initializing the hashmaps with the corresponding buttons for each menu
		mainMenuButtons.put(shoppingLists, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleOpenShoppingLists(e); }
		});
		mainMenuButtons.put(todoLists, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleOpenToDoLists(e); }
		});
		mainMenuButtons.put(about, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { /** TODO add About page handling */ }
		});
		mainMenuButtons.put(quit, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { Platform.exit(); }
		});
		
		shoppingListButtons.put(new Button("My Shopping List"), new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { /** TODO add handling method for lists, showing each note inside paneListNotes */ }
		});
		shoppingListButtons.put(backToMenu, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleCloseLists(e); }
		});
		
		todoListButtons.put(new Button("My To Do List"), new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { /** TODO add handling method for lists, showing each note inside paneListNotes */ }
		});
		todoListButtons.put(backToMenu, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleCloseLists(e); }
		});
		
		if(uiName.equalsIgnoreCase(UIScreen.LISTSMENU.getPath()))
		{		
			buttonAddNewNote.setVisible(false);
			
			initButtons(mainMenuButtons);
		}
	}
	
	private void initButtons(HashMap<Button, EventHandler<ActionEvent>> list)
	{
		this.paneListsMenu.getChildren().clear();
		
		try
		{
			for(Entry<Button, EventHandler<ActionEvent>> entry : list.entrySet())
			{
				entry.getKey().setFont(new Font("System", 23));
				entry.getKey().setTextFill(Paint.valueOf("#FFFFFF"));
				entry.getKey().setPrefSize(300, 70);
				entry.getKey().setMinWidth(paneListsMenu.getPrefWidth());
				entry.getKey().setAlignment(Pos.CENTER_LEFT);
				
				entry.getKey().setOnAction(entry.getValue());
				
				this.paneListsMenu.getChildren().add(entry.getKey());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** TODO Setup a pane with different controls for title, description, additional buttons */
	private void addNote(Note note)
	{
		try
		{
			Button newNote = new Button(note.getTitle());
			
			newNote.setFont(new Font("System", 23));
			newNote.setTextFill(Paint.valueOf("#FFFFFF"));
			newNote.setPrefSize(740, 70);
			newNote.setMinWidth(paneListsMenu.getPrefWidth());
			newNote.setAlignment(Pos.CENTER_LEFT);
								
			this.paneListNotes.getChildren().add(newNote);
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
	public void handleLogin(ActionEvent event)
	{
		/** TODO Verify if the account exists and if the password is correct */
		this.showScreen(buttonLogin, UIScreen.LISTSMENU);			
	}
	
	@FXML
	public void handleConfirmRegistration(ActionEvent event)
	{

	}
	
	@FXML
	public void handleOpenRegistrationForm(ActionEvent event)
	{
		/** TODO Send data for account creation */
		this.showScreen(buttonRegister, UIScreen.REGISTER);	
	}
	
	@FXML
	public void handleCloseRegistrationForm(ActionEvent event)
	{
		this.showScreen(buttonCancelRegister, UIScreen.LOGIN);
	}
	
	@FXML
	public void handleOpenShoppingLists(ActionEvent event)
	{
		((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - My Shopping Lists ");
		
		buttonAddNewNote.setVisible(true);
		buttonsToAdd.clear();
		initButtons(shoppingListButtons);	
		
		labelListTitle.setText("My Shopping List");
	}
	
	@FXML
	public void handleOpenToDoLists(ActionEvent event)
	{
		((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - My To Do Lists ");
		
		buttonAddNewNote.setVisible(true);
		buttonsToAdd.clear();
		initButtons(todoListButtons);
		
		labelListTitle.setText("My To Do List");
	}
	
	@FXML
	public void handleCloseLists(ActionEvent event)
	{
		((Stage) backToMenu.getScene().getWindow()).setTitle("Fantastic Memory - My Lists ");
		
		buttonAddNewNote.setVisible(false);
		buttonsToAdd.clear();
		initButtons(mainMenuButtons);
		
		labelListTitle.setText("My Lists");
	}
	
	@FXML
	public void handleAddNewNote(ActionEvent event)
	{
		Note note = new Note();
		
		note.setId(1);
		note.setTitle("Example Note");
		note.setMessage("Example Message");
		
		this.addNote(note);
	}
	
	@FXML
	public void handleShowListNotes(ActionEvent event)
	{
		
	}

}
