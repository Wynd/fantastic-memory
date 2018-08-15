package application.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.Note;
import application.model.User;
import application.service.LoginService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    
    @FXML
    private ScrollPane scrollPaneNotes;
    
    @FXML 
    private Label labelLoginError, labelRegError;
    
    // Buttons for the dynamic menus, these will be added based on which current menu the user has open, where the user will be able to choose between different types of lists
    private Button shoppingLists = new Button("Shopping Lists"), 
    			todoLists = new Button("To Do Lists"), 
    			about = new Button("About"), 
    	    	quit = new Button("Quit"), 
    			backToMenu = new Button("Back To Menu");

    private final String ERROR_FILLOUTFIELDS = "Please fill out all the fields";
    private final String ERROR_LOGINFAILED = "Incorrect username or password";
    private final String ERROR_DIFFERENTPASSWORD = "'Repeat Password' must match 'Password'";
    private final String ERROR_ACCOUNTOWNED = "An account with this username already exists !";
    
    // List with all the buttons "queued" to be added in the list, will get cleared each time a new set of buttons will be added
    private ArrayList<Button> buttonsToAdd = new ArrayList<Button>();
    
    // List with all the notes "queued" to be added in the list, will be filled with data from the DB when the UI is initialized
    private ArrayList<Button> notesToAdd = new ArrayList<Button>();
    
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
		
		if(uiName.equalsIgnoreCase(UIScreen.LOGIN.getPath()))
		{
			labelLoginError.setVisible(false);
		}
		else if(uiName.equalsIgnoreCase(UIScreen.REGISTER.getPath()))
		{
			labelRegError.setVisible(false);
		}
		else if(uiName.equalsIgnoreCase(UIScreen.LISTSMENU.getPath()))
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
	private void addNoteInList(Note note)
	{
		try
		{
			Pane newNoteRoot = new Pane();			
			newNoteRoot.setPrefSize(200, 150);
			newNoteRoot.setMinWidth(paneListNotes.getPrefWidth());
			
			Text newNoteTitle = new Text(note.getTitle());		
			newNoteTitle.setLayoutY(39);
			newNoteTitle.setFont(new Font("System", 36));
			newNoteTitle.setFill(Paint.valueOf("#FFFFFF"));
			newNoteTitle.setWrappingWidth(730);
			
			Label newNoteDesc = new Label(note.getMessage());	
			newNoteDesc.setPrefSize(500, 90);
			newNoteDesc.setLayoutY(58);
			newNoteDesc.setAlignment(Pos.TOP_LEFT);
			newNoteDesc.setFont(new Font("System", 18));
			newNoteDesc.setTextFill(Paint.valueOf("#FFFFFF"));
			newNoteDesc.setWrapText(true);
			
			TextField noteTitleEdit = new TextField(note.getTitle());
			noteTitleEdit.setPrefSize(725, 50);
			noteTitleEdit.setFont(new Font("System", 24));
			
			TextArea noteDescEdit = new TextArea(note.getMessage());
			noteDescEdit.setLayoutY(58);
			noteDescEdit.setPrefSize(500, 85);
			noteDescEdit.setFont(new Font("System", 14));
			
			noteTitleEdit.setVisible(false);
			noteDescEdit.setVisible(false);

			ImageView editNoteImage = new ImageView();
			Button editNote = createOptionButton(512, new Image(getClass().getResourceAsStream("/assets/icons8-edit-100.png")), editNoteImage);
			editNote.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					newNoteTitle.setVisible(!newNoteTitle.isVisible());
					newNoteDesc.setVisible(!newNoteDesc.isVisible());
					
					noteTitleEdit.setVisible(!noteTitleEdit.isVisible());
					noteDescEdit.setVisible(!noteDescEdit.isVisible());
					
					if(newNoteTitle.isVisible())
					{
						try
						{
							editNoteImage.setImage(new Image(getClass().getResourceAsStream("/assets/icons8-edit-100.png")));
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						try
						{
							editNoteImage.setImage(new Image(getClass().getResourceAsStream("/assets/icons8-checkmark-100.png")));
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}					
					}
					
					if(!newNoteTitle.getText().equalsIgnoreCase(noteTitleEdit.getText()))
						newNoteTitle.setText(noteTitleEdit.getText());
					
					if(!newNoteDesc.getText().equalsIgnoreCase(noteDescEdit.getText()))
						newNoteDesc.setText(noteDescEdit.getText());					
				}	
			});
			
			ImageView timerNoteImage = new ImageView();
			Button timerNote = createOptionButton(587, new Image(getClass().getResourceAsStream("/assets/icons8-google-alerts-100.png")), timerNoteImage);
			
			ImageView deleteNoteImage = new ImageView();
			Button deleteNote = createOptionButton(664, new Image(getClass().getResourceAsStream("/assets/icons8-cancel-100.png")), deleteNoteImage);			
			deleteNote.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					paneListNotes.getChildren().remove(newNoteRoot);
				}	
			});			
			
			newNoteRoot.getChildren().addAll(newNoteTitle, noteTitleEdit, newNoteDesc, noteDescEdit, editNote, timerNote, deleteNote); // noteTitleEdit, noteDescEdit
			this.paneListNotes.getChildren().add(newNoteRoot);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private Button createOptionButton(int posX, Image img, ImageView genericImage)
	{
		Button genericBtn = new Button();
		genericBtn.setPrefSize(55, 50);
		genericBtn.setLayoutX(posX);
		genericBtn.setLayoutY(70);
		genericImage.setFitWidth(45);
		genericImage.setFitHeight(50);
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
		this.showScreen(buttonRegister, UIScreen.LISTSMENU);
		/*if(!this.fieldUsername.getText().isEmpty() && !this.fieldPassword.getText().isEmpty())
		{
			User usr = LoginService.getInstance().login(this.fieldUsername.getText(), this.fieldPassword.getText()).orElse(null);
			
			if(usr != null)
			{
				this.showScreen(buttonRegister, UIScreen.LISTSMENU);
			}
			else
			{
				this.labelLoginError.setText(this.ERROR_LOGINFAILED);
				this.labelLoginError.setVisible(true);
			}
		}
		else
		{
			this.labelLoginError.setText(this.ERROR_FILLOUTFIELDS);
			this.labelLoginError.setVisible(true);
		}*/
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
					this.showScreen(buttonConfirmRegister, UIScreen.LISTSMENU);
				}
				else
				{
					this.labelRegError.setText(this.ERROR_ACCOUNTOWNED);
					this.labelRegError.setVisible(true);
				}
			}
			else
			{
				this.labelRegError.setText(this.ERROR_DIFFERENTPASSWORD);
				this.labelRegError.setVisible(true);
			}
		}
		else
		{
			this.labelRegError.setText(this.ERROR_FILLOUTFIELDS);
			this.labelRegError.setVisible(true);
		}
	}
	
	@FXML
	public void handleOpenRegistrationForm(ActionEvent event)
	{
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

		note.setTitle("Example Note");
		note.setMessage("Example Message");
		
		this.addNoteInList(note);
	}
	
	@FXML
	public void handleShowListNotes(ActionEvent event)
	{
		
	}

}
