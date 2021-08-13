package jdbcPresentation;

import java.sql.*;
import java.io.*;
import connection_package.*;

public class MetaDataDemo {

	public static void databaseMetadata() throws IOException, SQLException {
		Connection connection = DbUtil.getConnection();
		ResultSet resultSet = null;

		DatabaseMetaData dmd = connection.getMetaData();

		System.out.println("Database Product  -> " + dmd.getDatabaseProductName());
		System.out.println("Database Product Version -> " + dmd.getDatabaseProductVersion());
		System.out.println("Database DriverName -> " + dmd.getDriverName());

		String catalog = "jdbc_demo"; // Basically database name
		String schemaPattern = null;
		String tableNamePattern = null;
		String[] types = null; // table, view
		
		String columnNamePattern = null;

		resultSet = dmd.getTables(catalog, schemaPattern, tableNamePattern, types);

		System.out.println();
		System.out.println("Table List");
		System.out.println("--------------------------------");
		while (resultSet.next()) {
			System.out.println("Table Name: " + resultSet.getString("TABLE_NAME"));
			System.out.println("Table Type: " + resultSet.getString("TABLE_TYPE"));
			System.out.println("Table Schema: " + resultSet.getString("TABLE_SCHEM"));
			System.out.println("Database Name: " + resultSet.getString("TABLE_CAT"));
			System.out.println("--------------------------------");
		}

		System.out.println();
		System.out.println();

		tableNamePattern = "blob_and_clob";
		resultSet = dmd.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
		
		System.out.println();
		System.out.println("Column List from 'blob_and_clob' table");
		System.out.println("--------------------------------");
		while (resultSet.next()) {
			System.out.println("Column Name: " + resultSet.getString("COLUMN_NAME"));
			System.out.println("Column Size: " + resultSet.getInt("COLUMN_SIZE"));
			System.out.println("Ordinal Position: " + resultSet.getInt("ORDINAL_POSITION"));
			System.out.println("Data Type: " + resultSet.getString("TYPE_NAME"));
			System.out.println("--------------------------------");
		}
		
		connection.close();
	}

	public static void resultSetMetadata() throws IOException, SQLException {
		Connection connection = DbUtil.getConnection();
		PreparedStatement ps = connection.prepareStatement("select * from student");
		ResultSet resultSet = ps.executeQuery();
		
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int totalColumns = rsmd.getColumnCount();
		System.out.println("Total number of columns: " + totalColumns);
		
		System.out.println("\nColumn names: ");
		System.out.println("--------------------------------");
		for (int i = 1; i <= totalColumns; i++) {
			System.out.println(i + " ->");
			System.out.println("Column Name: " + rsmd.getColumnName(i));
			System.out.println("Column Type: " + rsmd.getColumnTypeName(i));
			System.out.println("Auto Increment: " + rsmd.isAutoIncrement(i));
			System.out.println("--------------------------------");
		}
	}

	public static void main(String[] args) throws IOException, SQLException {
		databaseMetadata();
//		resultSetMetadata();
	}

}
