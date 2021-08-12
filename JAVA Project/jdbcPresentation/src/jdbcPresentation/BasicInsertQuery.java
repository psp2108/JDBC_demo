package jdbcPresentation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection_package.DbUtil;

public class BasicInsertQuery {

	public static void main(String[] args) throws IOException, SQLException {
		Connection connection = DbUtil.getConnection();

		PreparedStatement prepareStatement = connection
				.prepareStatement("insert student (name, phone, final_cgpi, dept) values (?,?,?,?)");
		prepareStatement.setString(1, "New Student");
		prepareStatement.setString(2, "9999988888");
		prepareStatement.setFloat(3, 10);
		prepareStatement.setInt(4, 101);

		int numberOfRowsAffected = prepareStatement.executeUpdate();
		System.out.println("Number of rows affected: " + numberOfRowsAffected);
		
		connection.close();
	}

}
