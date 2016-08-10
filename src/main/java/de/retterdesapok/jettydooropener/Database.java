package de.retterdesapok.jettydooropener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Singleton */
public class Database {

	public static final String DB_CONNECTION_STRING = "jdbc:sqlite:DoorOpener.sqlite";
	private static final String CREATE_STATEMENT_PATH = "src/main/resources/sql/create_database.sql";

	private static Database INSTANCE = null;

	private Connection connection;

	private Database() {
	    try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ensureDatabaseExists();
	}

	public synchronized static Database GET() {
		ensureInitialized();
		return INSTANCE;
	}

	private static void ensureInitialized() {
		if (INSTANCE == null) {
			INSTANCE = new Database();
		}
	}

	private void ensureDatabaseExists() {
		try (Connection connection = DriverManager.getConnection(DB_CONNECTION_STRING);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT 1 FROM USER");) {
		} catch (Exception e) {
			createNewDatabase();
		}
	}

	private void createNewDatabase() {
		try (Connection connection = DriverManager.getConnection(DB_CONNECTION_STRING);
				Statement statement = connection.createStatement();) {
			String createStatement = IOUtility.getFileContent(CREATE_STATEMENT_PATH);

			if (createStatement != null) {
				statement.execute(createStatement);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(DB_CONNECTION_STRING);
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
