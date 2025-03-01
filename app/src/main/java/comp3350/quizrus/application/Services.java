package comp3350.quizrus.application;

import comp3350.quizrus.persistence.UserPersistence;
import comp3350.quizrus.persistence.QuizPersistence;
import comp3350.quizrus.persistence.QuestionPersistence;
import comp3350.quizrus.persistence.AnswerPersistence;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;
import comp3350.quizrus.persistence.stubs.QuizPersistenceStub;
import comp3350.quizrus.persistence.stubs.QuestionPersistenceStub;
import comp3350.quizrus.persistence.stubs.AnswerPersistenceStub;
import comp3350.quizrus.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.QuizPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.QuestionPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.AnswerPersistenceHSQLDB;

public class Services {
    private static UserPersistence userPersistence = null;
    private static QuizPersistence quizPersistence = null;
    private static QuestionPersistence questionPersistence = null;
    private static AnswerPersistence answerPersistence = null;

    public static synchronized UserPersistence getUserPersistence() {
        if (userPersistence == null) {
            userPersistence = new UserPersistenceHSQLDB(Main.getDBPathName());
        }

        return userPersistence;
    }

    public static synchronized QuizPersistence getQuizPersistence() {
        if (quizPersistence == null) {
            quizPersistence = new QuizPersistenceHSQLDB(Main.getDBPathName());
        }

        return quizPersistence;
    }

    public static synchronized QuestionPersistence getQuestionPersistence() {
        if (questionPersistence == null) {
            questionPersistence = new QuestionPersistenceHSQLDB(Main.getDBPathName());
        }

        return questionPersistence;
    }

    public static synchronized AnswerPersistence getAnswerPersistence() {
        if (answerPersistence == null) {
            answerPersistence = new AnswerPersistenceHSQLDB(Main.getDBPathName());
        }

        return answerPersistence;
    }
}
