package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.persistence.PersistenceException;
import comp3350.quizrus.persistence.QuestionPersistence;

public class QuestionPersistenceHSQLDB implements QuestionPersistence {
    public QuestionPersistenceHSQLDB() {
    }

    /**
     * returns the question with the primary key questionID
     */
    @Override
    public Question getQuestionByID(int questionID) {
        Question question = null;
        String query = "SELECT * FROM question WHERE questionID = ?";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Execute the query and retrieve the question.
            pstmt.setInt(1, questionID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    //create the object to return
                    question = buildQuestionFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return question;
    }

    /**
     * returns the list of questions for a specific quiz
     */
    @Override
    public List<Question> getQuestionsForQuiz(Quiz quiz) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM question where quizID = ?";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Execute the query and retrieve all questions.
            pstmt.setInt(1, quiz.getQuizID());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    //create the list of objects to return
                    Question curr_question = buildQuestionFromResultSet(rs);
                    questions.add(curr_question);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return questions;
    }

    /**
     * using the objects variables to insert it into the database
     */
    @Override
    public int insertQuestion(final String questionText, final Quiz quiz, final String questionType) {
        int questionID = -1;
        String query = "INSERT INTO question (questionText, quizID, questionType) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the values for the new question.
            pstmt.setString(1, questionText);
            pstmt.setInt(2, quiz.getQuizID());
            pstmt.setString(3, questionType);

            // Execute the query, then check that the question was inserted.
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating question failed, no rows affected.");
            }

            // Get the auto-incremented questionID of the inserted question.
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    questionID = rs.getInt("questionID");
                }
            }

            return questionID;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Builds and creates the needed object to return to the UI layers
     */
    private Question buildQuestionFromResultSet(ResultSet rs) throws SQLException {
        int questionID = rs.getInt("questionID");
        String questionText = rs.getString("questionText");
        int quizID = rs.getInt("quizID");
        String questionType = rs.getString("questionType");

        // Grab the quiz associated with the question.
        AccessQuizzes accessQuizzes = new AccessQuizzes();
        Quiz quiz = accessQuizzes.getQuiz(quizID);

        return new Question(questionID, questionText, quiz, questionType);
    }
}
