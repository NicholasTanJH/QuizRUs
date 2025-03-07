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
        insertAnswer(new Answer(0, "Morocco", false, question1), question1);
        insertAnswer(new Answer(1, "Malawi", false, question1), question1);
        insertAnswer(new Answer(2, "Bangladesh", false, question1), question1);
        insertAnswer(new Answer(3, "Canada", true, question1), question1);

        // Answers for quiz 1 question 2.
        insertAnswer(new Answer(4, "United States of America", false, question2), question2);
        insertAnswer(new Answer(5, "South Korea", false, question2), question2);
        insertAnswer(new Answer(6, "United Kingdom", false, question2), question2);
        insertAnswer(new Answer(7, "China", true, question2), question2);

        // Answers for quiz 1 question 3.
        insertAnswer(new Answer(8, "The Maple Banner", false, question3), question3);
        insertAnswer(new Answer(9, "The Red Leaf", false, question3), question3);
        insertAnswer(new Answer(10, "The Maple Leaf", true, question3), question3);
        insertAnswer(new Answer(11, "The White Cross", false, question3), question3);

        // Answers for quiz 1 question 4.
        insertAnswer(new Answer(12, "50", true, question4), question4);
        insertAnswer(new Answer(13, "52", false, question4), question4);
        insertAnswer(new Answer(14, "48", false, question4), question4);
        insertAnswer(new Answer(15, "51", false, question4), question4);

        // Answers for quiz 1 question 5.
        insertAnswer(new Answer(16, "Canada", false, question5), question5);
        insertAnswer(new Answer(17, "United Kingdom", true, question5), question5);
        insertAnswer(new Answer(18, "United States", false, question5), question5);
        insertAnswer(new Answer(19, "New Zealand", false, question5), question5);

        // Answers for quiz 2 question 6.
        insertAnswer(new Answer(20, "Nathan Nyugen", false, question6), question6);
        insertAnswer(new Answer(21, "Nicholas Edward", false, question6), question6);
        insertAnswer(new Answer(22, "Huzaifa Smith", false, question6), question6);
        insertAnswer(new Answer(23, "Neil Murray", true, question6), question6);

        // Answers for quiz 2 question 7.
        insertAnswer(new Answer(24, "101000", false, question7), question7);
        insertAnswer(new Answer(25, "78", false, question7), question7);
        insertAnswer(new Answer(26, "1", true, question7), question7);
        insertAnswer(new Answer(27, "China", false, question7), question7);
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
    public int insertAnswer(Answer answer, Question question) {
        answer.setAnswerID(this.numAnswers);
        this.answers.add(answer);
        this.numAnswers++;
        return answer.getAnswerID();
    }
}
