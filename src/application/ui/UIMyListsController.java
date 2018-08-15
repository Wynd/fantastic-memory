package application.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import application.model.Note;
import application.model.Type;
import application.service.EmailsService;
import application.service.NotesService;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UIMyListsController implements Initializable
{
	public static ScheduledExecutorService service=Executors.newSingleThreadScheduledExecutor();

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
            public void handle(ActionEvent e) 
            { 
		        Parent root;
		        try 
		        {
		            root = FXMLLoader.load(getClass().getResource("UIAbout.fxml"));
		            Stage stage = new Stage();
		            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons8-edit-100.png")));
		            stage.setTitle("About");
		            stage.setScene(new Scene(root));
		            stage.show();          
		        }
		        catch (Exception ex) 
		        {
		            ex.printStackTrace();
		        }
            }
		});
		mainMenuButtons.put(logout, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { UIManager.instance.showScreen(((Control)e.getSource()), UIScreen.LOGIN);
                                                EmailsService emailService=new EmailsService();
    		                                     emailService.stopEmailAction(service);}
		});
		mainMenuButtons.put(quit, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { Platform.exit(); }
		});

		shoppingListButtons.put(new Button("My Shopping List"), new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) {}
		});
		shoppingListButtons.put(backToMenu, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleCloseLists(e); }
		});
		
		todoListButtons.put(new Button("My To Do List"), new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) {}
		});
		todoListButtons.put(backToMenu, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleCloseLists(e); }
		});
		
		appointmentsButtons.put(new Button("Appointments"), new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) {}
		});
		appointmentsButtons.put(backToMenu, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleCloseLists(e); }
		});
		
		totakeListButtons.put(new Button("To Take Lists"), new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) {}
		});
		totakeListButtons.put(backToMenu, new EventHandler<ActionEvent>() 
		{
            public void handle(ActionEvent e) { handleCloseLists(e); }
		});
				
		initButtons(mainMenuButtons);
		EmailsService emailService=new EmailsService();
		emailService.emailAction(service);
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
	
	/**
	 * @param note
	 */
	private void addNoteInList(Note note)
	{
		try
		{

			Pane newNoteRoot = new Pane();			
			newNoteRoot.setPrefSize(200, 150);
			newNoteRoot.setMinWidth(paneListNotes.getPrefWidth());
			
			DropShadow controlShadow = new DropShadow();
			controlShadow.setWidth(6);
			controlShadow.setHeight(6);
			controlShadow.setOffsetY(1.0f);
			controlShadow.setOffsetX(1.0f);
			controlShadow.setSpread(0.2f);
			controlShadow.setColor(Color.BLACK);
			
			Label newNoteTitle = new Label(note.getTitle());	
			newNoteTitle.setLayoutX(70);
			newNoteTitle.setFont(new Font("System", 36));
			newNoteTitle.setTextFill(Paint.valueOf("#FFFFFF"));
			newNoteTitle.setWrapText(false);
			newNoteTitle.setEffect(controlShadow);
			if(note.isChecked())
			{
				newNoteTitle.setTextFill(Paint.valueOf("#FFC325"));
				newNoteTitle.setUnderline(true);
			}
			else
			{
				newNoteTitle.setTextFill(Paint.valueOf("#FFFFFF"));
				newNoteTitle.setUnderline(false);
			}
			
			Label newNoteDesc = new Label(note.getMessage());	
			newNoteDesc.setPrefSize(500, 90);
			newNoteDesc.setLayoutY(58);
			newNoteDesc.setAlignment(Pos.TOP_LEFT);
			newNoteDesc.setFont(new Font("System", 18));
			newNoteDesc.setTextFill(Paint.valueOf("#FFFFFF"));
			newNoteDesc.setWrapText(true);
			newNoteDesc.setEffect(controlShadow);
			
			ToggleButton noteCheck = new ToggleButton();
			noteCheck.setLayoutY(5);
			noteCheck.setPrefSize(50, 45);
			noteCheck.getStyleClass().clear();
			noteCheck.getStyleClass().add("checkbox");
			noteCheck.setSelected(note.isChecked());
			ImageView noteCheckImg = new ImageView();
			noteCheckImg.setFitWidth(50);
			noteCheckImg.setFitHeight(45);
			noteCheckImg.setPreserveRatio(true);
			Image imgForCheck = note.isChecked() ? new Image(getClass().getResourceAsStream("/assets/icons8-tick-box-100.png")) : new Image(getClass().getResourceAsStream("/assets/icons8-unchecked-checkbox-100.png"));
			try
			{
				noteCheckImg.setImage(imgForCheck);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			noteCheck.setGraphic(noteCheckImg);
			noteCheck.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					try
					{
						Image imgForCheck = noteCheck.isSelected() ? new Image(getClass().getResourceAsStream("/assets/icons8-tick-box-100.png")) : new Image(getClass().getResourceAsStream("/assets/icons8-unchecked-checkbox-100.png"));
						noteCheckImg.setImage(imgForCheck);
						noteCheck.setGraphic(noteCheckImg);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					note.setChecked(noteCheck.isSelected());
					if(note.isChecked())
					{
						newNoteTitle.setTextFill(Paint.valueOf("#FFC325"));
						newNoteTitle.setUnderline(true);
					}
					else
					{
						newNoteTitle.setTextFill(Paint.valueOf("#FFFFFF"));
						newNoteTitle.setUnderline(false);
					}
					
					NotesService.getInstance().changeNote(note);
				}				
			});
			
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
			editNoteImage.setPreserveRatio(true);
			Button editNote = UIManager.instance.createButtonWithImage(512, 70, new Image(getClass().getResourceAsStream("/assets/icons8-edit-100.png")), 45, 50, editNoteImage, 55, 50);
			editNote.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					newNoteTitle.setVisible(!newNoteTitle.isVisible());
					newNoteDesc.setVisible(!newNoteDesc.isVisible());
					noteCheck.setVisible(!noteCheck.isVisible());
					
					noteTitleEdit.setVisible(!noteTitleEdit.isVisible());
					noteDescEdit.setVisible(!noteDescEdit.isVisible());
					
					try
					{
						if(newNoteTitle.isVisible())
							editNoteImage.setImage(new Image(getClass().getResourceAsStream("/assets/icons8-edit-100.png")));
						else
							editNoteImage.setImage(new Image(getClass().getResourceAsStream("/assets/icons8-checkmark-100.png")));				
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					if(!newNoteTitle.getText().equalsIgnoreCase(noteTitleEdit.getText()))
					{
						newNoteTitle.setText(noteTitleEdit.getText());
						note.setTitle(noteTitleEdit.getText());
						NotesService.getInstance().changeNote(note);
					}
					
					if(!newNoteDesc.getText().equalsIgnoreCase(noteDescEdit.getText()))
					{
						newNoteDesc.setText(noteDescEdit.getText());
						note.setMessage(noteDescEdit.getText());
						NotesService.getInstance().changeNote(note);
					}
				}	
			});
			editNote.setEffect(controlShadow);
			
			ImageView timerNoteImage = new ImageView();
			timerNoteImage.setPreserveRatio(true);
			Button timerNote = UIManager.instance.createButtonWithImage(587, 70, new Image(getClass().getResourceAsStream("/assets/icons8-google-alerts-100.png")), 45, 50, timerNoteImage, 55, 50);
			timerNote.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					UIManager.instance.setNoteForReminder(note);
			        Parent root;
			        try 
			        {
			            root = FXMLLoader.load(getClass().getResource("UIReminderTime.fxml"));
			            Stage stage = new Stage();
			            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons8-google-alerts-100.png")));
			            stage.setTitle("Reminders");
			            stage.setScene(new Scene(root, 430, 570));
			            stage.show();          
			        }
			        catch (Exception e) 
			        {
			            e.printStackTrace();
			        }
				}
			});	
			timerNote.setEffect(controlShadow);
			
			ImageView deleteNoteImage = new ImageView();
			deleteNoteImage.setPreserveRatio(true);
			Button deleteNote = UIManager.instance.createButtonWithImage(664, 70, new Image(getClass().getResourceAsStream("/assets/icons8-delete-bin-100.png")), 45, 50, deleteNoteImage, 55, 50);			
			deleteNote.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					paneListNotes.getChildren().remove(newNoteRoot);
					NotesService.getInstance().deleteNote(note.getId());
				}	
			});		
			deleteNote.setEffect(controlShadow);
			
			newNoteRoot.getChildren().addAll(newNoteTitle, noteTitleEdit, noteCheck, newNoteDesc, noteDescEdit, editNote, timerNote, deleteNote);
			this.paneListNotes.getChildren().add(0, newNoteRoot);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void loadSavedNotes()
	{
		List<Note> savedNotes = NotesService.getInstance().getListOfNotes(UIManager.instance.getUserId(), UIManager.instance.getTypeForNotes());
		
		for(Note n : savedNotes)
		{
			this.addNoteInList(n);
		}
	}
	
	@FXML
	public void handleOpenShoppingLists(ActionEvent event)
	{
		((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - My Shopping Lists ");

		buttonAddNewNote.setVisible(true);
		buttonsToAdd.clear();
		initButtons(shoppingListButtons);	
		
		labelListTitle.setText("My Shopping List");
		
		UIManager.instance.setTypeForNotes(Type.SHOPPINGLIST.getId());
		
		loadSavedNotes();
	}

	@FXML
	public void handleOpenToTakeLists(ActionEvent event)
	{
		((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - To Take Lists ");
		
		buttonAddNewNote.setVisible(true);
		buttonsToAdd.clear();
		initButtons(totakeListButtons);
		
		labelListTitle.setText("My To Take List");
		
		UIManager.instance.setTypeForNotes(Type.TOTAKELIST.getId());
		
		loadSavedNotes();
	}

	@FXML
	public void handleOpenAppointmentsLists(ActionEvent event)
	{
		((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - Appointments ");
		
		buttonAddNewNote.setVisible(true);
		buttonsToAdd.clear();
		initButtons(appointmentsButtons);
		
		labelListTitle.setText("My Appointment");
		
		UIManager.instance.setTypeForNotes(Type.APPOINTMENTS.getId());
		
		loadSavedNotes();
	}
	
	@FXML
	public void handleOpenToDoLists(ActionEvent event)
	{
		((Stage) paneListsMenu.getScene().getWindow()).setTitle("Fantastic Memory - My To Do Lists ");
		
		buttonAddNewNote.setVisible(true);
		buttonsToAdd.clear();
		initButtons(todoListButtons);
		
		labelListTitle.setText("My To Do List");
		
		UIManager.instance.setTypeForNotes(Type.TODOLIST.getId());
		
		loadSavedNotes();
	}
	
	@FXML
	public void handleCloseLists(ActionEvent event)
	{
		((Stage) backToMenu.getScene().getWindow()).setTitle("Fantastic Memory - My Lists ");
		
		buttonAddNewNote.setVisible(false);
		buttonsToAdd.clear();
		initButtons(mainMenuButtons);
		
		labelListTitle.setText("My Lists");
		
		this.paneListNotes.getChildren().removeAll(this.paneListNotes.getChildren());
	}

	@FXML
	public void handleAddNewNote(ActionEvent event)
	{
		Note note = new Note();

		note.setTitle("Example Note");
		note.setMessage("Example Message");
		note.setId_type(UIManager.instance.getTypeForNotes());
		
		paneListNotes.getChildren().removeAll(paneListNotes.getChildren());
		NotesService.getInstance().addNote(UIManager.instance.getUserId(), note.getTitle(), note.getMessage(), UIManager.instance.getTypeForNotes(), false);
		loadSavedNotes();
	}
}
