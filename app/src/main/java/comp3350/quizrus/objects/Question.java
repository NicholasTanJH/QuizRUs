package comp3350.quizrus.objects;


import java.util.Objects;
public class Question {
    private final int questionID;
    private final Quiz myQuiz;
    private final String questionText;
    private final String questionType;

    public Question(final int newQuestionID, final String newQuestionText, final Quiz newMyQuiz, final String newQuestionType){
        this.questionText = newQuestionText;
        this.myQuiz = newMyQuiz;
        this.questionID = newQuestionID;
        this.questionType = newQuestionType;
    }

    public int getQuestionID(){
        return this.questionID;
    }

    public Quiz getMyQuiz(){
        return this.myQuiz;
    }

    public String getQuestionText(){return this.questionText;}

    public String getQuestionType() {
        return this.questionType;
    }

    @Override
    //toString Method
    public String toString(){
        return String.format("Question: [ID: %s, Quiz: %s, Text: %s, Type: %s]",
                questionID, myQuiz != null ? myQuiz.getQuizID() : "null", questionText, questionType);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Question otherQuestion = (Question) other;
        return Objects.equals(this.questionID, otherQuestion.questionID) &&
                Objects.equals(this.myQuiz, otherQuestion.myQuiz) &&
                Objects.equals(this.questionText, otherQuestion.questionText) &&
                Objects.equals(this.questionType, otherQuestion.questionType);
    }
}
