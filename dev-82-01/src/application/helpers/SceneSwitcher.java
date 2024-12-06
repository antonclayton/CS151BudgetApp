package application.helpers;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class SceneSwitcher {
	public static void switchScene(HBox mainBox, String fxmlPath) {
		try {
			URL url = SceneSwitcher.class.getResource(fxmlPath);
			if (url == null) {
				System.out.println("FXML file not found: " + fxmlPath);
				return;
			}

			FXMLLoader loader = new FXMLLoader(url);
			AnchorPane nextPane = loader.load();

			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}

			mainBox.getChildren().add(nextPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void goToHomePage(HBox mainBox) {
		switchScene(mainBox, "/view/Home.fxml");
	}

	public static void goToCreateAccount(HBox mainBox) {
		switchScene(mainBox, "/view/CreateAccount.fxml");
	}

	public static void goToAccountPage(HBox mainBox) {
		switchScene(mainBox, "/view/AccountPage.fxml");
	}

	public static void goToTransactionPage(HBox mainBox) {
		switchScene(mainBox, "/view/TransactionPage.fxml");
	}

	public static void goToTransactionTypes(HBox mainBox) {
		switchScene(mainBox, "/view/TransactionTypes.fxml");
	}

	public static void goToEnterTransaction(HBox mainBox) {
		switchScene(mainBox, "/view/CreateTransaction.fxml");
	}

	public static void goToEnterScheduledTransaction(HBox mainBox) {
		switchScene(mainBox, "/view/EnterScheduledTransaction.fxml");
	}

	public static void goToScheduledTransactionPage(HBox mainBox) {
		switchScene(mainBox, "/view/ScheduledTransactionPage.fxml");
	}

	public static void goToTransactionSearchPage(HBox mainBox) {
		switchScene(mainBox, "/view/TransactionSearchPage.fxml");
	}
}
