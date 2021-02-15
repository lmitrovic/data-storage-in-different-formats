package gui;

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

public class AlertWindow extends Application{

	public AlertWindow() {
		
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		Label blankLabel = new Label();
		Label label = new Label("You must select a row!");
		HBox boxH = new HBox();
		boxH.setAlignment(Pos.CENTER);
	
		Button btn = new Button("OK");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});
		boxH.getChildren().add(btn);
	
		
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
