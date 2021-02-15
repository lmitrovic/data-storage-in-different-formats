package gui;

import dataconvertor.DataIE;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Order;

public class SortWindow extends Application{

	private DataIE dataIE;
	
	public SortWindow(DataIE dataIE) {
		this.dataIE = dataIE;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Label label = new Label("Sort by: ");
		Button btn = new Button("Apply and choose");
		btn.setPrefSize(130, 30);
		
		ToggleGroup group = new ToggleGroup();
		RadioButton radio1 = new RadioButton("Name");
		RadioButton radio2 = new RadioButton("ID");
		radio1.setToggleGroup(group);
		radio2.setToggleGroup(group);
		
		Label ascDesc = new Label("Choose ascending or descending: ");

		ToggleGroup group2 = new ToggleGroup();
		RadioButton radio11 = new RadioButton("Ascending");
		RadioButton radio22 = new RadioButton("Descending");
		radio11.setToggleGroup(group2);
		radio22.setToggleGroup(group2);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (radio1.isSelected() && radio11.isSelected()) dataIE.sort(Order.nameAsc);
				if (radio1.isSelected() && radio22.isSelected()) dataIE.sort(Order.nameDesc);
				if (radio2.isSelected() && radio11.isSelected()) dataIE.sort(Order.idAsc);
				if (radio2.isSelected() && radio22.isSelected()) dataIE.sort(Order.idDesc);
				MainFrame.getInstance().getTable().refresh();
				primaryStage.close();
			}
		});
		
		GridPane gridLay = new GridPane();
		gridLay.setAlignment(Pos.CENTER);
		gridLay.setVgap(10);
		gridLay.setHgap(15);
		gridLay.add(label, 0, 0);
		gridLay.add(radio1, 0, 1);
		gridLay.add(radio2, 1, 1);
		gridLay.add(ascDesc, 0, 2);
		gridLay.add(radio11, 0, 3);
		gridLay.add(radio22, 1, 3);

		gridLay.add(btn, 1, 4);
		
		Scene newScene = new Scene(gridLay, 400, 250);
		primaryStage.setTitle("Sort");
		primaryStage.setScene(newScene);
		primaryStage.show();
		
	}

}
