package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.AnswerPersistence;

public class AnswerPersistenceStub implements AnswerPersistence {
    private List<Answer> answers;

    public AnswerPersistenceStub() {
        this.answers = new ArrayList<>();

        // Answers must be associated with a question.
        User user1 = new User("1", "kakashi", "password1");
        Quiz quiz1 = new Quiz("1", "Flags of Countries", user1);
        Question question1 = new Question("Which of these countries have white in their flag?", quiz1);
        Question question2 = new Question("In which country was the first flag created?", quiz1);
        Question question3 = new Question("The flag of Canada is commonly known as:", quiz1);
        Question question4 = new Question("How many stars are there on the flag of the United States of America?", quiz1);
        Question question5 = new Question("The flag of Australia contains which other country’s flag in its top left corner?", quiz1);

        answers.add(new Answer(1, "Morocco", false, question1));
        answers.add(new Answer(2, "Malawi", false, question1));
        answers.add(new Answer(3, "Bangladesh", false, question1));
        answers.add(new Answer(4, "Canada", true, question1));

        answers.add(new Answer(5, "United States of America", false, question2));
        answers.add(new Answer(6, "South Korea", false, question2));
        answers.add(new Answer(7,"United Kingdom", false, question2));
        answers.add(new Answer(8,"China", true, question2));

        answers.add(new Answer(9, "The Maple Banner", false, question3));
        answers.add(new Answer(10, "The Red Leaf", false, question3));
        answers.add(new Answer(11,"The Maple Leaf", true, question3));
        answers.add(new Answer(12,"The White Cross", false, question3));

        answers.add(new Answer(13, "50", true, question4));
        answers.add(new Answer(14, "52", false, question4));
        answers.add(new Answer(15,"48", false, question4));
        answers.add(new Answer(16,"51", false, question4));

        answers.add(new Answer(17, "Canada", false, question5));
        answers.add(new Answer(18, "United Kingdom", true, question5));
        answers.add(new Answer(19,"United States", false, question5));
        answers.add(new Answer(20,"New Zealand", false, question5));
    }

    @Override
    public List<Answer> getAnswersForQuestions(Question question) {
        List<Answer> answersToQuestions = new ArrayList<>();

        for (Answer answer : answers) {
            if (answer.getMyQuestion().equals(question)) {
                answersToQuestions.add(answer);
            }
        }
        return answersToQuestions;
    }

    @Override
    public List<Answer> getCorrectAnswers(Question question) {
        List<Answer> correctAnswers = new ArrayList<>();

        for (Answer answer : answers) {
            if (answer.getMyQuestion().equals(question) &&
                    answer.isCorrect()) {
                correctAnswers.add(answer);
            }
        }
        return Collections.unmodifiableList(correctAnswers);
    }
}
