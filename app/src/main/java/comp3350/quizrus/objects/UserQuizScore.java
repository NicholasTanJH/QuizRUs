package comp3350.quizrus.objects;

public class UserQuizScore {
    private int userQuizScoreID;
    private User user;
    private Quiz quiz;
    private int numCorrect;
    private int timeTaken;
    private int score;

    public UserQuizScore(int newUserQuizScoreID, User newUser, Quiz newQuiz, int newNumCorrect, int newtimeTaken,
            int newScore) {
        this.userQuizScoreID = newUserQuizScoreID;
        this.user = newUser;
        this.quiz = newQuiz;
        this.numCorrect = newNumCorrect;
        this.timeTaken = newtimeTaken;
        this.score = newScore;
    }

    public int getUserQuizScoreID() {
        return this.userQuizScoreID;
    }

    public User getUser() {
        return this.user;
    }

    public Quiz getQuiz() {
        return this.quiz;
    }

    public int getNumCorrect() {
        return this.numCorrect;
    }

    public int getTimeTaken() {
        return this.timeTaken;
    }

    public int getScore() {
        return this.score;
    }
}
