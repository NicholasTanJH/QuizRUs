package comp3350.quizrus.presentation;

import comp3350.quizrus.R;
import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.presentation.adapter.QuizRecycleViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizSelectionActivity extends Activity {
    List<Quiz> quizzes;

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
        accountButton.setOnClickListener(button -> showPopupSignOutMenu(button));
    }

    private void showPopupSignOutMenu(View view) {
        PopupMenu signOutPopUp = new PopupMenu(this, view);
        MenuInflater inflater = signOutPopUp.getMenuInflater();
        inflater.inflate(R.menu.menu_account, signOutPopUp.getMenu());

        signOutPopUp.setOnMenuItemClickListener(item -> {
            //TODO: go back to login page, look up how to go to next activity, Intent and startActivity()
            return true;
        });

        signOutPopUp.show();
    }

    private void addQuizTitles() {
        quizzes = new AccessQuizzes().getQuizzes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
