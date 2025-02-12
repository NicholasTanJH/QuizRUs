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

        User user1 = new User(1, "kakashi", "password1");
        Quiz quiz1 = new Quiz(1, "Flags of Countries", user1);
        Quiz quiz2 = new Quiz(2, "Celebrity Partners", user1);

        questions.add(new Question(1, "Which of these countries have white in their flag?", quiz1, "MULTIPLE_CHOICE"));
        questions.add(new Question(2, "In which country was the first flag created?", quiz1, "MULTIPLE_CHOICE"));
        questions.add(new Question(3, "The flag of Canada is commonly known as:", quiz1, "MULTIPLE_CHOICE"));
        questions.add(new Question(4, "How many stars are there on the flag of the United States of America?", quiz1, "MULTIPLE_CHOICE"));
        questions.add(new Question(5, "The flag of Australia contains which other country’s flag in its top left corner?", quiz1, "MULTIPLE_CHOICE"));

//        questions.add(new Question("Who is Kim Kardashian's current partner?", quiz1));
        questions.add(new Question(6, "How many people has The Weekend dated?", quiz2, "mc"));
        questions.add(new Question(7, "Beyonce is married to who?s", quiz2, "mc"));
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
}
