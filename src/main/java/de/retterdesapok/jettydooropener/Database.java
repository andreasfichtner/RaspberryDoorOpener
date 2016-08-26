package de.retterdesapok.jettydooropener;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Singleton */
public class Database {

	public static String dbConnectionString = "jdbc:sqlite:DoorOpener.sqlite";
	private static final String CREATE_STATEMENT = " CREATE TABLE IF NOT EXISTS USER ( "
			+ " ID                INTEGER   PRIMARY KEY AUTOINCREMENT, " + " USERNAME          TEXT      NOT NULL, "
			+ " PASSWORDHASH      TEXT      NOT NULL, " + " FAILEDLOGINCOUNT  INTEGER, " + " REMAININGLOGINS   INTEGER "
			+ " );";

	private static Database INSTANCE = null;

	private Connection connection;

	private Database() {
		try {
			String currentFolder = new java.io.File( "." ).getCanonicalPath();
			dbConnectionString = "jdbc:sqlite:" + currentFolder + "/" + "DoorOpener.sqlite";
			System.out.println("Database connection string: "+ dbConnectionString);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ensureDatabaseExists();
	}
	
	public String getConnectionString() {
		return dbConnectionString;
	}

	public synchronized static Database GET() {
		ensureInitialized();
		return INSTANCE;
	}

	public static void ensureInitialized() {
		if (INSTANCE == null) {
			INSTANCE = new Database();
		}
	}

	private void ensureDatabaseExists() {
		try (Connection connection = DriverManager.getConnection(dbConnectionString);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT 1 FROM USER");) {
		} catch (Exception e) {
			createNewDatabase();
		}
	}

	private void createNewDatabase() {
		try (Connection connection = DriverManager.getConnection(dbConnectionString);
				Statement statement = connection.createStatement();) {
			statement.execute(CREATE_STATEMENT);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(dbConnectionString);
		}
		return connection;
	}

	public void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public Statement createStatement() throws SQLException {
		return getConnection().createStatement();
	}

	public PreparedStatement createPreparedStatement(String sql) throws SQLException {
		return getConnection().prepareStatement(sql);
	}
}
