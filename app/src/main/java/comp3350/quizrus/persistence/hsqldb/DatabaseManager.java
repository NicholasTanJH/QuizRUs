package comp3350.quizrus.persistence.hsqldb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

import comp3350.quizrus.application.Main;
import comp3350.quizrus.persistence.PersistenceException;

public class DatabaseManager {
    private static final String dbPath = Main.getDBPathName();

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }
}