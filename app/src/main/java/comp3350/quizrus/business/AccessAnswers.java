package comp3350.quizrus.business;

import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.persistence.AnswerPersistence;

public class AccessAnswers {
    private AnswerPersistence answerPersistence;
    private List<Answer> answers;

    public AccessAnswers() {
        this.answerPersistence = Services.getAnswerPersistence();
        this.answers = null;
    }

    public AccessAnswers(final AnswerPersistence answerPersistence) {
        this();
        this.answerPersistence = answerPersistence;
    }

    public List<Answer> getAnswers(final Question question) {
        answers = answerPersistence.getAnswersForQuestions(question);
        return answers;
    }

    /**
     * Used by the UI to get the index in the list of the correct answer
     */
    public int getCorrectAnswerPosition(List<Answer> answers) {
        // Find the position of the correct answer in the list.
        int correctAnswerPosition = -1;
        for (int position = 0; position < answers.size(); position++) {
            if (answers.get(position).isCorrect()) {
                correctAnswerPosition = position;
            }
        }
        return correctAnswerPosition;
    }

    public Answer createAnswer(final String answerText, final Question question, final boolean isCorrect) {
        int answerID = answerPersistence.insertAnswer(answerText, question, isCorrect);

        if (answerID != -1) {
            return new Answer(answerID, answerText, isCorrect, question);
        } else {
            return null;
        }
    }
}
