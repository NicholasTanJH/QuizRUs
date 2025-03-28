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
    User currUser;
    RecyclerView recyclerViewListQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_selection);

        Intent intent = getIntent();
        currUser = (User) intent.getSerializableExtra("loggedInUser");
        accessQuiz = new AccessQuizzes();

        recyclerViewListQuiz = findViewById(R.id.listQuiz);

        setUpSearch();
        showQuizzes(getAllQuizzes());
        setUpUserIcon();
        setUpAddQuizIcon();
    }


    /**
     * Refresh the listQuiz RecyclerView to display all quizzes
     */
    @Override
    protected void onResume() {
        super.onResume();
        showQuizzes(getAllQuizzes());
    }

    /**
     * Once search button is clicked, get the input text from search bar and pass it to logic to search
     * Refresh the quiz recycle view list once got the returned search list
     * Display the search result
     * Set up the "x" cancel button where it will remove the search query when clicked
     */
    private void setUpSearch() {
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
                List<Quiz> searchedQuizzes = accessQuiz.searchQuizzes(searchText);
                showQuizzes(searchedQuizzes);

                //Search result
                searchResult.setVisibility(View.VISIBLE);
                int resultCount = searchedQuizzes.size();
                String searchResultMessage = String.format(getString(R.string.search_result_message), resultCount, searchText);
                searchResultTV.setText(searchResultMessage);
            }
        });

        // "x" cancel button
        searchResultCancerButton.setOnClickListener(button -> {
            searchResult.setVisibility(View.GONE);
            showQuizzes(getAllQuizzes());
        });
    }

    /**
     * Display quizzes in the listQuiz recycle view
     *
     * @param quizzes list of Quiz to be displayed
     */
    private void showQuizzes(List<Quiz> quizzes) {
        // Setting up the recycle view
        QuizRecycleViewAdapter adapter = new QuizRecycleViewAdapter(this, quizzes, currUser);
        recyclerViewListQuiz.setAdapter(adapter);
        recyclerViewListQuiz.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * @return All the quizzes
     */
    private List<Quiz> getAllQuizzes() {
        return accessQuiz.getQuizzes();
    }

    private void setUpUserIcon() {
        ImageButton accountButton = findViewById(R.id.accountImageButton);
        accountButton.setOnClickListener(button -> showPopupSignOutMenu(button));
    }

    /**
     * Show the popup that contains the username and sign out button
     *
     * @param view The account icon image button
     */
    private void showPopupSignOutMenu(View view) {
        PopupMenu signOutPopUp = new PopupMenu(this, view);
        MenuInflater inflater = signOutPopUp.getMenuInflater();
        inflater.inflate(R.menu.menu_account, signOutPopUp.getMenu());

        // Set the username in the popup menu
        MenuItem userMenuItem = signOutPopUp.getMenu().findItem(R.id.menu_username);
        if (userMenuItem != null) {
            userMenuItem.setTitle(currUser.getUsername());
        }

        // Set up the sign out button
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

    /**
     * Go to QuizCreation page
     */
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
