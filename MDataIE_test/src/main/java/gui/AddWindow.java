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

public class AddWindow extends Application {

	private DataIE dataIE;

	public AddWindow(DataIE dataIE) {
		this.dataIE = dataIE;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Label label1 = new Label("Name:");
		Label label2 = new Label("ID:");
		Label label3 = new Label("Attributes:");

		TextField fieldName = new TextField();
		TextField fieldID = new TextField();

		TextArea areaAttributes = new TextArea();
		areaAttributes.setPrefWidth(100);
		areaAttributes.setPrefHeight(100);

		Button buttonSave = new Button();
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String name = fieldName.getText();
				int id = -1;
				if ((!(fieldID.getText().equals("")) && isNumeric(fieldID.getText())))
					id = Integer.parseInt(fieldID.getText());
				String area = areaAttributes.getText();
				Map map = new HashMap<String, Object>();
				if (!area.equals("")) {
					for (String field : area.split("\n")) {
						String s[] = field.split(":");
						if (isNumeric(s[1]))
							map.put(s[0], Integer.parseInt(s[1]));
						else
							map.put(s[0], s[1]);
					}
				}
				if (id == -1 && !map.isEmpty())
					dataIE.addEntity(name, map);
				else if (id != -1 && !map.isEmpty())
					dataIE.addEntity(name, id, map);
				else if (map.isEmpty() && id == -1)
					dataIE.addEntity(name);
				else if (map.isEmpty() & id != -1)
					dataIE.addEntity(name, id);

				MainFrame.getInstance().getTable().setItems(FXCollections.observableList(dataIE.getEntityList()));
				MainFrame.getInstance().getTable().refresh();
				primaryStage.close();

			}
		});
		buttonSave.setPrefSize(110, 30);
		buttonSave.setText("Add");

		HBox boxH1 = new HBox();
		boxH1.setAlignment(Pos.BOTTOM_RIGHT);
		boxH1.getChildren().add(buttonSave);

		GridPane addGrid = new GridPane();
		addGrid.setVgap(10);
		addGrid.setHgap(10);
		addGrid.setAlignment(Pos.CENTER);

		addGrid.add(label1, 0, 0);
		addGrid.add(fieldName, 1, 0);
		if (!dataIE.isAutoincrement()) {

			addGrid.add(label2, 0, 1);
			addGrid.add(fieldID, 1, 1);

		}
		addGrid.add(label3, 0, 2);
		addGrid.add(areaAttributes, 1, 2);

		addGrid.add(boxH1, 3, 3);

		Scene addScene = new Scene(addGrid, 500, 500);
		primaryStage.setTitle("Add new Entity");
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
