package application.helpers.ScheduledTransactions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.ScheduledTransaction;
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
}
