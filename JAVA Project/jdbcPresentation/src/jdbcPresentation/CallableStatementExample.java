package jdbcPresentation;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection_package.DbUtil;

public class CallableStatementExample {

	public static void simpleProcedure()  throws IOException, SQLException{
		Connection connection = DbUtil.getConnection();
		
		CallableStatement callableStatement = connection.prepareCall("{call display_students_with_departments()}");
		
		ResultSet resultSet = callableStatement.executeQuery();
		
		while(resultSet.next()) {
			System.out.println(resultSet.getString(1) + ", " +resultSet.getString(2) + ", " +resultSet.getString(3) + ", " +resultSet.getString(4) + ", " +resultSet.getString(5));
		}
		
		connection.close();		
	}
	
	public static void procedureWithINParameter()  throws IOException, SQLException{
		Connection connection = DbUtil.getConnection();
		
		CallableStatement callableStatement = connection.prepareCall("{call just_echo(?)}");
		callableStatement.setString(1, "From JDBC");
		
		ResultSet resultSet = callableStatement.executeQuery();
		
		if(resultSet.next()) {
			System.out.println(resultSet.getString(1));
		}
		
		connection.close();		
		
	}
	
	public static void procedureWithOUTParameter()  throws IOException, SQLException{
		Connection connection = DbUtil.getConnection();
		
		CallableStatement callableStatement = connection.prepareCall("{call cal_sum(?,?,?)}");
		callableStatement.setInt(1, 35);
		callableStatement.setInt(2, 15);
		callableStatement.registerOutParameter(3, JDBCType.INTEGER);	// **
		
		callableStatement.execute();
		
		int outputSum = callableStatement.getInt(3);		// **
		System.out.println("Output is : " + outputSum);
		
		connection.close();		
		
	}

	public static void main(String[] args) throws IOException, SQLException {
//		simpleProcedure();
//		procedureWithINParameter();
		procedureWithOUTParameter();
	}

}
