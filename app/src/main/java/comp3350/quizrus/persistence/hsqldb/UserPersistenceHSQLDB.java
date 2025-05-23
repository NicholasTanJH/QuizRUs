package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.PersistenceException;
import comp3350.quizrus.persistence.UserPersistence;

public class UserPersistenceHSQLDB implements UserPersistence {
    public UserPersistenceHSQLDB() {
    }

    /**
     * returns the user with the primary key userID
     */
    @Override
    public User getUserByID(int userID) {
        User user = null;
        String query = "SELECT * FROM user WHERE userID = ?";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            // query for users with the inputted userID
            pstmt.setInt(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // create the object to return
                    user = buildUserFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return user;
    }

    /**
     * returns the user with the unique inputted username
     */
    @Override
    public User getUserByUsername(final String username) {
        User user = null;
        String query = "SELECT * FROM user WHERE username = ?";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            // query for users with the inputted username
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // create the object to return
                    user = buildUserFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return user;
    }

    /**
     * returns a list of all users
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // create the list of objects to return
                User user = buildUserFromResultSet(rs);
                users.add(user);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return users;
    }

    /**
     * using the objects variables to insert it into the database
     */
    @Override
    public int insertUser(final String username, final String password, final String firstname, final String lastname) {
        int userID = -1;
        String query = "INSERT INTO user (username, password, firstname, lastname) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the values for the new user.
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, firstname);
            pstmt.setString(4, lastname);

            // Execute the query, then check that the user was inserted.
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Get the auto-incremented userID of the inserted user.
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    userID = rs.getInt("userID");
                }
            }

            return userID;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Builds and creates the needed object to return to the UI layers
     */
    private User buildUserFromResultSet(ResultSet rs) throws SQLException {
        int userID = rs.getInt("userID");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String firstname = rs.getString("firstname");
        String lastname = rs.getString("lastname");
        return new User(userID, username, password, firstname, lastname);
    }
}
