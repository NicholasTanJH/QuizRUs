package comp3350.quizrus.tests.business;

import static org.junit.Assert.*;

import org.junit.Test;

import comp3350.quizrus.business.CalculateScore;

public class CalculateScoreTest {

    @Test
    public void testCalculateScoreFullTimeRemaining() {
        int score = CalculateScore.calculateScore(5, 100, 0);
        assertEquals(1000, score); // (5 * 100) * (1 + 1)
    }

    @Test
    public void testCalculateScoreHalfTimeRemaining() {
        int score = CalculateScore.calculateScore(3, 100, 50);
        assertEquals(450, score); // (3 * 100) * (1 + 0.5)
    }

    @Test
    public void testCalculateScoreNoTimeRemaining() {
        int score = CalculateScore.calculateScore(4, 100, 100);
        assertEquals(400, score); // (4 * 100) * (1 + 0)
    }

    @Test
    public void testCalculateScoreZeroCorrectAnswers() {
        int score = CalculateScore.calculateScore(0, 100, 50);
        assertEquals(0, score); // (0 * 100) * (1 + 0.5)
    }
}