package application.helpers.TransactionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.singleton.DatabaseConnectionManager;

public class TransactionTypesDBHelper {
	private static final String URL = "jdbc:sqlite:src/application/database/transactionTypes.db";

	// insert transaction type to database method
	public static void insertType(String transactionType) {
		// define SQL query
		String sql = "INSERT INTO transactionTypes (transaction_type) VALUES (?)";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {

			pstmt.setString(1, transactionType); // set first param to transactionType

			// execute to insert to transactionTypes table
			pstmt.executeUpdate();

			System.out.println("Type added to database: " + transactionType); // confirms that type has been added
																				// to
																				// database
		} catch (SQLException e) {
			System.out.println("Error inserting data: " + e.getMessage());
		}
	}

	public static void removeType(String transactionType) {
		// sql delete query
		String sql = "DELETE FROM transactionTypes WHERE transaction_type = ?";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {
			pstmt.setString(1, transactionType);

			// execute update to remove type
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Type deleted from database: " + transactionType);
			} else {
				System.out.println("No matching type found to delete.");
			}
		} catch (SQLException e) {
			System.out.println("Error inserting data: " + e.getMessage());
		}
	}

	public static boolean isDuplicateType(String transactionType) {
		String sql = "SELECT COUNT(*) FROM transactionTypes WHERE transaction_type = ?"; // SQL query to count instances
																							// of transaction type

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {

			// sets first parameter of sql statement to provided "transactionType"
			pstmt.setString(1, transactionType);

			// execute the query and store result in "rs"
			ResultSet rs = pstmt.executeQuery();

			// go to first row of ResultSet (if exists) and check the count
			if (rs.next()) {
				int count = rs.getInt(1); // Get the count from the first column of the result
				return count > 0; // Return true if count is greater than 0 (duplicate exists)
			}
		} catch (SQLException e) {
			System.out.println("Error checking for duplicate Type: " + e.getMessage());
		}

		return false; // If something goes wrong, assume no duplicate exists
	}
}
