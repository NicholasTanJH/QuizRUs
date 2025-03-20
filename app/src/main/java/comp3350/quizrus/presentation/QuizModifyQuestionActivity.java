package comp3350.quizrus.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import comp3350.quizrus.business.AccessAnswers;
import comp3350.quizrus.business.AccessQuestions;
import comp3350.quizrus.business.AccessQuizzes;
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

    List<String[]> newQuestionAndAnswersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        newQuestionAndAnswersList = new ArrayList<>();

        setContentView(R.layout.activity_quiz_modify_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            questionEditText = findViewById(R.id.QuestionInput);
            correctAnswerEditText = findViewById(R.id.CorrectAnswerInput);
            wrongAnswerOneEditText = findViewById(R.id.WrongAnswerInputOne);
            wrongAnswerTwoEditText = findViewById(R.id.WrongAnswerInputTwo);
            wrongAnswerThreeEditText = findViewById(R.id.WrongAnswerInputThree);
            saveQuestionButton = findViewById(R.id.buttonSave);
            doneQuestionsButton = findViewById(R.id.buttonDoneEditQuestion);

            Intent intent = getIntent();
            currUser = (User) intent.getSerializableExtra("loggedInUser");
            quizName = intent.getStringExtra("quizName");
            timerAmount = intent.getIntExtra("timerAmount", 0);

            saveQuestionButton.setOnClickListener(button -> setupQuestion());

            doneQuestionsButton.setOnClickListener(button -> setupQuestions());

            return insets;
        });
    }

    private void setupQuestion() {
        // add the questions to the list
        String question = questionEditText.getText().toString();
        String correctAnswer = correctAnswerEditText.getText().toString();
        String wrongAnswerOne = wrongAnswerOneEditText.getText().toString();
        String wrongAnswerTwo = wrongAnswerTwoEditText.getText().toString();
        String wrongAnswerThree = wrongAnswerThreeEditText.getText().toString();

        if (question.isEmpty()) {
            setAlertMessage("No question entered", "Sorry, please enter a question for this quiz.");
        } else if (correctAnswer.isEmpty() || wrongAnswerOne.isEmpty() || wrongAnswerTwo.isEmpty()
                || wrongAnswerThree.isEmpty()) {
            setAlertMessage("Missing answer", "Sorry, please enter all answers to this question.");
        } else {
            String[] questionAndAnswers = new String[5];

            questionAndAnswers[0] = question;
            questionAndAnswers[1] = correctAnswer;
            questionAndAnswers[2] = wrongAnswerOne;
            questionAndAnswers[3] = wrongAnswerTwo;
            questionAndAnswers[4] = wrongAnswerThree;

            newQuestionAndAnswersList.add(questionAndAnswers);

            reset();
        }
    }

    // animation for button when question is saved successfully
    // clear out all the edit text
    private void reset() {
        questionEditText.setText("");
        correctAnswerEditText.setText("");
        wrongAnswerOneEditText.setText("");
        wrongAnswerTwoEditText.setText("");
        wrongAnswerThreeEditText.setText("");

        saveQuestionButton.setText("✓");
        new Handler().postDelayed(() -> {
            saveQuestionButton.setText(R.string.save_question);
        }, 1000);
    }

    private void setupQuestions() {
        if (newQuestionAndAnswersList.isEmpty()) {
            setAlertMessage(getString(R.string.no_questions_entered), getString(R.string.sorry_please_enter_at_least_one_question_with_answers));
        } else {
            // make new quiz
            AccessQuizzes accessQuizzes = new AccessQuizzes();
            Quiz newQuiz = accessQuizzes.createQuiz(currUser, quizName, timerAmount);

            // make new questions and answers
            AccessQuestions accessQuestions = new AccessQuestions();
            AccessAnswers accessAnswers = new AccessAnswers();
            for (String[] questionAndAnswers : newQuestionAndAnswersList) {
                String newQuestionName = questionAndAnswers[0];
                String newCorrectAnswer = questionAndAnswers[1];
                String newWrongAnswerOne = questionAndAnswers[2];
                String newWrongAnswerTwo = questionAndAnswers[3];
                String newWrongAnswerThree = questionAndAnswers[4];

                Question newQuestion = accessQuestions.createQuestion(newQuiz, newQuestionName, "MULTIPLE_CHOICE");
                accessAnswers.createAnswer(newCorrectAnswer, newQuestion, true);
                accessAnswers.createAnswer(newWrongAnswerOne, newQuestion, false);
                accessAnswers.createAnswer(newWrongAnswerTwo, newQuestion, false);
                accessAnswers.createAnswer(newWrongAnswerThree, newQuestion, false);
            }

            // return to QuizSelection page
            doneQuestionsButton.setText("✓");
            new Handler().postDelayed(() -> {
                finish();
            }, 500);
        }

    }

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