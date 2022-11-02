package com.sauce.demo.automation.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.testng.Assert;

public class DatabaseHelpers {
	public static Logger log = Logger.getLogger(DatabaseHelpers.class);

	/**
	 * Function Name: databaseConnect
	 * Description: This function is used to connect various types of database and return the connection
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 14/10/2020
	 * @param dbType - Need to specify the type of the database for which connection is required
	 * @param hostName - This string argument holds database server name
	 * @param portNo - This string argument holds port number of the mentioned server
	 * @param dbName - This string argument holds database name
	 * @param userName - This string argument holds database connection username
	 * @param password - This string argument holds database connection user password
	 * @return con - database connection
	 * Usage: Connection con = databaseConnect("oracle", "10.63.17.70", "1521", "pefd_pdb", "TALEND", "TALEND");
	 */
	public static Connection databaseConnect(String dbType, String hostName, String portNo, String dbName, String userName, String password) throws Exception {
		// DB Connection
		Connection con = null;
		String dbClassName = "";
		String dbUrl = "";
		switch (dbType.toLowerCase().trim()) {
		case "oracle":
			dbClassName = "oracle.jdbc.driver.OracleDriver";
			dbUrl = "jdbc:oracle:thin:@" + hostName.trim() + ":" + portNo.trim() + "/" + dbName.trim();
			break;
		case "mysql":
			dbClassName = "com.mysql.jdbc.Driver";
			dbUrl = "jdbc:mysql://" + hostName.trim() + ":" + portNo.trim() + "/" + dbName.trim();
			break;
		case "db2":
			dbClassName = "COM.ibm.db2.jdbc.jcc.DB2Driver";
			dbUrl = "jdbc:db2:" + hostName.trim() + ":" + portNo.trim() + "/" + dbName.trim();
			break;
		case "sybase":
			dbClassName = "com.sybase.jdbc.SybDriver";
			dbUrl = "jdbc:sybase:Tds:" + hostName.trim() + ":" + portNo.trim() + "/" + dbName.trim();
			break;
		default:
			log.info("Incorrect Database Name");
			Assert.fail("\nIncorrect Database Name");
		}
		try {
			// Load the driver
			Class.forName(dbClassName);
			// Create the connection
			con = DriverManager.getConnection(dbUrl, userName.trim(), password.trim());
			log.info("Database connection established successfull. Database Name: " + dbName);
		} catch (ClassNotFoundException e) {
			log.info("Could not load driver: " + e.getMessage());
		} catch (SQLException e) {
			log.info("Could not connect to DB: " + e.getMessage());
		}
		return con;
	}	
	
	/**
	 * Function Name: dbExecQuery
	 * Description: This function is used to execute any database query and return the result set
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 15/10/2020
	 * @param con - This parameter holds database connection
	 * @param execQuery  - This parameter holds query which need to be executed
	 * @return ResultSet
	 * Usage: ResultSet rs = dbExecQuery(Connection con, "query");
	 */
	public static ResultSet dbExecQuery(Connection con, String execQuery) throws Exception {
		ResultSet rs = null;
		try {
		Statement stmt = (Statement) con.createStatement();
		rs = stmt.executeQuery(execQuery);		
		}
		catch (SQLException e) {
			log.info("Could not execute database query: " + e.getMessage());
		}
		return rs;
	}
}
