package application.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;

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
}
