package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import dataconvertor.DataIE;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import model.Entity;

public class FilterWindow extends Application{
	
	private DataIE dataIE;
	
	public FilterWindow(DataIE dataIE) {
		this.dataIE=dataIE;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Label label1 = new Label("ID");
		Label label2 = new Label("Name");
		
		Label sep1 = new Label();
		Label sep2 = new Label();

       sep1.setPrefSize(30,30);
       sep2.setPrefSize(30,30);
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			    	"=",
			        ">=",
			        ">",
			        "<=",
			        "<"
			    );
		ComboBox comboBox = new ComboBox(options);
		
		ObservableList<String> options2 = 
			    FXCollections.observableArrayList(
			    	"OR",
			        "AND"
			    );
		ComboBox comboBox2 = new ComboBox(options2);
	
		TextField fieldName = new TextField();
		TextField fieldID = new TextField();
		


		
		Button buttonSave = new Button();
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				int id = -1;
				if (isNumeric(fieldID.getText())) id = Integer.parseInt(fieldID.getText());
				String name = fieldName.getText();
				
				List<Entity> entities = new ArrayList<Entity>();
				
				if (id == -1 && !name.equals("")) entities = dataIE.filter(name, name.contains("%"));
				else if(id != -1 && name.equals("")) entities = dataIE.filter(id,comboBox.getSelectionModel().getSelectedItem().toString());
				else if (id != -1 && !name.equals("")) 
					entities = dataIE.filter(id, comboBox.getSelectionModel().getSelectedItem().toString(), 
							name, comboBox2.getSelectionModel().getSelectedItem().toString(), name.contains("%"));
				
				MainFrame.getInstance().getTable().setItems(FXCollections.observableList(entities));
				MainFrame.getInstance().getTable().refresh();
				
				primaryStage.close();
				
			}
		});
		buttonSave.setPrefSize(110, 30);
		buttonSave.setText("Filter");
		
		HBox boxH1 = new HBox();
		boxH1.setAlignment(Pos.BOTTOM_RIGHT);
		boxH1.getChildren().add(buttonSave);
		
		
		GridPane addGrid = new GridPane();
		addGrid.setVgap(10);
		addGrid.setHgap(10);
		addGrid.setAlignment(Pos.CENTER);
		
		addGrid.add(label1, 0, 0);
		addGrid.add(comboBox, 1, 0);
		addGrid.add(fieldID, 2, 0);
		
		addGrid.add(sep1, 1, 1);
		
		addGrid.add(comboBox2, 1, 2);
		
		addGrid.add(sep2, 1, 3);

		addGrid.add(label2, 0, 4);
		addGrid.add(fieldName, 1, 4);

		addGrid.add(boxH1, 2, 5);
		
		Scene addScene = new Scene(addGrid, 500, 500);
		primaryStage.setTitle("Filter");
		primaryStage.setScene(addScene);
		primaryStage.show();
		
	}
	
	private boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	

}
