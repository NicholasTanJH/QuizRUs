package comp3350.quizrus.objects;

import java.util.Objects;

public class Answer {
    private int answerID;
    private final String answerText;
    private final Question myQuestion;
    private final boolean isCorrect;

    public Answer(final int newAnswerID, final String newAnswerText, final boolean newIsCorrect, final Question newMyQuestion) {
        this.answerID = newAnswerID;
        this.answerText = newAnswerText;
        this.myQuestion = newMyQuestion;
        this.isCorrect = newIsCorrect;
    }

    public int getAnswerID() {
        return this.answerID;
    }

    public void setAnswerID(int newID){
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
    public String toString() {
        return String.format("Answer: [ID: %s, Text: %s, Question ID: %s, Correct: %b]",
                answerID, answerText, myQuestion != null ? myQuestion.getQuestionID() : "null", isCorrect);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Answer otherAnswer = (Answer) other;
        return Objects.equals(this.answerID, otherAnswer.answerID) &&
                Objects.equals(this.answerText, otherAnswer.answerText) &&
                Objects.equals(this.myQuestion, otherAnswer.myQuestion) &&
                this.isCorrect == otherAnswer.isCorrect;
    }
}