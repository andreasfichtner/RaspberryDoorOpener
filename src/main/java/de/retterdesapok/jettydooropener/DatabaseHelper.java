package de.retterdesapok.jettydooropener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

	public static String DATABASE_NAME = "~/DoorOpener.sqlite";
	
	public static void ensureDatabaseExists() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
 
        String makeUsersTable = "CREATE TABLE IF NOT EXISTS users (id integer, passwordhash text, remaininglogins integer)";
        String makeLogTable = "CREATE TABLE IF NOT EXISTS logs (id integer, userid integer, time text, success text)";

        Connection conn = DriverManager.getConnection("jdbc:sqlite" + ":" + DATABASE_NAME);

        try {
            Statement stmt = conn.createStatement();
            try {
                stmt.setQueryTimeout(30);
                stmt.executeUpdate(makeUsersTable);
                stmt.executeUpdate(makeLogTable);
            } finally {
                try { stmt.close(); } catch (Exception ignore) {}
            }
        } finally {
            try { conn.close(); } catch (Exception ignore) {}
        }
	}
}
