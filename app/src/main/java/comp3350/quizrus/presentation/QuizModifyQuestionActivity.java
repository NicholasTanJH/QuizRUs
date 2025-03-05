package comp3350.quizrus.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.R;
import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;

public class QuizModifyQuestionActivity extends AppCompatActivity {

    Button saveQuestionButton;
    Button doneQuestionsButton;
    TextInputEditText questionEditText;
    TextInputEditText correctAnswerEditText;
    TextInputEditText wrongAnswerOneEditText;
    TextInputEditText wrongAnswerTwoEditText;
    TextInputEditText wrongAnswerThreeEditText;
    User currUser;
    String quizName;
    int timerAmount;

    List<String> newQuestions;
    List<String> newAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        newQuestions = new ArrayList<>();
        newAnswers = new ArrayList<>();

        setContentView(R.layout.activity_quiz_modify_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            questionEditText = findViewById(R.id.QuestionInput);
            correctAnswerEditText = findViewById(R.id.CorrectAnswerInput);
            wrongAnswerOneEditText = findViewById(R.id.WrongAnswerInputOne);
            wrongAnswerTwoEditText = findViewById(R.id.WrongAnswerInputTwo);
            wrongAnswerThreeEditText = findViewById(R.id.WrongAnswerInputThree);
            doneQuestionsButton = findViewById(R.id.buttonDoneEditQuestion);

            Intent intent = getIntent();
            currUser = (User) intent.getSerializableExtra("loggedInUser");
            quizName = intent.getStringExtra("quizName");
            timerAmount = intent.getIntExtra("timerAmount", 0);

            saveQuestionButton.setOnClickListener(button ->
                setupQuestion()
            );

            doneQuestionsButton.setOnClickListener(button ->
                setupQuestions()
            );

            return insets;
        });
    }

    private void setupQuestion()
    {
        //add the questions to the list
        String question = questionEditText.getText().toString();
        String correctAnswer = correctAnswerEditText.getText().toString();
        String wrongAnswerOne = wrongAnswerOneEditText.getText().toString();
        String wrongAnswerTwo = wrongAnswerTwoEditText.getText().toString();
        String wrongAnswerThree = wrongAnswerThreeEditText.getText().toString();

        if(question.isEmpty())
        {
            setAlertMessage("No question entered", "Sorry, please enter a question for this quiz.");
        }
        else if(correctAnswer.isEmpty() || wrongAnswerOne.isEmpty() || wrongAnswerTwo.isEmpty() || wrongAnswerThree.isEmpty())
        {
            setAlertMessage("Missing answer", "Sorry, please enter all answers to this question.");
        }
        else
        {

        }
    }
    private void setupQuestions()
    {
        if(newQuestions.isEmpty() || newAnswers.isEmpty())
        {
            setAlertMessage("No questions entered", "Sorry, please enter at least one question with answers.");
        }
        else
        {
            for(String question : newQuestions)
            {

            }
            for(String answer : newAnswers)
            {

            }

            finish();
        }

    }

    private void setAlertMessage(String alertTitle, String alertMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        SpannableString spannableMessage = new SpannableString(alertMessage);
        spannableMessage.setSpan(new AbsoluteSizeSpan(30, true), 0, alertMessage.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

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