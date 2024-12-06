package application.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AccountsDBHelper {
	private static final String URL = "jdbc:sqlite:src/application/database/accounts.db";

	// testing connection to accounts.db
	public static void testDatabaseConnection() {
		try (Connection conn = DriverManager.getConnection(URL)) {
			if (conn != null) {
				System.out.println("Connection to SQLite has been established.");
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// insert account to database method
	public static void insertAccount(String accountName, double balance, LocalDate creationDate) {
		// define SQL query
		String sql = "INSERT INTO accounts (account_name, balance, date_created) VALUES (?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, accountName); // set first param to accountName
			pstmt.setDouble(2, balance); // set second param to balance
			pstmt.setString(3, creationDate.toString()); // set third param to date

			// execute to insert to accounts table
			pstmt.executeUpdate();

			System.out.println("Account added to database: " + accountName); // confirms that account has been added to
																				// database
		} catch (SQLException e) {
			System.out.println("Error inserting data: " + e.getMessage());
		}
	}

	// Method to check if an account with the given name already exists in the
	// database
	public static boolean isDuplicateAccount(String accountName) {
		String sql = "SELECT COUNT(*) FROM accounts WHERE account_name = ?"; // SQL query to count how many instances of
																				// account name "___"

		try (Connection conn = DriverManager.getConnection(URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// sets first parameter of sql statement to provided "accountName"
			pstmt.setString(1, accountName);

			// execute the query and store result in "rs"
			ResultSet rs = pstmt.executeQuery();

			// go to first row of ResultSet (if exists) and check the count
			if (rs.next()) {
				int count = rs.getInt(1); // Get the count from the first column of the result
				return count > 0; // Return true if count is greater than 0 (duplicate exists)
			}
		} catch (SQLException e) {
			System.out.println("Error checking for duplicate account: " + e.getMessage());
		}

		return false; // If something goes wrong, assume no duplicate exists
	}

}