package comp3350.quizrus.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.business.AccessAnswers;
import comp3350.quizrus.business.AccessQuestions;
import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.AnswerPersistence;
import comp3350.quizrus.persistence.QuestionPersistence;
import comp3350.quizrus.persistence.QuizPersistence;
import comp3350.quizrus.persistence.UserPersistence;
import comp3350.quizrus.persistence.stubs.AnswerPersistenceStub;
import comp3350.quizrus.persistence.stubs.QuestionPersistenceStub;
import comp3350.quizrus.persistence.stubs.QuizPersistenceStub;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;
import comp3350.quizrus.tests.utils.TestUtils;

public class AccessAnswerIT {
    private AccessQuestions accessQuestions;
    private AccessQuizzes accessQuizzes;
    private AccessUsers accessUsers;
    private AccessAnswers accessAnswers;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        UserPersistence userPersistence = Services.getUserPersistence();
        if(userPersistence instanceof UserPersistenceStub)
        {
            userPersistence = new UserPersistenceStub();
        }
        QuizPersistence quizPersistence = Services.getQuizPersistence();
        if(quizPersistence instanceof QuizPersistenceStub)
        {
            quizPersistence = new QuizPersistenceStub();
        }
        QuestionPersistence questionPersistence = Services.getQuestionPersistence();
        if(questionPersistence instanceof QuestionPersistenceStub)
        {
            questionPersistence = new QuestionPersistenceStub();
        }
        AnswerPersistence answerPersistence = Services.getAnswerPersistence();
        if(answerPersistence instanceof AnswerPersistenceStub)
        {
            answerPersistence = new AnswerPersistenceStub();
        }
        this.accessQuizzes = new AccessQuizzes(quizPersistence);
        this.accessUsers = new AccessUsers(userPersistence);
        this.accessQuestions = new AccessQuestions(questionPersistence);
        this.accessAnswers = new AccessAnswers(answerPersistence);
    }

    @After
    public void tearDown() {
        // Clear the database.
        this.tempDB.delete();
    }

    @Test
    public void testInsertAnswers() {
        // Create a new user.
        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        // Create a new quiz.
        Quiz quiz1 = accessQuizzes.createQuiz(user1, "Population in Canada", 120);
        assertNotNull(quiz1);
        assertNotEquals(-1, quiz1.getQuizID());

        // Create a new question.
        Question question1 = accessQuestions.createQuestion(quiz1, "What is the life expectancy in Canada?",
                "MULTIPLE_CHOICE");
        assertNotNull(question1);
        assertNotEquals(-1, question1.getQuestionID());

        // Insert answers into the database.
        Answer answer1 = accessAnswers.createAnswer("50", question1, false);
        assertNotNull(answer1);
        assertNotEquals(-1, answer1.getAnswerID());
        Answer answer2 = accessAnswers.createAnswer("81", question1, true);
        assertNotNull(answer2);
        assertNotEquals(-1, answer2.getAnswerID());
        Answer answer3 = accessAnswers.createAnswer("65", question1, false);
        assertNotNull(answer3);
        assertNotEquals(-1, answer3.getAnswerID());
        Answer answer4 = accessAnswers.createAnswer("12", question1, false);
        assertNotNull(answer4);
        assertNotEquals(-1, answer4.getAnswerID());
    }

    @Test
    public void testGetAnswersForQuestions() {
        // Create a new user.
        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        // Create a new quiz.
        Quiz quiz1 = accessQuizzes.createQuiz(user1, "Population in Canada", 120);
        assertNotNull(quiz1);
        assertNotEquals(-1, quiz1.getQuizID());

        // Create a new question.
        Question question1 = accessQuestions.createQuestion(quiz1, "What is the life expectancy in Canada?",
                "MULTIPLE_CHOICE");
        assertNotNull(question1);
        assertNotEquals(-1, question1.getQuestionID());
        System.out.println(question1);

        // Insert answers into the database.
        Answer answer1 = accessAnswers.createAnswer("50", question1, false);
        assertNotNull(answer1);
        assertNotEquals(-1, answer1.getAnswerID());
        Answer answer2 = accessAnswers.createAnswer("81", question1, true);
        assertNotNull(answer2);
        assertNotEquals(-1, answer2.getAnswerID());
        Answer answer3 = accessAnswers.createAnswer("65", question1, false);
        assertNotNull(answer3);
        assertNotEquals(-1, answer3.getAnswerID());
        Answer answer4 = accessAnswers.createAnswer("12", question1, false);
        assertNotNull(answer4);
        assertNotEquals(-1, answer4.getAnswerID());

        // Retrieve questions from the database.
        List<Answer> answers = accessAnswers.getAnswers(question1);
        System.out.println(answers);
        assertEquals(4, answers.size());
        for (Answer answer : answers) {
            assertTrue(answer.getAnswerText().equals("50") || answer.getAnswerText().equals("81") ||
                    answer.getAnswerText().equals("65") || answer.getAnswerText().equals("12"));
        }
    }
}
