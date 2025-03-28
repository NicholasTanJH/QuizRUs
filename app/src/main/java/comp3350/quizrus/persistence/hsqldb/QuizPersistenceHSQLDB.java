package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.persistence.PersistenceException;
import comp3350.quizrus.persistence.QuizPersistence;

public class QuizPersistenceHSQLDB implements QuizPersistence {
    public QuizPersistenceHSQLDB() {
    }

    /**
     * returns the quiz with the primary key quizID
     */
    @Override
    public Quiz getQuizByID(int quizID) {
        Quiz quiz = null;
        String query = "SELECT * FROM quiz WHERE quizID = ?";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            // query for quizzes with the inputted quizID
            pstmt.setInt(1, quizID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // create the object to return
                    quiz = buildQuizFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return quiz;
    }

    /**
     * returns a list of all quizzes
     */
    @Override
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quiz";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // create the list of objects to return
                Quiz quiz = buildQuizFromResultSet(rs);
                quizzes.add(quiz);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return quizzes;
    }

    /**
     * returns a list of quizzes with the inputted title (or similar to)
     */
    @Override
    public List<Quiz> getQuizzesByTitle(String quizTitle) {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quiz WHERE LOWER(title) LIKE LOWER(?)";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Execute the query and retrieve all questions.
            pstmt.setString(1, "%" + quizTitle + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // create the list of objects to return
                    Quiz curr_quiz = buildQuizFromResultSet(rs);
                    quizzes.add(curr_quiz);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return quizzes;
    }

    /**
     * using the objects variables to insert it into the database
     */
    @Override
    public int insertQuiz(final String title, final User user, final int timer) {
        int quizID = -1;
        String query = "INSERT INTO quiz (title, userID, timeLimit) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the values for the new quiz.
            pstmt.setString(1, title);
            pstmt.setInt(2, user.getUserID());
            pstmt.setInt(3, timer);

            // Execute the query, then check that the quiz was inserted.
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating quiz failed, no rows affected.");
            }

            // Get the auto-incremented quizID of the inserted quiz.
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    quizID = rs.getInt("quizID");
                }
            }

            return quizID;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * deletes a quiz from the database if the owner requested it
     */
    @Override
    public void deleteQuiz(Quiz quiz) {
        String query = "DELETE FROM quiz WHERE quizID = ?";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // query for the quiz that has the specified quizID
            pstmt.setInt(1, quiz.getQuizID());

            // Execute the query, then check that the quiz was deleted.
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting quiz failed, no quizzes were affected.");
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Builds and creates the needed object to return to the UI layers
     */
    private Quiz buildQuizFromResultSet(ResultSet rs) throws SQLException {
        int quizID = rs.getInt("quizID");
        String title = rs.getString("title");
        int userID = rs.getInt("userID");
        int timeLimit = rs.getInt("timeLimit");

        // Grab the user associated with the quiz.
        AccessUsers accessUsers = new AccessUsers();
        User user = accessUsers.getUser(userID);

        return new Quiz(quizID, title, user, timeLimit);
    }
}
