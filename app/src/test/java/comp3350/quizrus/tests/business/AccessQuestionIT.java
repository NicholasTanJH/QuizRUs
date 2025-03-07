package comp3350.quizrus.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.business.AccessQuestions;
import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.QuestionPersistence;
import comp3350.quizrus.persistence.QuizPersistence;
import comp3350.quizrus.persistence.UserPersistence;
import comp3350.quizrus.persistence.stubs.QuestionPersistenceStub;
import comp3350.quizrus.persistence.stubs.QuizPersistenceStub;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;
import comp3350.quizrus.tests.utils.TestUtils;

public class AccessQuestionIT {
    private AccessQuestions accessQuestions;
    private AccessQuizzes accessQuizzes;
    private AccessUsers accessUsers;
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
        this.accessQuizzes = new AccessQuizzes(quizPersistence);
        this.accessUsers = new AccessUsers(userPersistence);
        this.accessQuestions = new AccessQuestions(questionPersistence);
    }

    @After
    public void tearDown() {
        // Clear the database.
        this.tempDB.delete();
    }

    @Test
    public void testInsertQuiz() {
        // Create a new user.
        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        // Create a new quiz.
        Quiz quiz1 = accessQuizzes.createQuiz(user1, "Population in Canada", 120);
        assertNotNull(quiz1);
        assertNotEquals(-1, quiz1.getQuizID());

        // Insert a question into the database.
        Question question1 = accessQuestions.createQuestion(quiz1, "What is the life expectancy in Canada?",
                "MULTIPLE_CHOICE");
        assertNotNull(question1);
        assertNotEquals(-1, question1.getQuestionID());
    }

    @Test
    public void testGetQuestionsForQuiz() {
        // Create a new user.
        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        // Create a new quiz.
        Quiz quiz1 = accessQuizzes.createQuiz(user1, "Facts about Canada", 120);
        assertNotNull(quiz1);
        assertNotEquals(-1, quiz1.getQuizID());

        // Create questions.
        Question question1 = accessQuestions.createQuestion(quiz1, "What is the life expectancy in Canada?",
                "MULTIPLE_CHOICE");
        Question question2 = accessQuestions.createQuestion(quiz1, "What is the most popular city in Canada?",
                "MULTIPLE_CHOICE");
        assertNotNull(question1);
        assertNotEquals(-1, question1.getQuestionID());
        assertNotNull(question2);
        assertNotEquals(-1, question2.getQuestionID());

        // Retrieve questions from the database.
        List<Question> questions = accessQuestions.getQuestions(quiz1);
        assertEquals(2, questions.size());
        for (Question question : questions) {
            assertTrue(question.getQuestionText().equals("What is the life expectancy in Canada?")
                    || question.getQuestionText().equals("What is the most popular city in Canada?"));
            assertEquals("MULTIPLE_CHOICE", question.getQuestionType());
        }
    }

    @Test
    public void testGetQuestionByID() {
        // Create a new user.
        User user1 = accessUsers.createUser("alice", "password", "Alice", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        // Create a new quiz.
        Quiz quiz1 = accessQuizzes.createQuiz(user1, "Geography Quiz", 90);
        assertNotNull(quiz1);
        assertNotEquals(-1, quiz1.getQuizID());

        // Create a new question.
        Question question1 = accessQuestions.createQuestion(quiz1, "What is the capital of France?", "MULTIPLE_CHOICE");
        assertNotNull(question1);
        assertNotEquals(-1, question1.getQuestionID());

        // Retrieve the question using its ID.
        Question retrievedQuestion = accessQuestions.getQuestion(question1.getQuestionID());
        assertNotNull(retrievedQuestion);
        assertEquals(question1.getQuestionID(), retrievedQuestion.getQuestionID());
        assertEquals("What is the capital of France?", retrievedQuestion.getQuestionText());
        assertEquals("MULTIPLE_CHOICE", retrievedQuestion.getQuestionType());
    }

}
