package comp3350.quizrus.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.quizrus.persistence.UserPersistence;
import comp3350.quizrus.persistence.QuizPersistence;
import comp3350.quizrus.persistence.QuestionPersistence;
import comp3350.quizrus.persistence.AnswerPersistence;
import comp3350.quizrus.persistence.hsqldb.PersistenceException;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;
import comp3350.quizrus.persistence.stubs.QuizPersistenceStub;
import comp3350.quizrus.persistence.stubs.QuestionPersistenceStub;
import comp3350.quizrus.persistence.stubs.AnswerPersistenceStub;

import comp3350.quizrus.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.QuizPersistenceHSQLDB;


public class Services {
    private static UserPersistence userPersistence = null;
    private static QuizPersistence quizPersistence = null;
    private static QuestionPersistence questionPersistence = null;
    private static AnswerPersistence answerPersistence = null;

    public static synchronized UserPersistence getUserPersistence() {
        if (userPersistence == null) {
            userPersistence = new UserPersistenceStub();
        }

        return userPersistence;
    }

    public static synchronized QuizPersistence getQuizPersistence() {
        if (quizPersistence == null) {
            quizPersistence = new QuizPersistenceStub();
        }

        return quizPersistence;
    }

    public static synchronized QuestionPersistence getQuestionPersistence() {
        if (questionPersistence == null) {
            questionPersistence = new QuestionPersistenceStub();
        }

        return questionPersistence;
    }

    public static synchronized AnswerPersistence getAnswerPersistence() {
        if (answerPersistence == null) {
            answerPersistence = new AnswerPersistenceStub();
        }

        return answerPersistence;
    }
}
