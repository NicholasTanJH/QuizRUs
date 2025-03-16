package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    public List<UserQuizScore> getLeaderboard(Quiz quiz) {
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
    public int insertScore(User user, Quiz quiz, int score) {
        int userQuizScoreID = -1;
        String query = "INSERT INTO user_quiz_score (userID, quizID, score) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the values for the user's quiz score.
            pstmt.setInt(1, user.getUserID());
            pstmt.setInt(2, quiz.getQuizID());
            pstmt.setInt(3, score);

            // Execute the query, then check that the user was inserted.
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating a user's quiz score failed.");
            }

            // Get the auto-incremented ID of the inserted score.
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt("ID");
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
        Timestamp timeAdded = rs.getTimestamp("timeAdded");
        return new UserQuizScore(userQuizScoreID, userID, quizID, score, timeAdded);
    }
}
