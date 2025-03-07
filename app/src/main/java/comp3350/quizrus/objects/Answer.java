package comp3350.quizrus.objects;

import java.util.Objects;

public class Answer {
    private int answerID;
    private final String answerText;
    private final Question myQuestion;
    private final boolean isCorrect;

    public Answer(final int newAnswerID, final String newAnswerText, final boolean newIsCorrect,
            final Question newMyQuestion) {
        this.answerID = newAnswerID;
        this.answerText = newAnswerText;
        this.myQuestion = newMyQuestion;
        this.isCorrect = newIsCorrect;
    }

    public Answer(final String newAnswerText, final boolean newIsCorrect, final Question newMyQuestion) {
        this.answerID = -1;
        this.answerText = newAnswerText;
        this.myQuestion = newMyQuestion;
        this.isCorrect = newIsCorrect;
    }

    public int getAnswerID() {
        return this.answerID;
    }

    public void setAnswerID(int newID) {
        this.answerID = newID;
    }

    public String getAnswerText() {
        return this.answerText;
    }

    public Question getMyQuestion() {
        return this.myQuestion;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }

    @Override
    public boolean equals(Object other) {

        boolean equal = false;

        if (other instanceof Answer) {
            Answer otherAnswer = (Answer) other;
            equal = Objects.equals(this.answerID, otherAnswer.answerID);
        }

        return equal;
    }
}