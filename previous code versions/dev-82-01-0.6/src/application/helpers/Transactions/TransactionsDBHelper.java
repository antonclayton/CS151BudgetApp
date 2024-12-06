package application.helpers.Transactions;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import application.singleton.DatabaseConnectionManager;

public class TransactionsDBHelper {

	private static final String URL = "jdbc:sqlite:src/application/database/transactions.db";

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

}
