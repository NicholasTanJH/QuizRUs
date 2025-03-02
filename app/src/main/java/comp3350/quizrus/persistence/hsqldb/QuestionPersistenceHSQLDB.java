package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.business.AccessQuestions;
import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.QuestionPersistence;


public class QuestionPersistenceHSQLDB implements QuestionPersistence {
    private final String dbPath;

    public QuestionPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }
    @Override
    public Question getQuestionByID(int questionID){
        Question question = null;
        String query = "SELECT * FROM question WHERE questionID = ?";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, questionID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    question = buildQuestionFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return question;
    }

    @Override
    public List<Question> getQuestionsForQuiz(Quiz quiz)
    {
        List <Question> questions = new ArrayList<>();
        String query = "SELECT * FROM question where quizID = ?";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            pstmt.setInt(1, quiz.getQuizID());

            while (rs.next()) {
                Question curr_question = buildQuestionFromResultSet(rs);
                questions.add(curr_question);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return questions;
    }

    @Override
    public int insertQuestion(Question question, Quiz quiz) {
        int questionID = -1;
        String query = "INSERT INTO question (questionText, questionType, quizID) VALUES (?, ?, ?)";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the values for the new question.
            pstmt.setString(1, question.getQuestionText());
            pstmt.setString(2, question.getQuestionType());
            pstmt.setInt(3, quiz.getQuizID());

            // Execute the query, then check that the question was inserted.
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating question failed, no rows affected.");
            }

            // Get the auto-incremented questionID of the inserted question
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

    private Question buildQuestionFromResultSet(ResultSet rs) throws SQLException{
        int questionID = rs.getInt("questionID");
        String questionText = rs.getString("questionText");
        String questionType = rs.getString("questionType");
        int quizID = rs.getInt("quizID");

        // Grab the quiz associated with the question.
        AccessQuizzes accessQuizzes = new AccessQuizzes();
        Quiz quiz = accessQuizzes.getQuiz(quizID);

        return new Question(questionID, questionText, quiz, questionType);
    }
}
