package application.helpers.TransactionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.singleton.DatabaseConnectionManager;

public class TransactionTypesDAO {
	private static final String URL = "jdbc:sqlite:src/application/database/transactionTypes.db"; // Your database URL

	public List<String> getAllTypes() {
		List<String> types = new ArrayList<>();

		// SQL query to select all from type table
		String sql = "SELECT * FROM transactionTypes";

		try (PreparedStatement pstmt = DatabaseConnectionManager.getConnection(URL).prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			// loop through result set
			while (rs.next()) {
				String typeName = rs.getString("transaction_type");

				types.add(typeName);
			}
		} catch (SQLException e) {
			System.out.println("Error retrieving types: " + e.getMessage());
		}
		return types; // returns all types in database
	}
}
