module CodeVersion1 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	exports application.controller;
	
	opens application.controller to javafx.fxml;
	exports application to javafx.graphics;
}
