package jdbcPresentation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection_package.DbUtil;

public class BlobAndClobDemo {

	public static void insertBLOBFile(String inputFileName) throws SQLException, IOException {
		FileInputStream fileInputStream = new FileInputStream(inputFileName);

		Connection connection = DbUtil.getConnection();
		// Data type used in MySQL is 'BLOB'
		String query = "insert into blob_and_clob (file_name, binary_file) values (?,?)";
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setString(1, "Image File 1");
		prepareStatement.setBinaryStream(2, fileInputStream); // ***

		int rowsAffected = prepareStatement.executeUpdate();
		System.out.println("Rows Affected = " + rowsAffected);

		connection.close();
		fileInputStream.close();
	}

	public static void insertCLOBFile(String inputFileName) throws IOException, SQLException {
		FileReader fileReader = new FileReader(inputFileName);

		Connection connection = DbUtil.getConnection();
		// Data type used in MySQL is 'LONGTEXT'
		String query = "insert into blob_and_clob (file_name, text_file) values (?,?)";
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setString(1, "Image File 1");
		prepareStatement.setCharacterStream(2, fileReader); // ***

		int rowsAffected = prepareStatement.executeUpdate();
		System.out.println("Rows Affected = " + rowsAffected);

		connection.close();
		fileReader.close();
	}

	public static void fetchBLOBFile(String outputFileName) throws IOException, SQLException {
		Connection connection = DbUtil.getConnection();

		String query = "select binary_file from blob_and_clob where binary_file is not NULL";
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		ResultSet resultSet = prepareStatement.executeQuery();

		if (resultSet.next()) {
			InputStream inputstream = resultSet.getBinaryStream(1); // ***

			FileOutputStream fileOutputstreamfos = new FileOutputStream(outputFileName);
			byte[] buffer = new byte[1024];

			while ((inputstream.read(buffer)) > 0) {
				fileOutputstreamfos.write(buffer);
			}

			fileOutputstreamfos.close();
		}
		
		connection.close();
	}

	public static void fetchCLOBFile(String outputFileName) throws IOException, SQLException {
		Connection connection = DbUtil.getConnection();

		String query = "select text_file from blob_and_clob where text_file is not NULL";
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		ResultSet resultSet = prepareStatement.executeQuery();

		if (resultSet.next()) {
			Reader reader = resultSet.getCharacterStream(1); // ***

			FileWriter fileWriter = new FileWriter(outputFileName);
			int bufferCharacter;

			while ((bufferCharacter = reader.read()) > 0) {
				fileWriter.write(bufferCharacter);
			}

			fileWriter.close();
		}
		
		connection.close();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		insertBLOBFile("files/binary file.png");
		fetchBLOBFile("files/from db.png");
		insertCLOBFile("files/character file.txt");
		fetchCLOBFile("files/from db.txt");
	}
}
