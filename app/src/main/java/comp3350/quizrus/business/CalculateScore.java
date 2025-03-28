package comp3350.quizrus.business;

public class CalculateScore {
    public static final int POINTS_PER_CORRECT = 100;

    /**
     * Calculates a final score value using the time taken and number of correct answers by the user
     */
    public static int calculateScore(int numCorrect, int totalTime, int timeTaken) {
        int timeRemaining = totalTime - timeTaken;

        //timeBonus is a ratio to multiply with the correctnessScore
        float timeBonus = (float) timeRemaining / (float) totalTime;

        int correctnessScore = numCorrect * POINTS_PER_CORRECT;

        return (int) (correctnessScore * (1 + timeBonus));
    }
}
