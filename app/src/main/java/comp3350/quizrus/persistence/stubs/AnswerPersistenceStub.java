package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.AnswerPersistence;

public class AnswerPersistenceStub implements AnswerPersistence {
    private List<Answer> answers;
    private int numAnswers;

    public AnswerPersistenceStub() {
        this.answers = new ArrayList<>();
        this.numAnswers = 0;

        // A quiz must be associated with a user.
        User user1 = new User(0, "demo", "Password0!", "Jessie", "Andrade");
        User user2 = new User(1, "kakashi", "Password1!", "Saige", "Santana");

        // A question must be associated with a quiz.
        Quiz quiz1 = new Quiz(0, "Flags of Countries", user1, 120);
        Quiz quiz2 = new Quiz(1, "Celebrity Partners", user2, 120);

        // List of questions for quiz 1.
        Question question1 = new Question(0, "Which of these countries have white in their flag?", quiz1,
                "MULTIPLE_CHOICE");
        Question question2 = new Question(1, "In which country was the first flag created?", quiz1, "MULTIPLE_CHOICE");
        Question question3 = new Question(2, "The flag of Canada is commonly known as:", quiz1, "MULTIPLE_CHOICE");
        Question question4 = new Question(3, "How many stars are there on the flag of the United States of America?",
                quiz1, "MULTIPLE_CHOICE");
        Question question5 = new Question(4,
                "The flag of Australia contains which other country’s flag in its top left corner?", quiz1,
                "MULTIPLE_CHOICE");

        // List of questions for quiz 2.
        Question question6 = new Question(5, "Who is the partner of J.K. Rowling?", quiz2, "MULTIPLE_CHOICE");
        Question question7 = new Question(6, "How many partners did Drake date in 2024?", quiz2, "MULTIPLE_CHOICE");

        // Answers for quiz 1 question 1.
        insertAnswer("Morocco", question1, false);
        insertAnswer("Malawi", question1, false);
        insertAnswer("Bangladesh", question1, false);
        insertAnswer("Canada", question1, true);

        // Answers for quiz 1 question 2.
        insertAnswer("United States of America", question2, false);
        insertAnswer("South Korea", question2, false);
        insertAnswer("United Kingdom", question2, false);
        insertAnswer("China", question2, true);

        // Answers for quiz 1 question 3.
        insertAnswer("The Maple Banner", question3, false);
        insertAnswer("The Red Leaf", question3, false);
        insertAnswer("The Maple Leaf", question3, true);
        insertAnswer("The White Cross", question3, false);

        // Answers for quiz 1 question 4.
        insertAnswer("50", question4, true);
        insertAnswer("52", question4, false);
        insertAnswer("48", question4, false);
        insertAnswer("51", question4, false);

        // Answers for quiz 1 question 5.
        insertAnswer("Canada", question5, false);
        insertAnswer("United Kingdom", question5, true);
        insertAnswer("United States", question5, false);
        insertAnswer("New Zealand", question5, false);

        // Answers for quiz 2 question 6.
        insertAnswer("Nathan Nyugen", question6, false);
        insertAnswer("Nicholas Edward", question6, false);
        insertAnswer("Huzaifa Smith", question6, false);
        insertAnswer("Neil Murray", question6, true);

        // Answers for quiz 2 question 7.
        insertAnswer("101000", question7, false);
        insertAnswer("78", question7, false);
        insertAnswer("1", question7, true);
        insertAnswer("China", question7, false);
    }

    @Override
    public List<Answer> getAnswersForQuestions(Question question) {
        List<Answer> answersToQuestions = new ArrayList<>();

        for (Answer answer : this.answers) {
            if (answer.getMyQuestion().equals(question)) {
                answersToQuestions.add(answer);
            }
        }
        return answersToQuestions;
    }

    @Override
    public int insertAnswer(final String answerText, final Question question, final boolean isCorrect) {
        Answer newAnswer = new Answer(numAnswers, answerText, isCorrect, question);
        this.answers.add(newAnswer);
        this.numAnswers++;
        return newAnswer.getAnswerID();
    }
}
