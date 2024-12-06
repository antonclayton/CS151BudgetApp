package application.helpers.Transactions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.model.Transaction;
import application.singleton.DatabaseConnectionManager;

public class TransactionsDAO {
	private static final String URL = "jdbc:sqlite:src/application/database/transactions.db"; // Your database URL

	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<>();

		// SQL query to select all from transactions table
		String sql = "SELECT * FROM transactions";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			// loop through result set
			while (rs.next()) {
				String tAccountName = rs.getString("account_name");
				String tTypeName = rs.getString("type_name");
				LocalDate dateCreated = LocalDate.parse(rs.getString("date_created"));
				String tDesc = rs.getString("transaction_description");
				double tPayment = rs.getDouble("payment");
				double tDeposit = rs.getDouble("deposit");

				Transaction transaction = new Transaction(tAccountName, tTypeName, dateCreated, tDesc, tPayment,
						tDeposit);
				// new transaction created and added to arraylist to display in transaction page
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			System.out.println("Error retrieving transactions: " + e.getMessage());
		}
		return transactions; // returns all transactions in database
	}
}
