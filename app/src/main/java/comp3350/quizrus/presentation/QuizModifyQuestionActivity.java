package comp3350.quizrus.presentation;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import comp3350.quizrus.R;

public class QuizModifyQuestionActivity extends AppCompatActivity {

    Button saveQuestionButton;
    Button doneQuestionsButton;
    TextInputEditText questionEditText;
    TextInputEditText correctAnswerEditText;
    TextInputEditText wrongAnswerOneEditText;
    TextInputEditText wrongAnswerTwoEditText;
    TextInputEditText wrongAnswerThreeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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

//            saveQuestionButton.setOnClickListener(button ->
//
//            );

            doneQuestionsButton.setOnClickListener(button ->
                    setupQuestion()
            );

            return insets;
        });
    }
    private void setupQuestion()
    {
        finish();
    }
}