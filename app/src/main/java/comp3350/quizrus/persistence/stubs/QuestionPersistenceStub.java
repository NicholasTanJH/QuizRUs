package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.QuestionPersistence;

public class QuestionPersistenceStub implements QuestionPersistence {
    private List<Question> questions;
    private int numQuestions;

    public QuestionPersistenceStub() {
        this.questions = new ArrayList<>();
        this.numQuestions = 0;

        // A quiz must be associated with a user.
        User user1 = new User(0, "demo", "Password0!", "Jessie", "Andrade");
        User user2 = new User(1, "kakashi", "Password1!", "Saige", "Santana");

        // A question must be associated with a quiz.
        Quiz quiz1 = new Quiz(0, "Flags of Countries", user1, 120);
        Quiz quiz2 = new Quiz(1, "Celebrity Partners", user2, 120);

        // Add questions for the first quiz.
        insertQuestion("Which of these countries have white in their flag?", quiz1, "MULTIPLE_CHOICE");
        insertQuestion("In which country was the first flag created?", quiz1, "MULTIPLE_CHOICE");
        insertQuestion("The flag of Canada is commonly known as", quiz1, "MULTIPLE_CHOICE");
        insertQuestion("How many stars are there on the flag of the United States of America?", quiz1, "MULTIPLE_CHOICE");

        // Add questions for the second quiz.
        insertQuestion("Who is the partner of J.K. Rowling?", quiz2, "MULTIPLE_CHOICE");
        insertQuestion("How many partners did Drake date in 2024?", quiz2, "MULTIPLE_CHOICE");
    }

    @Override
    public Question getQuestionByID(int questionID) {
        for (Question question : this.questions) {
            if (question.getQuestionID() == questionID) {
                return question;
            }
        }
        return null;
    }

    @Override
    public List<Question> getQuestionsForQuiz(Quiz quiz) {
        List<Question> quizQuestions = new ArrayList<>();

        for (Question question : this.questions) {
            if (question.getMyQuiz().equals(quiz)) {
                quizQuestions.add(question);
            }
        }
        return quizQuestions;
    }

    @Override
    public int insertQuestion(final String questionText, final Quiz quiz, final String questionType) {
        Question newQuestion = new Question(numQuestions, questionText, quiz, questionType);
        this.questions.add(newQuestion);
        this.numQuestions++;
        return newQuestion.getQuestionID();
    }
}
