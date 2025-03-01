//package comp3350.quizrus.persistence.hsqldb;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;
//import java.sql.SQLException;
//
//public class DatabaseManager {
//    private static final String DEFAULT_DB_PATH = "src/main/java/comp3350/quizrus/persistence/hsqldb/db/quizrusdb";
//    private static final String DEFAULT_INIT_PATH = "src/main/assets/db/init.sql";
//    private static final String DEFAULT_DROP_TABLES_PATH = "src/main/assets/db/drop_tables.sql";
//
//    private final String dbPath;
//    private final String initPath;
//    private final String dropTablesPath;
//
//    public DatabaseManager(String dbPath, String initPath, String dropTablesPath) {
//        this.dbPath = (dbPath != null) ? getAbsPath(dbPath) : getAbsPath(DEFAULT_DB_PATH);
//        this.initPath = (initPath != null) ? getAbsPath(initPath) : getAbsPath(DEFAULT_INIT_PATH);
//        this.dropTablesPath = (dropTablesPath != null) ? getAbsPath(dropTablesPath)
//                : getAbsPath(DEFAULT_DROP_TABLES_PATH);
//        initializeTables();
//    }
//
//    public DatabaseManager() {
//        // Call the three-parameter constructor with nulls for all values
//        // by default.
//        this(null, null, null);
//    }
//
//    public void initializeTables() {
//        executeSQLFromFile(this.initPath);
//        System.out.println("Database initialized.");
//    }
//
//    public void dropTables() {
//        executeSQLFromFile(this.dropTablesPath);
//        System.out.println("All tables dropped.");
//    }
//
//    public Connection connection() throws SQLException {
//        return DriverManager.getConnection("jdbc:hsqldb:file:" + this.dbPath + ";shutdown=true", "SA", "");
//    }
//
//    private void executeSQLFromFile(String filePath) {
//        Connection conn = null;
//        try {
//            conn = connection();
//
//            // Begin a transaction.
//            conn.setAutoCommit(false);
//
//            StringBuilder sql = new StringBuilder();
//            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    sql.append(line).append("\n");
//                }
//            }
//
//            try (Statement stmt = conn.createStatement()) {
//                // Execute the query.
//                stmt.execute(sql.toString());
//            }
//
//            // Commit the transaction.
//            conn.commit();
//
//        } catch (SQLException | IOException e) {
//            if (conn != null) {
//                // Attempt to rollback any changes if we encounter an error.
//                try {
//                    conn.rollback();
//                } catch (SQLException err) {
//                    System.err.println("Failed to rollback transaction: " + err.getMessage());
//                }
//            }
//            throw new PersistenceException(e);
//        } finally {
//            // Restore auto-commit mode and close the connection.
//            if (conn != null) {
//                try {
//                    conn.setAutoCommit(true);
//                    conn.close();
//                } catch (SQLException e) {
//                    System.err
//                            .println("Failed to reset auto-commit to true or close the connection: " + e.getMessage());
//                }
//            }
//        }
//    }
//
//    private String getAbsPath(String path) {
//        return new File(path).getAbsolutePath();
//    }
//}
