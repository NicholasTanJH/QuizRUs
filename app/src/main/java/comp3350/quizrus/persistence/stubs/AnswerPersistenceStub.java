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
        User user1 = new User(1, "kakashi", "password1");
    
        // List of quizzes.
        Quiz quiz1 = new Quiz(1, "Flags of Countries", user1);
        Quiz quiz2 = new Quiz(2, "Celebrity Partners", user1);

        // List of questions for quiz 1.
        Question question1 = new Question(1, "Which of these countries have white in their flag?", quiz1, "MULTIPLE_CHOICE");
        Question question2 = new Question(2, "In which country was the first flag created?", quiz1, "MULTIPLE_CHOICE");
        Question question3 = new Question(3, "The flag of Canada is commonly known as:", quiz1, "MULTIPLE_CHOICE");
        Question question4 = new Question(4, "How many stars are there on the flag of the United States of America?", quiz1, "MULTIPLE_CHOICE");
        Question question5 = new Question(5, "The flag of Australia contains which other country’s flag in its top left corner?", quiz1, "MULTIPLE_CHOICE");

        // Answers for quiz 1 question 1.
        answers.add(new Answer(1, "Morocco", false, question1));
        answers.add(new Answer(2, "Malawi", false, question1));
        answers.add(new Answer(3, "Bangladesh", false, question1));
        answers.add(new Answer(4, "Canada", true, question1));

        // Answers for quiz 1 question 2.
        answers.add(new Answer(5, "United States of America", false, question2));
        answers.add(new Answer(6, "South Korea", false, question2));
        answers.add(new Answer(7,"United Kingdom", false, question2));
        answers.add(new Answer(8,"China", true, question2));

        // Answers for quiz 1 question 3.
        answers.add(new Answer(9, "The Maple Banner", false, question3));
        answers.add(new Answer(10, "The Red Leaf", false, question3));
        answers.add(new Answer(11,"The Maple Leaf", true, question3));
        answers.add(new Answer(12,"The White Cross", false, question3));

        // Answers for quiz 1 question 4.
        answers.add(new Answer(13, "50", true, question4));
        answers.add(new Answer(14, "52", false, question4));
        answers.add(new Answer(15,"48", false, question4));
        answers.add(new Answer(16,"51", false, question4));

        // Answers for quiz 1 question 5.
        answers.add(new Answer(17, "Canada", false, question5));
        answers.add(new Answer(18, "United Kingdom", true, question5));
        answers.add(new Answer(19,"United States", false, question5));
        answers.add(new Answer(20,"New Zealand", false, question5));

        // List of questions for quiz 2.
        Question question6 = new Question(6, "Who is the partner of J.K. Rowling?", quiz2, "MULTIPLE_CHOICE");
        Question question7 = new Question(7, "How many partners did Drake date in 2024?", quiz2, "MULTIPLE_CHOICE");

        // Answers for quiz 2 question 6.
        answers.add(new Answer(9, "Nathan Nyugen", false, question6));
        answers.add(new Answer(10, "Nicholas Edward", false, question6));
        answers.add(new Answer(11, "Huzaifa Smith", false, question6));
        answers.add(new Answer(12, "Neil Murray", true, question6));

        // Answers for quiz 2 question 7.
        answers.add(new Answer(13, "101000", false, question7));
        answers.add(new Answer(14, "78", false, question7));
        answers.add(new Answer(15,"1", true, question7));
        answers.add(new Answer(16,"China", false, question7));
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
    public int insertAnswer(Answer answer, Question question) {
        return -1;
    }
}
