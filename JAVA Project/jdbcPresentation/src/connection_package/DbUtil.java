package connection_package;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil{
	protected static String propertiesFile = "mycred.properties";

	protected static String dburl = "";
	protected static String username = "";
	protected static String password = "";

	protected static void loadProperties() throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileInputStream("mycreds.properties"));

		dburl = p.getProperty("dburl");
		username = p.getProperty("uname");
		password = p.getProperty("password");
	}


	public static Connection getConnection() throws FileNotFoundException, IOException, SQLException {
		DbUtil.loadProperties();
		Connection con = DriverManager.getConnection(dburl, username, password);
		return con;
	}

}
