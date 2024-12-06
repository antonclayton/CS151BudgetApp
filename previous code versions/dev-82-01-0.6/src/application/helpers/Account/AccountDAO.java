package application.helpers.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.model.Account;
import application.singleton.DatabaseConnectionManager;

public class AccountDAO {
	private static final String URL = "jdbc:sqlite:src/application/database/accounts.db"; // Your database URL

	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<>();

		// SQL query to select all from accounts table
		String sql = "SELECT * FROM accounts";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			// loop through result set
			while (rs.next()) {
				String accountName = rs.getString("account_name");
				double balance = rs.getDouble("balance");
				LocalDate dateCreated = LocalDate.parse(rs.getString("date_created"));

				Account account = new Account(accountName, balance, dateCreated);
				// new account created and added to arraylist to display in accountPage
				accounts.add(account);
			}
		} catch (SQLException e) {
			System.out.println("Error retrieving accounts: " + e.getMessage());
		}
		return accounts; // returns all accounts in database
	}
}
