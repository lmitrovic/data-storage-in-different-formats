package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

public class UpdateWindow extends Application{

	private DataIE dataIE;
	private Entity entity;
	public UpdateWindow(DataIE dataIE, Entity entity) {
		this.dataIE=dataIE;
		this.entity=entity;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Label label1 = new Label("Name:");
		Label label3 = new Label("Attributes:");

	
		TextField fieldName = new TextField();
		
		TextArea areaAttributes = new TextArea();
		areaAttributes.setPrefWidth(100);
		areaAttributes.setPrefHeight(100);
		
		fieldName.setText(entity.getName());
		
		Iterator iterator = entity.getAttributes().entrySet().iterator();
		Map mapWithoutNested = new HashMap<String,Object>();
		Map mapNested = new HashMap<String,Object>();


		while (iterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry) iterator.next();
			if (!(mapElement.getValue() instanceof Entity)) {
				mapWithoutNested.put(mapElement.getKey(), mapElement.getValue());
			}else {
				mapNested.put(mapElement.getKey(), mapElement.getValue());
			}
		}
		
		String str = mapWithoutNested.toString().replace(" ", "").replace("{", "").replace("}", "").replace("=", ":");
		StringBuilder sb = new StringBuilder();
		for (String field : str.split(",")) {
			sb.append(field + '\n');
		}
		sb.deleteCharAt(sb.length()-1);
		
		
		areaAttributes.setText(sb.toString());
		
		Button buttonSave = new Button();
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
                String name = fieldName.getText();
				String area = areaAttributes.getText();
				Map map = new HashMap<String,Object>();
				if (!area.equals("")) {
				for (String field : area.split("\n")) {
					String s[] = field.split(":");
					if (isNumeric(s[1])) map.put(s[0], Integer.parseInt(s[1]));
					else map.put(s[0], s[1]);
				}
				}
				map.putAll(mapNested);
				
				int id = entity.getId();
				
				dataIE.update(entity, name, map);
				
				MainFrame.getInstance().getTable().setItems(FXCollections.observableList(dataIE.getEntityList()));
				MainFrame.getInstance().getTable().refresh();
				primaryStage.close();
				
			}
		});
		buttonSave.setPrefSize(110, 30);
		buttonSave.setText("Update and save");
		
		HBox boxH1 = new HBox();
		boxH1.setAlignment(Pos.BOTTOM_RIGHT);
		boxH1.getChildren().add(buttonSave);
		
		
		GridPane addGrid = new GridPane();
		addGrid.setVgap(10);
		addGrid.setHgap(10);
		addGrid.setAlignment(Pos.CENTER);
		
		addGrid.add(label1, 0, 0);
		addGrid.add(fieldName, 1, 0);

		addGrid.add(label3, 0, 1);
		addGrid.add(areaAttributes, 1, 1);

		addGrid.add(boxH1, 2, 2);
		
		Scene addScene = new Scene(addGrid, 500, 500);
		primaryStage.setScene(addScene);
		primaryStage.show();
		
	}
	
	private boolean isNumeric(String str) { 
		  try {  
		    Integer.parseInt(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}

}
