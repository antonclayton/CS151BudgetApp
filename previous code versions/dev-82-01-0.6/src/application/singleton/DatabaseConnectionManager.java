package application.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnectionManager {
	// Store a connection for each database URL
	private static Map<String, Connection> connections = new HashMap<>();

	// Private constructor to prevent instantiation
	private DatabaseConnectionManager() {
	}

	// Method to get a Singleton connection for a specific database URL
	public static Connection getConnection(String dbUrl) {
		if (!connections.containsKey(dbUrl)) {
			try {
				Connection connection = DriverManager.getConnection(dbUrl);
				connections.put(dbUrl, connection);
				System.out.println("Connection to database established: " + dbUrl);
			} catch (SQLException e) {
				System.out.println("Error connecting to database: " + e.getMessage());
			}
		}
		return connections.get(dbUrl);
	}

	// Close all database connections
	public static void closeDBConnections() {
		for (Map.Entry<String, Connection> entry : connections.entrySet()) {
			try {
				if (entry.getValue() != null && !entry.getValue().isClosed()) {
					entry.getValue().close();
//					System.out.println("Connection closed for: " + entry.getKey());
				}
			} catch (SQLException e) {
				System.out.println("Error closing connection for " + entry.getKey() + ": " + e.getMessage());
			}
		}
	}
}
