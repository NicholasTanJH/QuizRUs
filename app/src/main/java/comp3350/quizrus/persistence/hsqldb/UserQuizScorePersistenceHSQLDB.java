package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    private final String dbPath;

    public UserQuizScorePersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public List<UserQuizScore> getScoresForQuiz(Quiz quiz) {
        List<UserQuizScore> userQuizScores = new ArrayList<>();
        String query = "SELECT * FROM user_quiz_score WHERE quizID = ?"
                + "ORDER BY score DESC";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, quiz.getQuizID());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserQuizScore userQuizScore = buildUserQuizScoreFromRS(rs);
                    userQuizScores.add(userQuizScore);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return userQuizScores;
    }

    @Override
    public List<UserQuizScore> getScoresForUser(User user) {
        List<UserQuizScore> userQuizScores = new ArrayList<>();
        String query = "SELECT * FROM user_quiz_score WHERE userID = ?"
                + "ORDER BY score DESC";

        try (Connection conn = DatabaseManager.connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, user.getUserID());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserQuizScore userQuizScore = buildUserQuizScoreFromRS(rs);
                    userQuizScores.add(userQuizScore);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return userQuizScores;
    }

    @Override
    public int insertScore(UserQuizScore userQuizScore, User user, Quiz quiz) {
        int userQuizScoreID = -1;
        String query = "INSERT INTO user_quiz_score (userID, quizID, score, numCorrect, timeElapsed, timeAdded) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the values for the user's quiz score.
            pstmt.setInt(1, user.getUserID());
            pstmt.setInt(2, quiz.getQuizID());
            pstmt.setInt(3, userQuizScore.getScore());
            pstmt.setInt(4, userQuizScore.getNumCorrect());
            pstmt.setInt(5, userQuizScore.getTimeElapsed());
            pstmt.setTimestamp(6, userQuizScore.getTimeAdded());

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

    private UserQuizScore buildUserQuizScoreFromRS(ResultSet rs) throws SQLException {
        int userQuizScoreID = rs.getInt("userQuizScoreID");
        int userID = rs.getInt("userID");
        int quizID = rs.getInt("quizID");
        int score = rs.getInt("score");
        int numCorrect = rs.getInt("numCorrect");
        int timeElapsed = rs.getInt("timeElapsed");
        Timestamp timeAdded = rs.getTimestamp("timeAdded");

        AccessUsers accessUsers = new AccessUsers();
        User user = accessUsers.getUser(userID);

        AccessQuizzes accessQuizzes = new AccessQuizzes();
        Quiz quiz = accessQuizzes.getQuiz(quizID);

        return new UserQuizScore(userQuizScoreID, user, quiz, numCorrect, timeElapsed, score, timeAdded);
    }
}
