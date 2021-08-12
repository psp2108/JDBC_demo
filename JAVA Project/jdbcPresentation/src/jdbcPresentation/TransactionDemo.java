package jdbcPresentation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection_package.DbUtil;

public class TransactionDemo {
	private static Connection con = null;
	private static PreparedStatement updateAccount = null;
	private static PreparedStatement getBalanceSumQuery = null;

	public static void initialize() throws SQLException, FileNotFoundException, IOException {
		con = DbUtil.getConnection();
		con.setAutoCommit(false);

		// Preparing statement to get the sum of balance
		getBalanceSumQuery = con.prepareStatement("select sum(balance) from jdbc_bank where id = ? or id = ?");

		// Creating a statement to update balance
		updateAccount = con.prepareStatement("update jdbc_bank set balance = balance + ? where id=?");
	}

	public static int getBalanceSum(int accID1, int accID2) throws SQLException {
		getBalanceSumQuery.setInt(1, accID1);
		getBalanceSumQuery.setInt(2, accID2);

		ResultSet rs = getBalanceSumQuery.executeQuery();

		return (rs.next()) ? rs.getInt(1) : -1;
	}

	public static int updateBalance(int accID, float amount) throws SQLException {
		updateAccount.setFloat(1, amount);
		updateAccount.setInt(2, accID);
		return updateAccount.executeUpdate();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		initialize();
		
		int rowsAffected = 0;
		int from = 5002;
		int to = 5000;
		int amount = 123;
		int initialBalance = -1, newBalance = -1;
		
/*
 * TASK --> Transfer 123 rs from Vijai (5000) to Siddhartha (5002)
 * FIRSTLY CHECK IF VIJAI HAS ENOUGH BALANCE TO TRANSFER
 * (ASSUME THIS CONDITION IS SATISFIED)
 */

// Fetching initial sum of balance
		initialBalance = getBalanceSum(from, to);
		System.out.println("Initial Balance = " + initialBalance);

// Updating balance from sender's account
		rowsAffected = updateBalance(from, -amount);
		System.out.println("Rows affected for " + from + " = " + rowsAffected);
		rowsAffected=0;

// Updating balance to receiver's account
		rowsAffected = updateBalance(to, amount);
		System.out.println("Rows affected for " + to + " = " + rowsAffected);
		rowsAffected=0;

// Fetching final sum of balance
		newBalance = getBalanceSum(from, to);
		System.out.println("Initial Balance = " + newBalance);

// Check if the transaction is successful
		if (initialBalance == newBalance) {
			System.out.println("DB is consistent, commiting changes");
			con.commit();
		} else {
			System.out.println("DB is not consistent, rolling back operation");
			con.rollback();
		}

		con.close();
	}

}
