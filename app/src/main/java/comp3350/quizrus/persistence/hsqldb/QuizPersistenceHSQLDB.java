package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.persistence.QuizPersistence;

public class QuizPersistenceHSQLDB implements QuizPersistence {
    private final DatabaseHandler dbHandler;

    public QuizPersistenceHSQLDB() {
        this.dbHandler = new DatabaseHandler();
    }

    public QuizPersistenceHSQLDB(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quiz";

        try (Connection conn = this.dbHandler.connection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Quiz quiz = buildQuizFromResultSet(rs);
                quizzes.add(quiz);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return quizzes;
    }

    @Override
    public List<Quiz> getUserQuizzes(User user) {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quiz WHERE userID = ?";

        try (Connection conn = this.dbHandler.connection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            pstmt.setInt(1, user.getUserID());

            while (rs.next()) {
                Quiz curr_quiz = buildQuizFromResultSet(rs);
                quizzes.add(curr_quiz);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return quizzes;
    }

    @Override
    public int insertQuiz(Quiz quiz, User user) {
        int quizID = -1;
        String query = "INSERT INTO quiz (title, userID) VALUES (?, ?)";

        try (Connection conn = this.dbHandler.connection();
             PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the values for the new quiz.
            pstmt.setString(1, quiz.getTitle());
            pstmt.setInt(2, user.getUserID());

            // Execute the query, then check that the user was inserted.
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

    private Quiz buildQuizFromResultSet(ResultSet rs) throws SQLException {
        int quizID = rs.getInt("quizID");
        String title = rs.getString("title");
        int userID = rs.getInt("userID");

        // Grab the user associated with the quiz.
        UserPersistenceHSQLDB userPersistence = new UserPersistenceHSQLDB(this.dbHandler);
        User user = userPersistence.getUserByID(userID);

        return new Quiz(quizID, title, user);
    }
}
