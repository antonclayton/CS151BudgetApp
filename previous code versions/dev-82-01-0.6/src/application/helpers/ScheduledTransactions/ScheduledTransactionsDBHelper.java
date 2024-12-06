package application.helpers.ScheduledTransactions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.singleton.DatabaseConnectionManager;

public class ScheduledTransactionsDBHelper {
	private static final String URL = "jdbc:sqlite:src/application/database/scheduledTransactions.db";

	public static void insertScheduledTransaction(String STName, String STAccountName, String STType,
			String STFrequency, int STDueDate, double STPayment) {

		// sql query
		String sql = "INSERT INTO scheduledTransactions (scheduled_transaction_name, account_name, type_name, frequency, due_date, payment) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {
			pstmt.setString(1, STName); // set first param to account name
			pstmt.setString(2, STAccountName); // set second param to type
			pstmt.setString(3, STType); // set third param to date
			pstmt.setString(4, STFrequency); // set fourth param to description
			pstmt.setDouble(5, STDueDate); // set fifth param to payment
			pstmt.setDouble(6, STPayment); // set sixth param to deposit

			pstmt.executeUpdate(); // add transaction to database
			System.out.println("Scheduled transaction added to database"); // log transaction added
		} catch (SQLException e) {
			System.out.println("Error inserting data: " + e.getMessage());
		}
	}

	public static boolean hasDuplicateScheduledTransactionName(String STName) {
		String sql = "SELECT COUNT(*) FROM scheduledTransactions WHERE scheduled_transaction_name = ?";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {

			// sets first parameter of sql statement to provided "accountName"
			pstmt.setString(1, STName);

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
