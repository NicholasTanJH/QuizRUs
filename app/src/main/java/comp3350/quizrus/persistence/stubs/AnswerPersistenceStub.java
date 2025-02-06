package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.persistence.AnswerPersistence;

public class AnswerPersistenceStub implements AnswerPersistence {
    private List<Answers> answers;

    public AnswerPersistenceStub() {
        this.answers = new ArrayList<>();

        // Answers must be associated with a question.
        User user1 = new User("kakashi", "password1");
        Quiz quiz1 = new Quiz("Flags of Countries", user1);
        Question question1 = new Question("Which of these countries has a white flag?", quiz1);
        Question question2 = new Question("In which country was the first flag created?", quiz1);

        answers.add(new Answer("USA", question1));
        answers.add(new Answer("Armenia", question1));
        answers.add(new Answer("China", question1));
        answers.add(new Answer("Canada", question1));

        answers.add(new Answer("Nigeria", question2));
        answers.add(new Answer("South Korea", question2));
        answers.add(new Answer("United Kingdoms", question2));
        answers.add(new Answer("Pakistan", question2));
    }

    @Override
    public List<Answer> getQuestionsForQuiz(Question question) {
        List<Answer> answersToQuestion = new ArrayList<>();

        for (Answer answer : answers) {
            if (answer.question.equals(question)) {
                answersToQuestions.add(answer);
            }
        }
        return Collections.unmodifiableList(answersToQuestion);
    }
}
