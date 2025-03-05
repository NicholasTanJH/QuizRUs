package comp3350.quizrus.presentation;

import comp3350.quizrus.R;
import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.presentation.adapter.QuizRecycleViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class QuizSelectionActivity extends Activity {
    List<Quiz> quizzes;
    User currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_selection);

        addQuizTitles();

        // Setting up the recycle view
        RecyclerView recyclerView = findViewById(R.id.listQuiz);
        QuizRecycleViewAdapter adapter = new QuizRecycleViewAdapter(this, quizzes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // User Account
        ImageButton accountButton = findViewById(R.id.accountImageButton);
        ImageButton createQuizButton = findViewById(R.id.newQuizButton);
        accountButton.setOnClickListener(button -> showPopupSignOutMenu(button));
        createQuizButton.setOnClickListener(button -> startCreatingNewQuiz());

        Intent intent = getIntent();
        currUser = (User) intent.getSerializableExtra("loggedInUser");
    }

    private void showPopupSignOutMenu(View view) {
        PopupMenu signOutPopUp = new PopupMenu(this, view);
        MenuInflater inflater = signOutPopUp.getMenuInflater();
        inflater.inflate(R.menu.menu_account, signOutPopUp.getMenu());

        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");

        // Set the username in the popup menu
        MenuItem userMenuItem = signOutPopUp.getMenu().findItem(R.id.menu_username);
        if (userMenuItem != null) {
            userMenuItem.setTitle(username);
        }

        signOutPopUp.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_sign_out) {
                Intent intent = new Intent(QuizSelectionActivity.this, UserLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
                startActivity(intent);
                finish(); // Close current activity
                return true;
            }
            return false;
        });

        signOutPopUp.show();
    }

    private void startCreatingNewQuiz()
    {
        Intent intent = new Intent(this, QuizCreationActivity.class);
        intent.putExtra("loggedInUser", currUser);
        this.startActivity(intent);
    }

    private void addQuizTitles() {
        quizzes = new AccessQuizzes().getQuizzes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
