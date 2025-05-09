package comp3350.quizrus.objects;

import java.util.Objects;

public class Question {
    private int questionID;
    private final Quiz myQuiz;
    private final String questionText;
    private final String questionType;

    public Question(final int newQuestionID, final String newQuestionText, final Quiz newMyQuiz,
            final String newQuestionType) {
        this.questionText = newQuestionText;
        this.myQuiz = newMyQuiz;
        this.questionID = newQuestionID;
        this.questionType = newQuestionType;
    }

    public int getQuestionID() {
        return this.questionID;
    }

    public Quiz getMyQuiz() {
        return this.myQuiz;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public String getQuestionType() {
        return this.questionType;
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;

        if (other instanceof Question) {
            Question otherQuestion = (Question) other;
            equal = Objects.equals(this.questionID, otherQuestion.questionID);
        }

        return equal;
    }
}
