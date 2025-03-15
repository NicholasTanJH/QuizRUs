package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import comp3350.quizrus.application.Main;

public class DatabaseManager {
    private static final String dbPath = Main.getDBPathName();

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }
}