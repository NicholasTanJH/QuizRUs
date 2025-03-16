package comp3350.quizrus.business;

public class CalculateScore {
    public static final int POINTS_PER_CORRECT = 100;
    public static int calculateScore(int numCorrect, int totalTime, int timeElapsed)
    {
        int timeRemaining = totalTime - timeElapsed;
        float timeBonus = (float)timeRemaining/(float)totalTime;
        int correctnessScore = numCorrect * POINTS_PER_CORRECT;

        return (int)(correctnessScore*(1+timeBonus));
    }
}
