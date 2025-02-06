package comp3350.quizrus.objects;

import java.util.Objects;

public class Answer {
    private final int answerID;
    private final String answerText;
    private final Question myQuestion;
    private final boolean isCorrect;

    public Answer(final int newAnswerID){
        this.answerID = newAnswerID;
        this.answerText = null;
        this.myQuestion = null;
        this.isCorrect = false;
    }
    public Answer(final String newAnswerText, final Question newMyQuestion){
        this.answerText = newAnswerText;
        this.myQuestion = newMyQuestion;
        this.answerID = 0;
        this.isCorrect = true;
    }

    public Answer(final String newAnswerText, final Question newMyQuestion, final int
    newAnswerID){
        this.answerText = newAnswerText;
        this.myQuestion = newMyQuestion;
        this.answerID = newAnswerID;
        this.isCorrect = true;
    }

    // Getters
    public int getAnswerID() {
        return this.answerID;
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

    // toString method
    @Override
    public String toString() {
        return String.format("Answer: [ID: %s, Text: %s, Question ID: %s, Correct: %b]",
                answerID, answerText, myQuestion != null ? myQuestion.getQuestionID() : "null", isCorrect);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(answerID, answerText, myQuestion, isCorrect);
    }

    // equals method
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
