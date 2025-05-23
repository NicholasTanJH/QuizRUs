package comp3350.quizrus.presentation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.quizrus.R;
import comp3350.quizrus.business.AccessLeaderboard;
import comp3350.quizrus.business.AccessQuestions;
import comp3350.quizrus.business.CalculateScore;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.presentation.adapter.LeaderboardRecycleViewAdapter;

public class QuizEndActivity extends AppCompatActivity {

    private AccessLeaderboard accessLeaderboard;
    private User currUser;
    private Quiz currQuiz;
    private List<Question> questions;
    private int numCorrectQuestions;
    private int totalQuestionNumber;
    private int timeLeft;
    private int timeLimitTotal;
    private int userHighScoreTotal;
    private Button buttonGoHome;
    private TextView questionNumScoreTV;
    private TextView timeLeftTV;
    private TextView highScoreTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_end);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        AccessQuestions accessQuestions = new AccessQuestions();
        String textViewHolder;

        quizEndAudio();

        accessLeaderboard = new AccessLeaderboard();

        currQuiz = (Quiz) intent.getSerializableExtra("currQuiz");
        currUser = (User) intent.getSerializableExtra("currUser");
        numCorrectQuestions = intent.getIntExtra("score", 0);

        // Inputting the time remaining
        timeLeft = intent.getIntExtra("timeLeft", 0);
        textViewHolder = "Time: " + timeLeft + "s";
        timeLeftTV = findViewById(R.id.timeRemaining);
        timeLeftTV.setText(textViewHolder);

        // Getting number of questions
        questions = accessQuestions.getQuestions(currQuiz);
        totalQuestionNumber = questions.size();

        // Putting the number of correct questions
        textViewHolder = "Correct: " + numCorrectQuestions + "/" + totalQuestionNumber;
        questionNumScoreTV = findViewById(R.id.correctQuestions);
        questionNumScoreTV.setText(textViewHolder);

        // Time limit stored for calculation?
        timeLimitTotal = currQuiz.getTimeLimit();

        // User current score
        userHighScoreTotal = CalculateScore.calculateScore(numCorrectQuestions, timeLimitTotal, timeLeft);
        textViewHolder = "Score: " + userHighScoreTotal;
        highScoreTV = findViewById(R.id.finalScore);
        highScoreTV.setText(textViewHolder);

        saveScore();

        showLeaderboard();

        buttonGoHome = findViewById(R.id.goHomeButton);
        buttonGoHome.setOnClickListener(button -> finish());
    }

    /**
     * Shows the leaderboard in the end screen
     * grabs the information from the logic layer based on the quiz
     */
    private void showLeaderboard() {
        List<UserQuizScore> userQuizScoreList = accessLeaderboard.getScoresForQuiz(currQuiz, 5);

        // Setting up the recycle view
        RecyclerView recyclerView = findViewById(R.id.EndLeaderBoardRecyclerView);
        LeaderboardRecycleViewAdapter adapter = new LeaderboardRecycleViewAdapter(this, userQuizScoreList,
                totalQuestionNumber);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Stores the users final score
     */
    private void saveScore() {
        // Quick Calc to get time taken
        int timeTaken = timeLimitTotal - timeLeft;
        // Stores the results of the quiz
        accessLeaderboard.CreateUserQuizScore(currUser, currQuiz, numCorrectQuestions, timeTaken, userHighScoreTotal);
    }

    private void quizEndAudio() {
        int luckyNumber = 7;
        int maxChance = 10;
        int minChance = 1;
        int range = maxChance - minChance + 1;
        MediaPlayer mediaPlayer;

        int audioChance = (int) (Math.random() * range) + minChance;

        if (audioChance == luckyNumber) {
            mediaPlayer = MediaPlayer.create(this, R.raw.haha_funny_sound);
        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.applause_sound_effect);
        }

        mediaPlayer.start();
    }
}