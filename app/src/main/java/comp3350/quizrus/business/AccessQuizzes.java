package comp3350.quizrus.business;

import java.util.Collections;
import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.persistence.QuizPersistence;

public class AccessQuizzes {
    private QuizPersistence quizPersistence;
    private List<Quiz> quizzes;
    private Quiz quiz;
    private int currentQuiz;

    public AccessQuizzes()
    {
        quizPersistence = Services.getQuizPersistence();
        quizzes = null;
        quiz = null;
        currentQuiz = 0;
    }

    public AccessQuizzes(final QuizPersistence quizPersistence)
    {
        this();
        this.quizPersistence = quizPersistence;
    }

    public List<Quiz> getQuizzes()
    {
        quizzes = quizPersistence.getAllQuizzes();
        return Collections.unmodifiableList(quizzes);
    }

    public Quiz getSequential()
    {
        if(quizzes == null)
        {
            quizzes = quizPersistence.getAllQuizzes();
            currentQuiz = 0;
        }
        if(currentQuiz < quizzes.size())
        {
            quiz = quizzes.get(currentQuiz);
            currentQuiz++;
        }
        else
        {
            quizzes = null;
            quiz = null;
            currentQuiz = 0;
        }
        return quiz;
    }
}
