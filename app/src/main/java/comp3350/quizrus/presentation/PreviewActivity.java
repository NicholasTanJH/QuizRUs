package comp3350.quizrus.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.presentation.adapter.LeaderboardRecycleViewAdapter;

public class PreviewActivity extends AppCompatActivity {
    AccessLeaderboard accessLeaderboard;
    User currUser;
    Quiz currQuiz;
    List<Question> questions;
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

        // AccessLeaderboard
        accessLeaderboard = new AccessLeaderboard();

        // Get intent passing variables
        Intent intent = getIntent();
        currQuiz = (Quiz) intent.getSerializableExtra("currQuiz");
        currUser = (User) intent.getSerializableExtra("currUser");

        AccessQuestions accessQuestions = new AccessQuestions();
        questions = accessQuestions.getQuestions(currQuiz);

        setUpQuizInfo();
        setUpButtons();
        showLeaderboard();
    }

    private void setUpQuizInfo() {
        // Quiz title
        String quizTitle = currQuiz.getTitle();
        TextView quizTitleTV = findViewById(R.id.quizTitleTextView);
        quizTitleTV.setText(quizTitle);

        // Creator
        String creator = currQuiz.getUser().getUsername();
        String creatorDisplayText = String.format(getString(R.string.created_by), creator);
        TextView creatorTV = findViewById(R.id.creatorTextView);
        creatorTV.setText(creatorDisplayText);

        // Time limit
        int timeLimit = currQuiz.getTimeLimit();
        String timeLimitDisplayText = String.format(getString(R.string.time_limit_text), timeLimit, "s");
        TextView timeLimitTV = findViewById(R.id.timeLimitTextView);
        timeLimitTV.setText(timeLimitDisplayText);

        // # of Questions
        totalQuestionNumber = questions.size();
        String questionNumberDisplayText = String.format(getString(R.string.question_number_text), totalQuestionNumber);
        TextView questionNumberTV = findViewById(R.id.questionNumberTextView);
        questionNumberTV.setText(questionNumberDisplayText);

        // User top score
        TextView userHighScoreTV = findViewById(R.id.userHighScoreTV);
        int userHighScore = accessLeaderboard.getUserHighScore(currQuiz, currUser);
        // no change; the display will be "-"
        if (userHighScore != 0) {
            userHighScoreTV.setText(String.valueOf(userHighScore));
        }

        // User attempts
        TextView userAttemptsTV = findViewById(R.id.userAttemptsTV);
        int userAttempts = accessLeaderboard.getNumAttempts(currQuiz, currUser);
        // no change; the display will be "-"
        if (userAttempts != 0) {
            userAttemptsTV.setText(String.valueOf(userAttempts));
        }
    }

    private void setUpButtons() {
        // back button
        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(button -> finish());

        // start button
        Button buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(button -> startQuiz());

        // Delete Quiz button
        AccessQuizzes accessQuizzes = new AccessQuizzes();
        boolean isQuizBelongsToUser = accessQuizzes.isQuizBelongsToUser(currQuiz, currUser);
        Button buttonDeleteQuiz = findViewById(R.id.buttonDeleteQuiz);
        TextView deleteTV = findViewById(R.id.deleteTV);
        if (isQuizBelongsToUser) {
            deleteTV.setVisibility(View.GONE);
            buttonDeleteQuiz.setOnClickListener(b -> {
                // popup for delete confirmation
                new AlertDialog.Builder(this)
                        .setTitle(R.string.confirm_deletion)
                        .setMessage(R.string.are_you_sure_you_want_to_delete_this_quiz_this_action_cannot_be_undone)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                accessQuizzes.deleteQuiz(currQuiz, currUser);
                                // exit this page
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });
        } else {
            buttonDeleteQuiz.setEnabled(false);
            buttonDeleteQuiz.setAlpha(0.5f);
        }
    }

    /**
     * Show top 5 scores in the leaderboard
     */
    private void showLeaderboard() {
        List<UserQuizScore> userQuizScoreList = accessLeaderboard.getScoresForQuiz(currQuiz, 5);

        // Setting up the recycle view
        RecyclerView recyclerView = findViewById(R.id.leaderboardRecyclerView);
        LeaderboardRecycleViewAdapter adapter = new LeaderboardRecycleViewAdapter(this, userQuizScoreList,
                totalQuestionNumber);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Go to MCQuestion page
     */
    private void startQuiz() {
        Intent intent = new Intent(this, MCQuestionActivity.class);
        intent.putExtra("currQuiz", currQuiz); // pass the Quiz object that is pressed
        intent.putExtra("currUser", currUser); // pass the User object that is pressed
        this.startActivity(intent);
        finish();
    }
}