package comp3350.quizrus.persistence.hsqldb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

import comp3350.quizrus.application.Main;

public class DatabaseManager {
    private static final String dbPath = Main.getDBPathName();

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    public static void executeSQLFromFile(String filePath) {
        Connection conn = null;
        try {
            conn = connection();

            // Begin a transaction.
            conn.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sql.append(line).append("\n");
                }
            }

            try (Statement stmt = conn.createStatement()) {
                // Execute the query.
                stmt.execute(sql.toString());
            }

            // Commit the transaction.
            conn.commit();

        } catch (SQLException | IOException e) {
            if (conn != null) {
                // Attempt to rollback any changes if we encounter an error.
                try {
                    conn.rollback();
                } catch (SQLException err) {
                    System.err.println("Failed to rollback transaction: " + err.getMessage());
                }
            }
            throw new PersistenceException(e);
        } finally {
            // Restore auto-commit mode and close the connection.
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err
                            .println("Failed to reset auto-commit to true or close the connection: " + e.getMessage());
                }
            }
        }
    }
}