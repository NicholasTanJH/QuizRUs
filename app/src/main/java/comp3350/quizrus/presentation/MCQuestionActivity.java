package comp3350.quizrus.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import comp3350.quizrus.R;

public class MCQuestionActivity extends AppCompatActivity {
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private int lastPressedButtonId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mcquestion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setQuestionText("Question: How cold is today?");
        initButton();
    }

    //set the question text
    private void setQuestionText(String questionText) {
        TextView questionTv = findViewById(R.id.questionTV);
        questionTv.setText(questionText);
    }

    //set the option button text
    private void setOptionsText(String optionText, Button button){
           button.setText(optionText);
    }

    /*
    initialize the button
    add onclick listener that track the last pressed button id
    and change color when pressed
     */
    private void initButton() {
        option1 = findViewById(R.id.button1);
        option2 = findViewById(R.id.button2);
        option3 = findViewById(R.id.button3);
        option4 = findViewById(R.id.button4);

        option1.setOnClickListener(b -> {
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.button_clicked_color);
            option2.setBackgroundResource(R.drawable.button_default_color);
            option3.setBackgroundResource(R.drawable.button_default_color);
            option4.setBackgroundResource(R.drawable.button_default_color);
        });

        option2.setOnClickListener(b -> {
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.button_clicked_color);
            option1.setBackgroundResource(R.drawable.button_default_color);
            option3.setBackgroundResource(R.drawable.button_default_color);
            option4.setBackgroundResource(R.drawable.button_default_color);
        });

        option3.setOnClickListener(b -> {
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.button_clicked_color);
            option1.setBackgroundResource(R.drawable.button_default_color);
            option2.setBackgroundResource(R.drawable.button_default_color);
            option4.setBackgroundResource(R.drawable.button_default_color);
        });

        option4.setOnClickListener(b -> {
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.button_clicked_color);
            option1.setBackgroundResource(R.drawable.button_default_color);
            option2.setBackgroundResource(R.drawable.button_default_color);
            option3.setBackgroundResource(R.drawable.button_default_color);
        });
    }
}