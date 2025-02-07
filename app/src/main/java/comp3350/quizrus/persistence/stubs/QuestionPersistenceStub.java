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

        User user1 = new User("1", "kakashi", "password1");
        Quiz quiz1 = new Quiz("1", "Flags of Countries", user1);
        Quiz quiz2 = new Quiz("2", "Celebrity Partners", user1);

        questions.add(new Question("Which of these countries has a white flag?", quiz1));
        questions.add(new Question("In which country was the first flag created?", quiz1));
        questions.add(new Question("Who is Kim Kardashian's current partner?", quiz1));
        questions.add(new Question("How many people has The Weekend dated?", quiz2));
        questions.add(new Question("Beyonce is married to who?s", quiz2));
    }

    @Override
    public List<Question> getQuestionsForQuiz(Quiz quiz) {
        List<Question> quizQuestions = new ArrayList<>();

        for (Question question : questions) {
            if (question.getMyQuiz().equals(quiz)) {
                quizQuestions.add(question);
            }
        }
        return Collections.unmodifiableList(quizQuestions);
    }
}
