package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dataconvertor.DataIE;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Entity;

public class MainFrame extends Application {

	private DataIE dataIE;
	private static MainFrame instance;
	private TableView<Entity> table;

	private MainFrame() {

	}

	public static MainFrame getInstance() {
		if (instance == null)
			instance = new MainFrame();
		return instance;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Kreiranje tabele
		table = new TableView<Entity>();
		ObservableList<Entity> entities = FXCollections.observableList(dataIE.getEntityList());
		table.setItems(entities);

		TableColumn<Entity, String> nazivCol = new TableColumn<Entity, String>("Name");
		TableColumn<Entity, Integer> idCol = new TableColumn<Entity, Integer>("ID");
		TableColumn<Entity, Map<String, Object>> atributiCol = new TableColumn<Entity, Map<String, Object>>(
				"Attributes");
		table.getColumns().addAll(nazivCol, idCol, atributiCol);

		nazivCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("name"));
		idCol.setCellValueFactory(new PropertyValueFactory<Entity, Integer>("id"));
		atributiCol.setCellValueFactory(new PropertyValueFactory<Entity, Map<String, Object>>("attributes"));
		
		//atributiCol.prefWidthProperty().bind(table.widthProperty().multiply(0.767));

		Button btnAdd = new Button("Add");
		Button btnAddNested = new Button("AddNested");
		Button btnDelete = new Button("Delete");
		Button btnUpdate = new Button("Update");
		Button btnFilter = new Button("Filter");
		Button btnSort = new Button("Sort");
		Button saveAll = new Button("SaveAll");
		Button reset = new Button("Reset");

		btnAdd.setPrefSize(90, 30);
		btnAddNested.setPrefSize(90, 30);
		btnDelete.setPrefSize(90, 30);
		btnUpdate.setPrefSize(90, 30);
		btnFilter.setPrefSize(90, 30);
		btnSort.setPrefSize(90, 30);
		saveAll.setPrefSize(90, 30);
		reset.setPrefSize(90, 30);

		reset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				table.setItems(FXCollections.observableList(dataIE.getEntityList()));
				table.refresh();

			}
		});

		btnDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Stage stage = new Stage();
				DeleteWindow delWin = new DeleteWindow(dataIE);
				try {
					delWin.start(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnAddNested.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Entity e = table.getSelectionModel().getSelectedItem();
				if (e != null) {
					Stage newWindow = new Stage();
					AddNestedWindow add = new AddNestedWindow(dataIE, e);
					try {
						add.start(newWindow);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("You must select the Entity in the table to which you want to assign the nested Entity!");
					alert.showAndWait();
				}
			}

		});

		saveAll.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					String objects = dataIE.convertObjectToDataFormat(dataIE.getEntityList());
					dataIE.exportDataFormat(objects);
				} catch (Exception e) {
					e.printStackTrace();
				}
				primaryStage.close();
			}

		});

		btnAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage newWindow = new Stage();
				AddWindow add = new AddWindow(dataIE);
				try {
					add.start(newWindow);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnUpdate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Entity e = table.getSelectionModel().getSelectedItem();
				if (e != null) {
					Stage s = new Stage();
					UpdateWindow uw = new UpdateWindow(dataIE, e);
					try {
						uw.start(s);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("You must select the Entity in the table to which you want to assign the nested Entity!");
					alert.showAndWait();
				}
			}
		});

		btnSort.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage newStage = new Stage();
				SortWindow newWindow = new SortWindow(dataIE);
				try {
					newWindow.start(newStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnFilter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage newStage = new Stage();
				FilterWindow newWindow = new FilterWindow(dataIE);
				try {
					newWindow.start(newStage);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		Label lblNorth = new Label();
		Label lblEast = new Label();
		Label lblWest = new Label();
		Label lblSouth = new Label();
		Label lblBottom = new Label();
		lblSouth.setPrefSize(50, 50);
		lblNorth.setPrefSize(50, 50);
		lblEast.setPrefSize(50, 50);
		lblWest.setPrefSize(50, 50);
		lblBottom.setPrefSize(50, 50);

		GridPane grid = new GridPane();
		grid.setVgap(5);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);

		grid.add(lblNorth, 0, 0);
		grid.add(btnAdd, 0, 1);
		grid.add(btnAddNested, 1, 1);
		grid.add(btnDelete, 2, 1);
		grid.add(btnUpdate, 3, 1);
		grid.add(btnFilter, 4, 1);
		grid.add(btnSort, 5, 1);
		grid.add(reset, 6, 1);
		grid.add(saveAll, 6, 2);
		grid.add(lblBottom, 0, 3);

		BorderPane layout = new BorderPane();
		layout.setTop(lblNorth);
		layout.setLeft(lblWest);
		layout.setRight(lblEast);
		layout.setCenter(table);
		layout.setBottom(grid);

		Scene scene = new Scene(layout, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void setDataIE(DataIE dataIE) {
		this.dataIE = dataIE;
	}

	public DataIE getDataIE() {
		return dataIE;
	}

	public TableView<Entity> getTable() {
		return table;
	}

}
