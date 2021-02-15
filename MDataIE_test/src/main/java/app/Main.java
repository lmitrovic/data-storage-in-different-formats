package app;

import java.awt.Insets;
import java.io.File;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dataconvertor.DataIE;
import dataconvertor.DataIEManager;
import gui.AutoincrementWindow;
import gui.MainFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Entity;
import utils.FileMethods;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Data Import/Export");

		Label welcomeLabel = new Label("Welcome to the Data Import/Export");

		final FileChooser fileChooser = new FileChooser();

		Text saveText = new Text();

		Button chooseButton = new Button();
		chooseButton.setText("Choose a file");
		chooseButton.setPrefHeight(30);
		chooseButton.setPrefWidth(150);
		chooseButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				saveText.setText("loading...");
				saveText.setFill(Color.FORESTGREEN);

				Stage newWindow = new Stage();
				newWindow.setTitle("Data Import/Export");

				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					String fileName = file.getAbsolutePath();
					// Import file
					String json = "";
					try {

						Class.forName("dataie_yaml.DataIE_yaml");
						DataIE dataIE = DataIEManager.getDataIE(fileName);

						MainFrame.getInstance().setDataIE(dataIE);

						json = dataIE.importDataFormat();

						if (!(json.equals(""))) {

							List<Entity> objects = dataIE.convertDataFormatToObjects(json, Entity.class);

							Map map = new HashMap<String, String>();
							String str = FileMethods.fileToString("/config/autoincrement.txt");
							if (!str.equals("")) {
								for (String s : str.split("\n")) {
									String f[] = s.split(";");
									map.put(f[0], f[1]);
								}

								if (map.containsKey(fileName) && map.get(fileName).equals("TRUE")) {
									dataIE.setAutoincrement(true);
									int id = 0;
									for (Entity en:dataIE.getEntityList()) {
										Iterator iterator = en.getAttributes().entrySet().iterator();
										while(iterator.hasNext()) {
											Map.Entry mapElement = (Map.Entry) iterator.next();
											if (mapElement.getValue() instanceof Entity) {
												Entity entity = (Entity) mapElement.getValue();
												id = Math.max(id, entity.getId());
											}
										}
										id = Math.max(id, en.getId());
									}
									dataIE.setID(id);
								} else {
									dataIE.setAutoincrement(false);
									int id = 0;
									for (Entity en:dataIE.getEntityList()) {
										Iterator iterator = en.getAttributes().entrySet().iterator();
										while(iterator.hasNext()) {
											Map.Entry mapElement = (Map.Entry) iterator.next();
											if (mapElement.getValue() instanceof Entity) {
												Entity entity = (Entity) mapElement.getValue();
												id = Math.max(id, entity.getId());
											}
										}
										id = Math.max(id, en.getId());
									}
									dataIE.setID(id);
								}
							}

						}else {
							dataIE.setAutoincrement(true);
						}

						try {
							MainFrame.getInstance().start(newWindow);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						newWindow.show();

						primaryStage.close();

						if (json.equals("")) {
							AutoincrementWindow au = new AutoincrementWindow(dataIE);
							Stage s = new Stage();
							au.start(s);

						}

					} catch (Exception e1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText(null);
						alert.setContentText("The file you selected is not in the expected format!");
						alert.showAndWait();
					}

				}

			}

		});

		HBox hbox2 = new HBox();
		hbox2.getChildren().add(saveText);
		hbox2.setAlignment(Pos.CENTER);

		GridPane layout = new GridPane();
		layout.setAlignment(Pos.CENTER);
		layout.setHgap(15);
		layout.setVgap(10);

		layout.add(welcomeLabel, 0, 0);
		layout.add(chooseButton, 0, 2);
		layout.add(hbox2, 0, 3);

		Scene scene = new Scene(layout, 450, 350);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
