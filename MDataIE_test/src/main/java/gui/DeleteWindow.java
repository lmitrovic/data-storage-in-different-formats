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

public class DeleteWindow extends Application {

	private DataIE dataIE;

	public DeleteWindow(DataIE dataIE) {
		this.dataIE = dataIE;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Label lbl1 = new Label("Delete Entity where: ");
		Label label1 = new Label("Name = ");
		Label label2 = new Label("ID = ");

		TextField fieldName = new TextField();
		TextField fieldID = new TextField();
		
		Label or = new Label("OR");

		Button buttonSave = new Button();
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
	
                String name = fieldName.getText();
                int id=-1;
                if (isNumeric(fieldID.getText())) id = Integer.parseInt(fieldID.getText());
                if (name.equals("") && id!=-1) dataIE.delete(id);
                else if(!name.equals("") && id == -1) dataIE.delete(name);
                else if (!name.equals("") && id != -1) {
                	dataIE.delete(id);
                	dataIE.delete(name);
                }
                
				MainFrame.getInstance().getTable().setItems(FXCollections.observableList(dataIE.getEntityList()));
				MainFrame.getInstance().getTable().refresh();
				primaryStage.close();

			}
		});
		buttonSave.setPrefSize(110, 30);
		buttonSave.setText("Delete and save");

		HBox boxH1 = new HBox();
		boxH1.setAlignment(Pos.BOTTOM_RIGHT);
		boxH1.getChildren().add(buttonSave);

		GridPane addGrid = new GridPane();
		addGrid.setVgap(10);
		addGrid.setHgap(10);
		addGrid.setAlignment(Pos.CENTER);

		addGrid.add(lbl1, 0, 0);

		addGrid.add(label1, 0, 1);
		addGrid.add(fieldName, 1, 1);
		addGrid.add(or, 0, 2);

		addGrid.add(label2, 0, 3);
		addGrid.add(fieldID, 1, 3);

		addGrid.add(boxH1, 3, 4);

		Scene addScene = new Scene(addGrid, 450, 250);
		primaryStage.setTitle("Delete");
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
