package application.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.helpers.AlertHandler;
import application.helpers.SceneSwitcher;
import application.helpers.TransactionType.TransactionTypesDAO;
import application.singleton.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransactionTypesController implements Initializable {
	private CommonObjects commonObjs = CommonObjects.getInstance();

	@FXML
	private ScrollPane typesScrollPane;

	@FXML
	private TextField typeField;

	@Override
	public void initialize(URL url, ResourceBundle resources) {
//		System.out.println("RESET"); // tester

		List<String> types = fetchTypesFromDatabase(); // get all types from database

		VBox typesVBox = new VBox(); // holds rows

		for (String type : types) {
			HBox row = new HBox();

			Label typeName = new Label(type);

			// Create a delete button for each HBox
			Button deleteButton = new Button("X");

			// Set the action for the delete button
			deleteButton.setOnAction(event -> deleteType(type));

			// styles
			typeName.setMinWidth(175);
			typeName.setStyle("-fx-font-size: 12px; -fx-text-fill: black; -fx-padding: 2px; -fx-pref-height: 20px;");

			deleteButton.setStyle("-fx-background-color: red; -fx-font-weight: bold;");

			// add type name and button to HBox row
			row.getChildren().addAll(typeName, deleteButton);

			row.setStyle(
					"-fx-background-color: lightblue; -fx-padding: 6; -fx-border-color: black; -fx-border-width: 0.5;");
			row.setMinHeight(30);

			typesVBox.getChildren().add(row); // add row to vbox
		}

		typesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		// add VBox to scrollPane (this allows for overflow in y direction for VBox
		// content if there are many accounts)
		typesScrollPane.setContent(typesVBox);
		typesScrollPane.setFitToWidth(true);

	}

	// create transaction type
	@FXML
	public void createType() {
		String typeName = typeField.getText();

		// checking to make sure type name is not empty
		if (typeName.isEmpty()) {
			AlertHandler.showAlert(AlertType.ERROR, "Form Error", "Please fill in all fields.");
			return;
		}

		// checking for duplicates
		if (TransactionTypesDAO.isDuplicateType(typeName)) {
			AlertHandler.showAlert(AlertType.ERROR, "Duplicate Type", "A type with this name already exists");
			return;
		}

		// insert type to database
		TransactionTypesDAO.insertType(typeName);

		AlertHandler.showAlert(AlertType.INFORMATION, "Type Created", "Type Name: " + typeName);

		reloadTransactionTypes();
	}

	// delete type from database
	public void deleteType(String typeName) {
		TransactionTypesDAO.removeType(typeName);

		// Inform the user of successful deletion (optional)
		System.out.println("Type Deleted: " + typeName);

		reloadTransactionTypes();
	}

	// fetches all types from database
	private List<String> fetchTypesFromDatabase() {
		TransactionTypesDAO typesDAO = new TransactionTypesDAO();
		return typesDAO.getAllTypes(); // for use in initialization of TransactionType page
	}

	// switch back from transactionTypePage to home page
	@FXML
	public void goToHomePageOp() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToHomePage(mainBox);
	}

	// reload the anchor pane to get "initialize" method to happen again
	@FXML
	public void reloadTransactionTypes() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToTransactionTypes(mainBox);
	}
}
