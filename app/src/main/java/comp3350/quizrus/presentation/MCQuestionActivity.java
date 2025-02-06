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
    //integer to keep track the option buttons
    //eg. 1 will be the first option button, and 4 is the forth
    final int OPTION_BUTTON_1_ORDER_NUM = 1;
    final int OPTION_BUTTON_2_ORDER_NUM = 2;
    final int OPTION_BUTTON_3_ORDER_NUM = 3;
    final int OPTION_BUTTON_4_ORDER_NUM = 4;
    private Button optionButton1;
    private Button optionButton2;
    private Button optionButton3;
    private Button optionButton4;
    //A button for submit and next
    private Button proceedButton;
    //keep track of which button is last pressed before clicking submit button
    private int lastPressedButtonOrderNum = -1;
    //The button that is the right answer
    private int rightAnswerButtonOrderNum = -1;
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

        setUpOptionButtons();
        setUpProceedButton();
        reset();
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
            if (!isSubmitted)
                setProceedButtonToSubmitMode();
            lastPressedButtonOrderNum = OPTION_BUTTON_1_ORDER_NUM;
            b.setBackgroundResource(R.drawable.question_option_button_clicked_color);
            optionButton2.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton3.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton4.setBackgroundResource(R.drawable.question_option_button_default_color);
        });

        optionButton2.setOnClickListener(b -> {
            if (!isSubmitted)
                setProceedButtonToSubmitMode();
            lastPressedButtonOrderNum = OPTION_BUTTON_2_ORDER_NUM;
            b.setBackgroundResource(R.drawable.question_option_button_clicked_color);
            optionButton1.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton3.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton4.setBackgroundResource(R.drawable.question_option_button_default_color);
        });

        optionButton3.setOnClickListener(b -> {
            if (!isSubmitted)
                setProceedButtonToSubmitMode();
            lastPressedButtonOrderNum = OPTION_BUTTON_3_ORDER_NUM;
            b.setBackgroundResource(R.drawable.question_option_button_clicked_color);
            optionButton1.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton2.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton4.setBackgroundResource(R.drawable.question_option_button_default_color);
        });

        optionButton4.setOnClickListener(b -> {
            if (!isSubmitted)
                setProceedButtonToSubmitMode();
            lastPressedButtonOrderNum = OPTION_BUTTON_4_ORDER_NUM;
            b.setBackgroundResource(R.drawable.question_option_button_clicked_color);
            optionButton1.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton2.setBackgroundResource(R.drawable.question_option_button_default_color);
            optionButton3.setBackgroundResource(R.drawable.question_option_button_default_color);
        });
    }

    /*
    initialize the proceedButton by finding the id
    make it into invisible mode
    **proceed button is either in invisible mode, submit, or next mode**
     */
    private void setUpProceedButton() {
        proceedButton = findViewById(R.id.buttonProceed);
    }

    //reset the whole page including the tracker for last pressed and right answer button
    //and reset the UI to default, remove any color markings on the buttons and reset the proceed button
    private void reset() {
        setProceedButtonToInvisibleMode();
        lastPressedButtonOrderNum = -1;
        rightAnswerButtonOrderNum = 1; //TODO: get it from data
        TextView questionTV = findViewById(R.id.questionTV);
        questionTV.setText("How cold is today?");
        optionButton1.setText("10");
        optionButton2.setText("0");
        optionButton3.setText("-10");
        optionButton4.setText("-20");
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
            //indicate which is the right answer by changing the color of the selection item
            indicateRightAndWrongAnswer();
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
            reset();
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

    //mark the right answer option button to green, and red for the user's wrong answer option button
    private void indicateRightAndWrongAnswer() {
        getButtonByOrderNum(rightAnswerButtonOrderNum).setBackgroundResource(R.drawable.question_option_button_wrong);
        getButtonByOrderNum(lastPressedButtonOrderNum).setBackgroundResource(R.drawable.question_option_button_right);
    }

    //find the Button using the given constant
    private Button getButtonByOrderNum(int optionOrderNum) {
        if (optionOrderNum == OPTION_BUTTON_1_ORDER_NUM) {
            return optionButton1;
        } else if (optionOrderNum == OPTION_BUTTON_2_ORDER_NUM) {
            return optionButton2;
        } else if (optionOrderNum == OPTION_BUTTON_3_ORDER_NUM) {
            return optionButton3;
        } else if (optionOrderNum == OPTION_BUTTON_4_ORDER_NUM) {
            return optionButton4;
        } else {
            return null; //something is wrong
        }
    }
}