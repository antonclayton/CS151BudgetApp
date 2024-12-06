package application.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import application.helpers.SceneSwitcher;
import application.helpers.Account.AccountDAO;
import application.model.Account;
import application.singleton.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AccountPageController implements Initializable {

	private CommonObjects commonObjs = CommonObjects.getInstance();

//	@FXML private VBox accountsVBox;	//using VBox for ease of implementing tabular format
	@FXML
	private ScrollPane accountScrollPane; // using ScrollPane to handle overflow y for VBox

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Account> accounts = fetchAccountsFromDatabase(); // method from AccountDAO (arraylist of all accounts in
																// database)

		// sort the accounts by createdDate in descending order
		accounts.sort(Comparator.comparing(Account::getCreatedDate).reversed());

		VBox accountsVBox = new VBox();

		for (Account account : accounts) {
			HBox row = new HBox(10); // 10 is spacing between each element in HBox

			Label nameLabel = new Label(account.getAccountName());

			// format double balance as string
			Label balanceLabel = new Label(String.format("$%.2f", account.getAccountBalance()));

			// format LocalDate as string
			DateTimeFormatter dateStringFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			Label dateLabel = new Label(account.getCreatedDate().format(dateStringFormatter));

			// basic label styling
			nameLabel.setMinWidth(155); // minimum width of nameLabel
			nameLabel.setAlignment(Pos.CENTER_LEFT); // align left center

			balanceLabel.setMinWidth(105); // min width of balanceLabel
			balanceLabel.setAlignment(Pos.CENTER_RIGHT); // align center right

			dateLabel.setMinWidth(95); // min width of dateLabel
			dateLabel.setAlignment(Pos.CENTER); // align center

			// extra styling for labels
			nameLabel.setStyle(
					"-fx-font-size: 12px; -fx-text-fill: blue; -fx-border-color: black; -fx-border-width: 0 1 0 0; -fx-padding: 5px; -fx-pref-height: 30px;");
			balanceLabel
					.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;  -fx-padding: 5px; -fx-pref-height: 30px;");
			dateLabel.setStyle(
					"-fx-font-size: 12px; -fx-padding: 5px; -fx-border-color: black;-fx-border-width: 0 0 0 1; -fx-pref-height: 30px;");

			// add labels to the HBox
			row.getChildren().addAll(nameLabel, balanceLabel, dateLabel);
			row.setStyle(
					"-fx-background-color: lightblue; -fx-padding: 10; -fx-border-color: black; -fx-border-width: 1;");

			// add HBox row to VBox
			accountsVBox.getChildren().add(row);
		}

		// no horizontal overflow
		accountScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		// add VBox to scrollPane (this allows for overflow in y direction for VBox
		// content if there are many accounts)
		accountScrollPane.setContent(accountsVBox);
		accountScrollPane.setFitToWidth(true);
	}

	// fetch accounts arrayList from database
	private List<Account> fetchAccountsFromDatabase() {
		AccountDAO accountDAO = new AccountDAO();
		return accountDAO.getAllAccounts(); // for use in initialization of accountPage
	}

	// connects "create new account" button on AccountPage to go to CreateAccount
	// page
	@FXML
	public void goToCreateAccountOp() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToCreateAccount(mainBox);
	}
}
