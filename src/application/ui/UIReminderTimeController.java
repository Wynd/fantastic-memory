package application.ui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import application.model.Email;
import application.service.EmailsService;
import application.service.NotesService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UIReminderTimeController implements Initializable
{

    @FXML
    private AnchorPane paneReminderRoot;

    @FXML
    private Button buttonReminderCancel, buttonReminderConfirm, buttonReminderNew;

    @FXML
    private VBox paneListsMenu;

    @FXML
    private Pane paneReminder;

    @FXML
    private DatePicker timePickerReminderDate;

    @FXML
    private TextField fieldReminderTime;
	
	public void initialize(URL location, ResourceBundle resources) 
	{		
		loadSavedReminders();
	}
	
	private void loadSavedReminders()
	{
		List<Email> unsentMails = EmailsService.getInstance().getUnsentEmailsForANote(UIManager.instance.getNoteForReminder().getId());
				
		for(Email e : unsentMails)
		{
			createNewReminder(e);
		}		
	}
	
	private void createNewReminder(Email mail)
	{
		try
		{
			Pane newReminderRoot = new Pane();
			
			DropShadow controlShadow = new DropShadow();
			controlShadow.setWidth(6);
			controlShadow.setHeight(6);
			controlShadow.setOffsetY(1.0f);
			controlShadow.setOffsetX(1.0f);
			controlShadow.setSpread(0.2f);
			controlShadow.setColor(Color.BLACK);
			
			Text reminderDateText = new Text("Date");	
			reminderDateText.setLayoutX(42);
			reminderDateText.setLayoutY(33);
			reminderDateText.setFont(Font.font("System", FontWeight.BOLD, 30));
			reminderDateText.setFill(Paint.valueOf("#97C145"));					
			reminderDateText.setEffect(controlShadow);
			
			Text reminderTimeText = new Text("Time");	
			reminderTimeText.setLayoutX(183);
			reminderTimeText.setLayoutY(33);
			reminderTimeText.setFont(Font.font("System", FontWeight.BOLD, 30));
			reminderTimeText.setFill(Paint.valueOf("#97C145"));					
			reminderTimeText.setEffect(controlShadow);

			DatePicker reminderDatePicker = new DatePicker();
			reminderDatePicker.setPrefSize(136, 25);
			reminderDatePicker.setLayoutX(14);
			reminderDatePicker.setLayoutY(41);
			reminderDatePicker.setValue(mail.getDate_scheduled() != null  ? mail.getDate_scheduled().toLocalDate() : LocalDate.now());
			Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() 
			{
			    public void updateItem(LocalDate item, boolean empty) 
			    {
			        super.updateItem(item, empty);
			        if (item.isBefore(LocalDate.now())) //Disable all dates after required date
			        {
			            setDisable(true);
			            setStyle("-fx-background-color: #ffc0cb;"); //To set background on different color
			        }
			    }
			};
			reminderDatePicker.setDayCellFactory(dayCellFactory);
			
			TextField reminderTimePicker = new TextField();
			reminderTimePicker.setPrefSize(86, 25);
			reminderTimePicker.setLayoutX(175);
			reminderTimePicker.setLayoutY(41);
			reminderTimePicker.setAlignment(Pos.CENTER);
			reminderTimePicker.setText(mail.getDate_scheduled() != null ? mail.getDate_scheduled().getHour() + ":" + mail.getDate_scheduled().getMinute() : "");
			reminderTimePicker.setPromptText("HH:MM");
			
			controlShadow.setSpread(0);
			
			ImageView reminderSetImage = new ImageView();
			reminderSetImage.setPreserveRatio(true);
			Image imgForReminder = mail.getDate_scheduled() != null ? new Image(getClass().getResourceAsStream("/assets/icons8-stop-100.png")) : new Image(getClass().getResourceAsStream("/assets/icons8-google-alerts-100.png"));
			Button reminderSet = UIManager.instance.createButtonWithImage(310, 10, imgForReminder, 76, 64, reminderSetImage, 58, 53);			
			reminderSet.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					if(mail.getDate_scheduled() == null)
					{
						int year = reminderDatePicker.getValue().getYear();
						int month = reminderDatePicker.getValue().getMonthValue();
						int dayOfMonth = reminderDatePicker.getValue().getDayOfMonth();
						
						String unparsedTime = reminderTimePicker.getText();
											
						if(unparsedTime.length() == 5)
						{
							
							String firstHalf = unparsedTime.substring(0, 2);
							String secondHalf = unparsedTime.substring(3, unparsedTime.length());
													
							int hour = Integer.parseInt(firstHalf);
							int minute = Integer.parseInt(secondHalf);
							
							if(hour < 24 && minute < 60)
							{									
								mail.setDate_scheduled(LocalDateTime.of(year, month, dayOfMonth, hour, minute, 0));
	
								NotesService.getInstance().createReminder(UIManager.instance.getNoteForReminder().getId(), mail.getDate_scheduled());	
								
								reminderSetImage.setImage(new Image(getClass().getResourceAsStream("/assets/icons8-stop-100.png")));
							}
						}
					}
					else
					{
						EmailsService.getInstance().updateEmails(mail.getDate_scheduled(), true, UIManager.instance.getNoteForReminder().getId());
						EmailsService.getInstance().deleteEmails();
						paneListsMenu.getChildren().remove(newReminderRoot);
					}
				}	
			});	
			reminderSet.setEffect(controlShadow);
			
			newReminderRoot.getChildren().addAll(reminderDateText, reminderTimeText, reminderDatePicker, reminderTimePicker, reminderSet);
			
			this.paneListsMenu.getChildren().add(0, newReminderRoot);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

    @FXML
    void handleCreateNewReminder(ActionEvent event) 
    {
    	Email newMail = new Email();
    	
    	newMail.setId_note(UIManager.instance.getNoteForReminder().getId());
    	
    	this.createNewReminder(newMail);
    }
    
    @FXML
    void handleCancel(ActionEvent event) 
    {
    	UIManager.instance.setNoteForReminder(null);
    	((Stage)((Control)event.getSource()).getScene().getWindow()).close();
    }

}
