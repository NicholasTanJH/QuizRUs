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
        return Collections.unmodifiableList(answers);
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


//    public List<Answer> getAnswers() {
//        answers = answerPersistence.getAnswerSequential();
//        return Collections.unmodifiableList(answers);
//    }

//    public Answer getSequential() {
//        if (answers == null) {
//            answers = answerPersistence.getAnswerSequential();
//            currentAnswer = 0;
//        }
//        if (currentAnswer < answers.size()) {
//            answer = answers.get(currentAnswer);
//            currentAnswer++;
//        } else {
//            answers = null;
//            answer = null;
//            currentAnswer = 0;
//        }
//        return answer;
//    }
//
//    public Answer getRandom(int answerID) {
//        answer = null;
//        if (answerID <= 0) {
//            // System.out.println("*** Invalid answer ID");
//        } else {
//            answers = answerPersistence.getAnswerRandom(new Answer(answerID));
//            if (answers.size() == 1) {
//                answer = answers.get(0);
//            }
//        }
//        return answer;
//    }
}
