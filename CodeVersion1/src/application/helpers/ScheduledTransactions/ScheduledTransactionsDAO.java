package application.helpers.ScheduledTransactions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.TransactionModels.ScheduledTransaction;
import application.singleton.DatabaseConnectionManager;

public class ScheduledTransactionsDAO {
	// database url
	private static final String URL = "jdbc:sqlite:src/application/database/scheduledTransactions.db";

	public List<ScheduledTransaction> getAllTransactions() {
		List<ScheduledTransaction> scheduledTransactions = new ArrayList<>();

		// SQL query to select all from scheduledTransactions table
		String sql = "SELECT * FROM scheduledTransactions";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			// loop through result set
			while (rs.next()) {
				String STName = rs.getString("scheduled_transaction_name");
				String STAccountName = rs.getString("account_name");
				String STType = rs.getString("type_name");
				String STFrequency = rs.getString("frequency");
				int STDueDate = rs.getInt("due_date");
				double STPayment = rs.getDouble("payment");

				ScheduledTransaction scheduledTransaction = new ScheduledTransaction(STName, STAccountName, STType,
						STFrequency, STDueDate, STPayment);
				// new scheduledTransaction created and added to arraylist to display in
				// ScheduledTransaction page
				scheduledTransactions.add(scheduledTransaction);
			}
		} catch (SQLException e) {
			System.out.println("Error retrieving Scheduled Transactions: " + e.getMessage());
		}
		return scheduledTransactions; // returns all scheduledTransactions in database
	}

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

	public static void removeScheduledTransaction(ScheduledTransaction scheduledTransaction) {
		// sql delete query
		String sql = "DELETE FROM scheduledTransactions WHERE scheduled_transaction_name = ?";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {
			// Set the parameters
			pstmt.setString(1, scheduledTransaction.getTransactionName());

			// execute update to remove type
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Type deleted from database: " + scheduledTransaction.getTransactionName());
			} else {
				System.out.println("No match found to delete.");
			}
		} catch (SQLException e) {
			System.out.println("Error inserting data: " + e.getMessage());
		}
	}

	public static void updateScheduledTransaction(String prevName, String scheduledTransactionName,
			String stAccountName, String stTypeName, String stFrequency, int stDueDate, double stPayment) {

		// SQL query for updating the transaction
		String sql = "UPDATE scheduledTransactions SET scheduled_transaction_name = ?, account_name = ?, type_name = ?, frequency = ?, due_date = ?, payment = ? WHERE scheduled_transaction_name = ?";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {
			pstmt.setString(1, scheduledTransactionName); // Set account name
			pstmt.setString(2, stAccountName); // Set type name
			pstmt.setString(3, stTypeName); // Set date
			pstmt.setString(4, stFrequency); // Set description
			pstmt.setInt(5, stDueDate); // Set payment
			pstmt.setDouble(6, stPayment); // Set deposit
			pstmt.setString(7, prevName); // Set scheduled transaction (used in WHERE clause)

			pstmt.executeUpdate(); // Update the transaction in the database
			System.out.println("Scheduled Transaction updated in the database"); // Log success
		} catch (SQLException e) {
			System.out.println("Error updating data: " + e.getMessage());
		}
	}
}
