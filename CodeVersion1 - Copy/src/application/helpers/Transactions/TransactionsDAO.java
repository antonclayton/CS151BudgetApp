package application.helpers.Transactions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.model.TransactionModels.Transaction;
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

	public static void insertTransaction(String tAccountName, String tTypeName, LocalDate tDatePicker, String tDesc,
			double tPayment, double tDeposit) {

		// sql query
		String sql = "INSERT INTO transactions (account_name, type_name, date_created, transaction_description, payment, deposit) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {
			pstmt.setString(1, tAccountName); // set first param to account name
			pstmt.setString(2, tTypeName); // set second param to type
			pstmt.setString(3, tDatePicker.toString()); // set third param to date
			pstmt.setString(4, tDesc); // set fourth param to description
			pstmt.setDouble(5, tPayment); // set fifth param to payment
			pstmt.setDouble(6, tDeposit); // set sixth param to deposit

			pstmt.executeUpdate(); // add transaction to database
			System.out.println("Transaction added to database"); // log transaction added
		} catch (SQLException e) {
			System.out.println("Error inserting data: " + e.getMessage());
		}
	}

	public static void removeTransaction(Transaction transaction) {
		// sql delete query
		String sql = "DELETE FROM transactions WHERE account_name = ? AND type_name = ? AND date_created = ? AND transaction_description = ? AND payment = ? AND deposit = ?";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {
			// Set the parameters
			pstmt.setString(1, transaction.getAccountName());
			pstmt.setString(2, transaction.getTypeName());
			pstmt.setString(3, transaction.getCreatedDate().toString());
			pstmt.setString(4, transaction.getTransactionName());
			pstmt.setDouble(5, transaction.getPayment());
			pstmt.setDouble(6, transaction.getDeposit());

			// execute update to remove type
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Type deleted from database: " + transaction.getTransactionName());
			} else {
				System.out.println("No match found to delete.");
			}
		} catch (SQLException e) {
			System.out.println("Error inserting data: " + e.getMessage());
		}
	}

	public static void updateTransaction(String prevDescription, String tAccountName, String tTypeName,
			LocalDate tDatePicker, String tDesc, double tPayment, double tDeposit) {

		// SQL query for updating the transaction
		String sql = "UPDATE transactions SET account_name = ?, type_name = ?, date_created = ?, transaction_description = ?, payment = ?, deposit = ? WHERE transaction_description = ?";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql)) {
			pstmt.setString(1, tAccountName); // Set account name
			pstmt.setString(2, tTypeName); // Set type name
			pstmt.setString(3, tDatePicker.toString()); // Set date
			pstmt.setString(4, tDesc); // Set description
			pstmt.setDouble(5, tPayment); // Set payment
			pstmt.setDouble(6, tDeposit); // Set deposit
			pstmt.setString(7, prevDescription); // Set transaction ID (used in WHERE clause)

			pstmt.executeUpdate(); // Update the transaction in the database
			System.out.println("Transaction updated in the database"); // Log success
		} catch (SQLException e) {
			System.out.println("Error updating data: " + e.getMessage());
		}
	}
}
