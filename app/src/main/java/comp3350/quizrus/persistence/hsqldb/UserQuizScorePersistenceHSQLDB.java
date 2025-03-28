package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.persistence.PersistenceException;
import comp3350.quizrus.persistence.UserQuizScorePersistence;

public class UserQuizScorePersistenceHSQLDB implements UserQuizScorePersistence {
    public UserQuizScorePersistenceHSQLDB() {
    }

    /**
     * returns a list of scores for a quiz
     */
    @Override
    public List<UserQuizScore> getScoresForQuiz(Quiz quiz, int numEntries) {
        List<UserQuizScore> userQuizScores = new ArrayList<>();
        String query = "SELECT * FROM user_quiz_score WHERE quizID = ?"
                + "ORDER BY score DESC LIMIT ?";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            //find the top numEntries scores for a quiz
            pstmt.setInt(1, quiz.getQuizID());
            pstmt.setInt(2, numEntries);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    //create the list of objects to return
                    UserQuizScore userQuizScore = buildUserQuizScoreFromRS(rs);
                    userQuizScores.add(userQuizScore);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return userQuizScores;
    }

    /**
     * returns the highest score from a user's attempt on a specific quiz
     */
    @Override
    public int getUserHighScore(Quiz quiz, User user)
    {
        String query = "SELECT MAX(score) FROM user_quiz_score WHERE quizID = ? AND userID = ?";

        try (Connection conn = DatabaseManager.connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            //query the score from a specific attempt on a quiz
            pstmt.setInt(1, quiz.getQuizID());
            pstmt.setInt(2, user.getUserID());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    if(rs.wasNull())
                    {
                        return 0;
                    }
                    else
                    {
                        //return the value of the user's highest score on the quiz
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return 0;
    }

    /**
     * return the number of attempts a user made on a quiz
     */
    @Override
    public int getNumAttempts(Quiz quiz, User user)
    {
        String query = "SELECT COUNT(*) FROM user_quiz_score WHERE quizID = ? AND userID = ?";

        try (Connection conn = DatabaseManager.connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            //query the scores for the number of attempts
            pstmt.setInt(1, quiz.getQuizID());
            pstmt.setInt(2, user.getUserID());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    //return the number of scores a user has recorded
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return 0;
    }

    /**
     * using the objects variables to insert it into the database
     */
    @Override
    public int insertScore(final User user, final Quiz quiz, final int numCorrect, final int timeTaken, final int score) {
        int userQuizScoreID = -1;
        String query = "INSERT INTO user_quiz_score (userID, quizID, numCorrect, timeTaken, score) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the values for the user's quiz score.
            pstmt.setInt(1, user.getUserID());
            pstmt.setInt(2, quiz.getQuizID());
            pstmt.setInt(3, numCorrect);
            pstmt.setInt(4, timeTaken);
            pstmt.setInt(5, score);

            // Execute the query, then check that the user was inserted.
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating a user's quiz score failed.");
            }

            // Get the auto-incremented ID of the inserted score.
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    userQuizScoreID = rs.getInt("userQuizScoreID");
                }
            }

            return userQuizScoreID;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Builds and creates the needed object to return to the UI layers
     */
    private UserQuizScore buildUserQuizScoreFromRS(ResultSet rs) throws SQLException {
        int userQuizScoreID = rs.getInt("userQuizScoreID");
        int userID = rs.getInt("userID");
        int quizID = rs.getInt("quizID");
        int numCorrect = rs.getInt("numCorrect");
        int timeTaken = rs.getInt("timeTaken");
        int score = rs.getInt("score");

        // Get the associated user.
        AccessUsers accessUsers = new AccessUsers();
        User user = accessUsers.getUser(userID);

        // Get the associated quiz.
        AccessQuizzes accessQuizzes = new AccessQuizzes();
        Quiz quiz = accessQuizzes.getQuiz(quizID);

        return new UserQuizScore(userQuizScoreID, user, quiz, numCorrect, timeTaken, score);
    }
}
