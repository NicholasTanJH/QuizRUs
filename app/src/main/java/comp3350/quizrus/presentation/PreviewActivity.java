package comp3350.quizrus.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.presentation.adapter.LeaderboardRecycleViewAdapter;

public class PreviewActivity extends AppCompatActivity {
    Quiz currQuiz;
    List<Question> questions;
    ImageButton buttonBack;
    TextView quizTitleTV;
    TextView creatorTV;
    TextView timeLimitTV;
    TextView questionNumberTV;
    Button buttonStart;
    int totalQuestionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_preview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        currQuiz = (Quiz) intent.getSerializableExtra("currQuiz");

        AccessQuestions accessQuestions = new AccessQuestions();
        questions = accessQuestions.getQuestions(currQuiz);

        //back button
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(button -> finish());

        //Quiz title
        String quizTitle = currQuiz.getTitle();
        quizTitleTV = findViewById(R.id.quizTitleTextView);
        quizTitleTV.setText(quizTitle);

        //Creator
        String creator = currQuiz.getUser().getUsername();
        String creatorDisplayText = "Created by " + creator;
        creatorTV = findViewById(R.id.creatorTextView);
        creatorTV.setText(creatorDisplayText);

        //Time limit
        int timeLimit = currQuiz.getTimeLimit();
        String timeLimitDisplayText = "Time limit: " + Integer.toString(timeLimit) + "s";
        timeLimitTV = findViewById(R.id.timeLimitTextView);
        timeLimitTV.setText(timeLimitDisplayText);

        //# of Questions
        totalQuestionNumber = questions.size();
        String questionNumberDisplayText = "No. of Question: " + totalQuestionNumber;
        questionNumberTV = findViewById(R.id.questionNumberTextView);
        questionNumberTV.setText(questionNumberDisplayText);

        //start button
        buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(button -> startQuiz());

        showLeaderboard();
    }

    private void startQuiz() {
        Intent intent = new Intent(this, MCQuestionActivity.class);
        intent.putExtra("currQuiz", currQuiz); // pass the Quiz object that is pressed
        this.startActivity(intent);
        finish();
    }

    private void showLeaderboard() {
        AccessLeaderboard accessLeaderboard = new AccessLeaderboard();
        List<UserQuizScore> userQuizScoreList = accessLeaderboard.getScoresForQuiz(currQuiz);

        // Setting up the recycle view
        RecyclerView recyclerView = findViewById(R.id.leaderboardRecyclerView);
        LeaderboardRecycleViewAdapter adapter = new LeaderboardRecycleViewAdapter(this, userQuizScoreList, totalQuestionNumber);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}