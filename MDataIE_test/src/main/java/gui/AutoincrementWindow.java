package gui;


import dataconvertor.DataIE;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.FileMethods;

public class AutoincrementWindow extends Application{
	
	
	private DataIE dataIE;
	public AutoincrementWindow(DataIE dataIE) {
		this.dataIE=dataIE;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label blankLabel = new Label();
		Label label = new Label("Do you want to use autoincrement or not?");
		HBox boxH = new HBox();
		boxH.setAlignment(Pos.CENTER);
	
		Button btn = new Button("Yes");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				StringBuilder sb = new StringBuilder();
				String conFile = FileMethods.fileToString("/config/autoincrement.txt");
				// ako zeli autoincrement
				sb.append(conFile);
				sb.append(dataIE.getFileName() + ";" + "TRUE");
                dataIE.setAutoincrement(true);
                FileMethods.stringToFile("/config/autoincrement.txt", sb.toString());
				primaryStage.close();
			}
		});
		
		Button btn1 = new Button("No");
		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				StringBuilder sb = new StringBuilder();
				String conFile = FileMethods.fileToString("/config/autoincrement.txt");
				// ako zeli autoincrement
				sb.append(conFile);
				sb.append(dataIE.getFileName() + ";" + "FALSE");
                dataIE.setAutoincrement(false);
                FileMethods.stringToFile("/config/autoincrement.txt", sb.toString());
				primaryStage.close();
			}
		});
		boxH.getChildren().add(btn);
		boxH.getChildren().add(btn1);
		
		GridPane gridLay = new GridPane();
		gridLay.setAlignment(Pos.CENTER);
		gridLay.setHgap(20);
		gridLay.add(blankLabel, 0, 0);
		gridLay.add(label, 0, 1);
		gridLay.add(boxH, 0, 2);
		
		Scene newScene = new Scene(gridLay, 200, 100);
		primaryStage.setScene(newScene);
		primaryStage.show();
		
	}


}
