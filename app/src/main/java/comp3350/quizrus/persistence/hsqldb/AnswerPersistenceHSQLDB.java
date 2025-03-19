package comp3350.quizrus.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.business.AccessQuestions;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.persistence.AnswerPersistence;
import comp3350.quizrus.persistence.PersistenceException;

public class AnswerPersistenceHSQLDB implements AnswerPersistence {
    public AnswerPersistenceHSQLDB() {
    }

    @Override
    public List<Answer> getAnswersForQuestions(Question question) {
        List<Answer> answers = new ArrayList<>();
        String query = "SELECT * FROM answer where questionID = ?";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, question.getQuestionID());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Answer curr_answer = buildAnswerFromResultSet(rs);
                    answers.add(curr_answer);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return answers;
    }

    @Override
    public int insertAnswer(Answer answer, Question question) {
        int answerID = -1;
        String query = "INSERT INTO answer (answerText, isCorrect, questionID) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.connection();
                PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the values for the new answer.
            pstmt.setString(1, answer.getAnswerText());
            pstmt.setBoolean(2, answer.isCorrect());
            pstmt.setInt(3, question.getQuestionID());

            // Execute the query, then check that the answer was inserted.
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating answer failed, no rows affected.");
            }

            // Get the auto-incremented answerID of the inserted answer.
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    answerID = rs.getInt("answerID");
                }
            }

            return answerID;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Answer buildAnswerFromResultSet(ResultSet rs) throws SQLException {
        int answerID = rs.getInt("answerID");
        String answerText = rs.getString("answerText");
        boolean isCorrect = rs.getBoolean("isCorrect");
        int questionID = rs.getInt("questionID");

        // Grab the question associated with the answer.
        AccessQuestions accessQuestions = new AccessQuestions();
        Question question = accessQuestions.getQuestion(questionID);

        return new Answer(answerID, answerText, isCorrect, question);
    }
}
