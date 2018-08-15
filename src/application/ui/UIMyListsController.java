package application.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import application.model.Note;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UIMyListsController implements Initializable
{

    @FXML
    private VBox paneListsMenu, paneListNotes;

    @FXML
    private ScrollPane scrollPaneNotes;

    @FXML
    private Text labelListTitle;

    @FXML
    private Button buttonAddNewNote;
	
    // Buttons for the dynamic menus, these will be added based on which current menu the user has open, where the user will be able to choose between different types of lists
    private Button shoppingLists = new Button("Shopping Lists"), 
    			todoLists = new Button("To Do Lists"), 
    			appointmentsLists = new Button("Appointments"), 
    	    	totakeLists = new Button("To Take Lists"), 
    			about = new Button("About"), 
    	    	logout = new Button("Logout"), 
    	    	quit = new Button("Quit"), 
    			backToMenu = new Button("Back To Menu");
    
    // List with all the buttons "queued" to be added in the list, will get cleared each time a new set of buttons will be added
    private ArrayList<Button> buttonsToAdd = new ArrayList<Button>();
    
    // List with all the notes "queued" to be added in the list, will be filled with data from the DB when the UI is initialized
    private ArrayList<Button> notesToAdd = new ArrayList<Button>();
    
    private final LinkedHashMap<Button, EventHandler<ActionEvent>> mainMenuButtons = new LinkedHashMap<Button, EventHandler<ActionEvent>>();
    private final LinkedHashMap<Button, EventHandler<ActionEvent>> shoppingListButtons = new LinkedHashMap<Button, EventHandler<ActionEvent>>();
    private final LinkedHashMap<Button, EventHandler<ActionEvent>> todoListButtons = new LinkedHashMap<Button, EventHandler<ActionEvent>>();
    private final LinkedHashMap<Button, EventHandler<ActionEvent>> appointmentsButtons = new LinkedHashMap<Button, EventHandler<ActionEvent>>();
    private final LinkedHashMap<Button, EventHandler<ActionEvent>> totakeListButtons = new LinkedHashMap<Button, EventHandler<ActionEvent>>();

	public void initialize(URL location, ResourceBundle resources) 
	{
		buttonAddNewNote.setVisible(false);
		
		// Initializing the hashmaps with the corresponding buttons for each menu
		mainMenuButtons.put(shoppingLists, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleOpenShoppingLists(e); }
		});
		mainMenuButtons.put(todoLists, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleOpenToDoLists(e); }
		});
		mainMenuButtons.put(appointmentsLists, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleOpenAppointmentsLists(e); }
		});
		mainMenuButtons.put(totakeLists, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleOpenToTakeLists(e); }
		});
		mainMenuButtons.put(about, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { /** TODO add About page handling */ }
		});
		mainMenuButtons.put(logout, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { UIManager.instance.showScreen(((Control)e.getSource()), UIScreen.LOGIN); }
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
		
		appointmentsButtons.put(new Button("Appointments"), new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { /** TODO add handling method for lists, showing each note inside paneListNotes */ }
		});
		appointmentsButtons.put(backToMenu, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleCloseLists(e); }
		});
		
		totakeListButtons.put(new Button("To Take Lists"), new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { /** TODO add handling method for lists, showing each note inside paneListNotes */ }
		});
		totakeListButtons.put(backToMenu, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleCloseLists(e); }
		});
				
		initButtons(mainMenuButtons);
	}
	
	private void initButtons(LinkedHashMap<Button, EventHandler<ActionEvent>> list) 
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
			timerNote.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
			        Parent root;
			        try 
			        {
			            root = FXMLLoader.load(getClass().getResource("UIReminderTime.fxml"));
			            Stage stage = new Stage();
			            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons8-google-alerts-100.png")));
			            stage.setTitle("Set Reminder");
			            stage.setScene(new Scene(root, 330, 250));
			            stage.show();
			        }
			        catch (Exception e) 
			        {
			            e.printStackTrace();
			        }
				}	
			});	
			
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
	public void handleOpenToTakeLists(ActionEvent event)
	{
		((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - To Take Lists ");
		
		buttonAddNewNote.setVisible(true);
		buttonsToAdd.clear();
		initButtons(totakeListButtons);
		
		labelListTitle.setText("My To Take List");
	}

	@FXML
	public void handleOpenAppointmentsLists(ActionEvent event)
	{
		((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - Appointments ");
		
		buttonAddNewNote.setVisible(true);
		buttonsToAdd.clear();
		initButtons(appointmentsButtons);
		
		labelListTitle.setText("My Appointments");
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
}
