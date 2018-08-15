package application.ui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
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
/*		timePickerReminderDate.setValue(LocalDate.now());

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
		
		timePickerReminderDate.setDayCellFactory(dayCellFactory);*/
	}
	
    @FXML
    void handleAddNewReminder(ActionEvent event) 
    {

    }

    @FXML
    void handleCreateNewReminder(ActionEvent event) 
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
			reminderDatePicker.setValue(LocalDate.now());
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
			reminderTimePicker.setPromptText("HH:MM");
			
			controlShadow.setSpread(0);
			
			ImageView reminderSetImage = new ImageView();
			reminderSetImage.setPreserveRatio(true);
			Button reminderSet = UIManager.instance.createButtonWithImage(310, 10, new Image(getClass().getResourceAsStream("/assets/icons8-google-alerts-100.png")), 76, 64, reminderSetImage, 58, 53);			
			reminderSet.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					
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
}
