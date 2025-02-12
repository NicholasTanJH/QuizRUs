package comp3350.quizrus.business;

import org.hsqldb.lib.Collection;

import java.util.Collections;
import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.persistence.AnswerPersistence;

public class AccessAnswers {
    private AnswerPersistence answerPersistence;
    private List<Answer> answers;
    private Answer answer;
    private int currentAnswer;

    public AccessAnswers() {
        this.answerPersistence = Services.getAnswerPersistence();
        this.answers = null;
        this.answer = null;
        this.currentAnswer = 0;
    }

    public AccessAnswers(final AnswerPersistence answerPersistence) {
        this();
        this.answerPersistence = answerPersistence;
    }

    public List<Answer> getAnswers(final Question question){
        answers = answerPersistence.getAnswersForQuestions(question);
        return answers;
    }

    //find the position of the correct answer in the list
    public int getCorrectAnswerPosition(List<Answer> answers){
        int correctAnswerPosition = -1;
        for(int position = 0; position < answers.size(); position++){
            if(answers.get(position).isCorrect()){
                correctAnswerPosition = position;
            }
        }
        return correctAnswerPosition;
    }
}
