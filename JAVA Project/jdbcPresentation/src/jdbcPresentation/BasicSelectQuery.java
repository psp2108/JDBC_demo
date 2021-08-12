package jdbcPresentation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection_package.DbUtil;

public class BasicSelectQuery {

	public static void main(String[] args) throws IOException, SQLException {
		Connection connection = DbUtil.getConnection();

		Statement statement = connection.createStatement();

		ResultSet resultSet = statement.executeQuery("select * from student");

		while (resultSet.next()) {
			System.out.print(resultSet.getString("stud_id") + ", ");
			System.out.print(resultSet.getString("name") + ", ");
			System.out.print(resultSet.getString(3) + ", ");
			System.out.print(resultSet.getString(4) + ", ");
			System.out.print(resultSet.getString(5));
			System.out.println();
		}

		connection.close();
	}

}
