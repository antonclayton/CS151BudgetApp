package application;

import application.singleton.CommonObjects;
import application.singleton.DatabaseConnectionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HBox mainBox = (HBox) FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			Scene scene = new Scene(mainBox);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			// keep a reference of the mainBox inside the commonObjs object
			CommonObjects commonObjs = CommonObjects.getInstance();
			commonObjs.setMainBox(mainBox);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		// Close all database connections
		DatabaseConnectionManager.closeDBConnections();
		System.out.println("Application is closing, and all database connections have been closed.");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
