module CodeVersion1 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires org.xerial.sqlitejdbc; // Required for SQLite JDBC
	requires org.slf4j; // Required for SLF4J

	exports application.controller;

	opens application.controller to javafx.fxml;

	exports application to javafx.graphics;
}
