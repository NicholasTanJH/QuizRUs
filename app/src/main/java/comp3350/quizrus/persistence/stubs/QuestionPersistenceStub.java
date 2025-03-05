package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.QuestionPersistence;

public class QuestionPersistenceStub implements QuestionPersistence {
    private List<Question> questions;

    public QuestionPersistenceStub() {
        this.questions = new ArrayList<>();

        // A quiz must be associated with a user.
        User user1 = new User(0, "demo", "Password0!", "Jessie", "Andrade");
        User user2 = new User(1, "kakashi", "Password1!", "Saige", "Santana");

        // A question must be associated with a quiz.
        Quiz quiz1 = new Quiz(0, "Flags of Countries", user1);
        Quiz quiz2 = new Quiz(1, "Celebrity Partners", user2);

        // Add questions for the first quiz.
        questions.add(new Question(0, "Which of these countries have white in their flag?", quiz1, "MULTIPLE_CHOICE"));
        questions.add(new Question(1, "In which country was the first flag created?", quiz1, "MULTIPLE_CHOICE"));
        questions.add(new Question(2, "The flag of Canada is commonly known as", quiz1, "MULTIPLE_CHOICE"));
        questions.add(new Question(3, "How many stars are there on the flag of the United States of America?", quiz1,
                "MULTIPLE_CHOICE"));
        questions.add(
                new Question(4, "The flag of Australia contains which other country’s flag in its top left corner?",
                        quiz1, "MULTIPLE_CHOICE"));

        // Add questions for the second quiz.
        questions.add(new Question(5, "Who is the partner of J.K. Rowling?", quiz2, "MULTIPLE_CHOICE"));
        questions.add(new Question(6, "How many partners did Drake date in 2024?", quiz2, "MULTIPLE_CHOICE"));
    }

    @Override
    public Question getQuestionByID(int questionID) {
        return null;
    }

    @Override
    public List<Question> getQuestionsForQuiz(Quiz quiz) {
        List<Question> quizQuestions = new ArrayList<>();

        for (Question question : questions) {
            if (question.getMyQuiz().equals(quiz)) {
                quizQuestions.add(question);
            }
        }
        return quizQuestions;
    }

    @Override
    public int insertQuestion(Question question, Quiz quiz) {
        return -1;
    }
}
