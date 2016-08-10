package de.retterdesapok.jettydooropener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
	
	private static final String SQL_SELECT_USER = "SELECT ID, USERNAME, PASSWORDHASH, FAILEDLOGINCOUNT, REMAININGLOGINS "
			+ " FROM USER "
			+ " WHERE USERNAME = :1 "
			+ " AND PASSWORDHASH = :2 ";
	
	private static final String SQL_INCREMENT_FAILED_LOGIN_COUNT_FOR_USER = "UPDATE USER SET FAILEDLOGINCOUNT = FAILEDLOGINCOUNT + 1 WHERE USERNAME = :1";
	
	public static User loginUserWithNameAndPasswordHash(String username, String passwordhash) throws SQLException {
		try (PreparedStatement statement = Database.GET().createPreparedStatement(SQL_SELECT_USER)) {
			statement.setString(1, username);
			statement.setString(2, passwordhash);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				User user = new User(result.getInt("ID"), 
						result.getString("USERNAME"), 
						result.getString("PASSWORDHASH"),
						result.getInt("FAILEDLOGINCOUNT"), 
						result.getInt("REMAININGLOGINS"));
				
				return user;
			}
		}
		
		// No user has been found with this combination, increment failed login counter if user exists
		incrementUsersFailedLogins(username);
		
		return null;
	}
	
	public static void incrementUsersFailedLogins(String username) throws SQLException {
		try (PreparedStatement statement = Database.GET().createPreparedStatement(SQL_INCREMENT_FAILED_LOGIN_COUNT_FOR_USER)) {
			statement.setString(1, username);
			statement.executeUpdate();
		}
	}
}
