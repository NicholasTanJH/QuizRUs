package comp3350.quizrus.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import comp3350.quizrus.R;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;

public class QuizCreationActivity extends AppCompatActivity {
    ImageButton buttonBack;
    Button editQuestionButton;
    TextInputEditText quizNameEditText;
    TextInputEditText timerAmountEditText;
    User currUser;
    Quiz currQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_creation);

        Intent intent = getIntent();
        currUser = (User) intent.getSerializableExtra("loggedInUser");
        currQuiz = null;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            buttonBack = findViewById(R.id.buttonBackCreateQuiz);
            editQuestionButton = findViewById(R.id.buttonEditQuestion);
            quizNameEditText = findViewById(R.id.QuizNameInput);
            timerAmountEditText = findViewById(R.id.TimerInput);

            buttonBack.setOnClickListener(button -> finish());

            editQuestionButton.setOnClickListener(button -> setupQuizInfo());

            return insets;
        });

    }

    /**
     * Record the new quiz info
     * Invalid quiz input format will be notified through alertMessage popup
     */
    private void setupQuizInfo() {
        Intent intent;
        String quizName = quizNameEditText.getText().toString();
        String timerString = timerAmountEditText.getText().toString();

        int timerAmount;

        if (quizName.isEmpty()) {
            setAlertMessage(getString(R.string.no_quiz_name), getString(R.string.please_enter_a_quiz_name));
        } else if (timerString.isEmpty()) {
            setAlertMessage(getString(R.string.not_a_number),
                    getString(R.string.please_enter_a_number_into_the_timer_field));
        } else {
            timerAmount = Integer.parseInt(timerString);
            intent = new Intent(this, QuizModifyQuestionActivity.class);

            intent.putExtra("loggedInUser", currUser);
            intent.putExtra("quizName", quizName);
            intent.putExtra("timerAmount", timerAmount);

            this.startActivity(intent);
            finish();
        }
    }

    /**
     * This method is called whenever user want to save a new question they have
     * created
     * Save the user input into a list to record the new question they create
     * Invalid question input format will be notified through alertMessage popup
     */
    private void setAlertMessage(String alertTitle, String alertMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        SpannableString spannableMessage = new SpannableString(alertMessage);
        spannableMessage.setSpan(new AbsoluteSizeSpan(30, true), 0, alertMessage.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setTitle(alertTitle)
                .setMessage(spannableMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Dismiss dialog on OK click
                    }
                })
                .show();
    }
}