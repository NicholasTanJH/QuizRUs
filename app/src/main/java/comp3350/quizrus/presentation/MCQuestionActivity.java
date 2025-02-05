package comp3350.quizrus.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import comp3350.quizrus.R;

public class MCQuestionActivity extends AppCompatActivity {
    private Button optionButton1;
    private Button optionButton2;
    private Button optionButton3;
    private Button optionButton4;
    //keep track of which button is last pressed before clicking submit button
    private int lastPressedButtonId = -1;
    private Button proceedButton;
    private boolean isSubmitted = false;


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
        setUpOptionButtons();
        setUpProceedButton();
    }

    //set the question text
    private void setQuestionText(String questionText) {
        TextView questionTv = findViewById(R.id.questionTV);
        questionTv.setText(questionText);
    }

    /*
    initialize the option buttons
    add onclick listener that:
        track the last pressed button id
        change the option button color when pressed
        make the proceed button into submit mode once any of the option button is pressed
     */
    private void setUpOptionButtons() {
        optionButton1 = findViewById(R.id.button1);
        optionButton2 = findViewById(R.id.button2);
        optionButton3 = findViewById(R.id.button3);
        optionButton4 = findViewById(R.id.button4);

        optionButton1.setOnClickListener(b -> {
            if(!isSubmitted)
                setProceedButtonToSubmitMode();
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.question_option_button_clicked_color);
            optionButton2.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton3.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton4.setBackgroundResource(R.drawable.question_option_button_default_color);
        });

        optionButton2.setOnClickListener(b -> {
            if(!isSubmitted)
                setProceedButtonToSubmitMode();
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.question_option_button_clicked_color);
            optionButton1.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton3.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton4.setBackgroundResource(R.drawable.question_option_button_default_color);
        });

        optionButton3.setOnClickListener(b -> {
            if(!isSubmitted)
                setProceedButtonToSubmitMode();
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.question_option_button_clicked_color);
            optionButton1.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton2.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton4.setBackgroundResource(R.drawable.question_option_button_default_color);
        });

        optionButton4.setOnClickListener(b -> {
            if(!isSubmitted)
                setProceedButtonToSubmitMode();
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.question_option_button_clicked_color);
            optionButton1.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton2.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton3.setBackgroundResource(R.drawable.question_option_button_default_color);
        });
    }

    //set the option button text
    private void setOptionsText(String optionText, Button button) {
        button.setText(optionText);
    }

    /*
    initialize the proceedButton by finding the id
    make it into invisible mode
    **proceed button is either in invisible mode, submit, or next mode**
     */
    private void setUpProceedButton() {
        proceedButton = findViewById(R.id.buttonProceed);
        setProceedButtonToInvisibleMode();
    }

    //set the proceed button to invisible and unclickable
    private void setProceedButtonToInvisibleMode() {
        proceedButton.setVisibility(View.INVISIBLE);
        proceedButton.setEnabled(false);
    }

    //make the proceed button visible and usable
    //make it as a submit button
    private void setProceedButtonToSubmitMode() {
        isSubmitted = true;
        proceedButton.setText(R.string.submit);
        proceedButton.setVisibility(View.VISIBLE);
        proceedButton.setEnabled(true);
        proceedButton.setBackgroundResource(R.drawable.question_proceed_button_submit);
        proceedButton.setOnClickListener(proceedButton -> {
            //set the proceed button from the submit mode to next mode
            setProceedButtonToNextMode();
            //set the answer option buttons to unusable
            optionButton1.setClickable(false);
            optionButton2.setClickable(false);
            optionButton3.setClickable(false);
            optionButton4.setClickable(false);
        });
    }

    //make it as a next button
    private void setProceedButtonToNextMode() {
        isSubmitted = false;
        proceedButton.setText(R.string.next);
        proceedButton.setBackgroundResource(R.drawable.question_proceed_button_next);
        proceedButton.setOnClickListener(proceedButton -> {
            //set the proceed button from next mode to invisible mode
            setProceedButtonToInvisibleMode();
            //clear the color of the selected option answer
            optionButton1.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton2.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton3.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton4.setBackgroundResource(R.drawable.question_option_button_default_color);
            //set the option button clickable
            optionButton1.setClickable(true);
            optionButton2.setClickable(true);
            optionButton3.setClickable(true);
            optionButton4.setClickable(true);
        });
    }
}