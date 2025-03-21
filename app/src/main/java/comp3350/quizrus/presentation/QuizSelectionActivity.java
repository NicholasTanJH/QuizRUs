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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class QuizSelectionActivity extends Activity {
    AccessQuizzes accessQuiz;
    List<Quiz> quizzes;
    User currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_selection);

        Intent intent = getIntent();
        currUser = (User) intent.getSerializableExtra("loggedInUser");
        accessQuiz = new AccessQuizzes();

        setUpSearchButton();
        getQuizzes();
        showQuizzes();
        setUpUserIcon();
        setUpAddQuizIcon();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getQuizzes();
        showQuizzes();
    }

    private void setUpSearchButton() {
        EditText searchET = findViewById(R.id.searchInput);
        ImageButton searchButton = findViewById(R.id.searchButton);
        LinearLayout searchResult = findViewById(R.id.searchResult);
        TextView searchResultTV = findViewById(R.id.searchResultTV);
        ImageButton searchResultCancerButton = findViewById(R.id.searchResultCancerButton);

        searchResult.setVisibility(View.GONE);

        searchButton.setOnClickListener(button -> {
            String searchText = searchET.getText().toString();
            searchET.getText().clear();

            if (!searchText.isEmpty()) {
                quizzes = accessQuiz.searchQuizzes(searchText);
                showQuizzes();

                searchResult.setVisibility(View.VISIBLE);
                int resultCount = quizzes.size();
                String searchResultMessage = resultCount + " results found from \"" + searchText + "\"";
                searchResultTV.setText(searchResultMessage);
            }
        });

        searchResultCancerButton.setOnClickListener(button -> {
            searchResult.setVisibility(View.GONE);
            getQuizzes();
            showQuizzes();
        });
    }

    private void showQuizzes() {
        // Setting up the recycle view
        RecyclerView recyclerView = findViewById(R.id.listQuiz);
        QuizRecycleViewAdapter adapter = new QuizRecycleViewAdapter(this, quizzes, currUser);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getQuizzes() {
        quizzes = accessQuiz.getQuizzes();
    }

    private void setUpUserIcon() {
        ImageButton accountButton = findViewById(R.id.accountImageButton);
        accountButton.setOnClickListener(button -> showPopupSignOutMenu(button));
    }

    private void showPopupSignOutMenu(View view) {
        PopupMenu signOutPopUp = new PopupMenu(this, view);
        MenuInflater inflater = signOutPopUp.getMenuInflater();
        inflater.inflate(R.menu.menu_account, signOutPopUp.getMenu());

        // Set the username in the popup menu
        MenuItem userMenuItem = signOutPopUp.getMenu().findItem(R.id.menu_username);
        if (userMenuItem != null) {
            userMenuItem.setTitle(currUser.getUsername());
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

    private void setUpAddQuizIcon() {
        ImageButton createQuizButton = findViewById(R.id.newQuizButton);
        createQuizButton.setOnClickListener(button -> startCreatingNewQuiz());
    }

    private void startCreatingNewQuiz() {
        Intent intent = new Intent(this, QuizCreationActivity.class);
        intent.putExtra("loggedInUser", currUser);
        this.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
