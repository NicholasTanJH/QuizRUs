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
        quizzes = quizPersistence.getQuizSequential();
        return Collections.unmodifiableList(quizzes);
    }

    public Quiz getSequential()
    {
        if(quizzes == null)
        {
            quizzes = quizPersistence.getQuizSequential();
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

    public Quiz getRandom(String quizID)
    {
        quiz = null;
        if(quizID.trim().equals(""))
        {
            //System.out.println("*** Invalid quiz ID");
        }
        else
        {
            quizzes = quizPersistence.getQuizRandom(new Quiz(quizID));
            if(quizzes.size() == 1)
            {
                quiz = (Quiz) quizzes.get(0);
            }
        }
        return quiz;
    }

    public Quiz insertQuiz(Quiz currentQuiz)
    {
        return quizPersistence.insertQuiz(currentQuiz);
    }

    public Quiz updateQuiz(Quiz currentQuiz)
    {
        return quizPersistence.updateQuiz(currentQuiz);
    }

    public void deleteQuiz(Quiz currentQuiz)
    {
        quizPersistence.deleteQuiz(currentQuiz);
    }
}
