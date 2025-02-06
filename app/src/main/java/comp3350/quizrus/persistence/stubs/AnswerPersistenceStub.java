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
        Question question1 = new Question("Which of these countries have white in their flag?", quiz1);
        Question question2 = new Question("In which country was the first flag created?", quiz1);

        answers.add(new Answer("Morocco", false, question1));
        answers.add(new Answer("Malawi", false, question1));
        answers.add(new Answer("Bangladesh", false, question1));
        answers.add(new Answer("Canada", true, question1));

        answers.add(new Answer("United States of America", false, question2));
        answers.add(new Answer("South Korea", false, question2));
        answers.add(new Answer("United Kingdom", false, question2));
        answers.add(new Answer("China", true, question2));
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

    @Override
    public List<Answer> getCorrectAnswers(Question question) {
        List<Answer> correctAnswers = new ArrayList<>();

        for (Answer answer : answers) {
            if (answer.question.equals(question) &&
                    answer.isCorrect == true) {
                correctAnswers.add(answer);
            }
        }
        return Collection.unmodifiableList(correctAnswers);
    }
}
