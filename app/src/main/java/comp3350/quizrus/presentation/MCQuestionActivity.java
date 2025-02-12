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

import java.util.List;

import comp3350.quizrus.R;
import comp3350.quizrus.business.AccessAnswers;
import comp3350.quizrus.business.AccessQuestions;
import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.business.Random;
import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;

public class MCQuestionActivity extends AppCompatActivity {
    //integer to keep track the option buttons
    //eg. 1 will be the first option button, and 4 is the forth
    private final int OPTION_BUTTON_1_ORDER_NUM = 0;
    private final int OPTION_BUTTON_2_ORDER_NUM = 1;
    private final int OPTION_BUTTON_3_ORDER_NUM = 2;
    private final int OPTION_BUTTON_4_ORDER_NUM = 3;

    //For getting data
    private final AccessAnswers accessAnswers = new AccessAnswers();
    private List<Question> questions;
    //Number of question in this quiz
    private int totalQuestionCount;
    //Track the question no.
    private int questionNum = 0;
    //The button last pressed
    private int lastPressedButtonOrderNum = -1;
    //The right answer button
    private int rightAnswerButtonOrderNum = -1;
    private boolean isSubmitted = false;
    private Button optionButton1;
    private Button optionButton2;
    private Button optionButton3;
    private Button optionButton4;
    //A button for submit and next
    private Button proceedButton;

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

        setUpQuestions();
        setUpOptionButtons();
        setUpProceedButton();
        reset();
    }

    //get the quiz number from last activity, find the quiz by the quiz number
    //get the questions from the quiz
    //randomize the questions
    //get the total question count
    private void setUpQuestions() {
        AccessQuizzes quizzes = new AccessQuizzes();
        List<Quiz> quizList = quizzes.getQuizzes();
        int quizNum = getIntent().getIntExtra("quizNum", -1); //get the quiz number user pressed from last activity
        Quiz quiz = quizList.get(quizNum);
        AccessQuestions accessQuestions = new AccessQuestions();
        questions = accessQuestions.getQuestions(quiz);
        Random.randomizeListItem(questions); //randomize
        totalQuestionCount = questions.size();
    }

    /*
    initialize the option buttons
    add onclick listener that:
        - track the last pressed button id
        - change the option button color when pressed
        - make the proceed button into submit mode once any of the option button is pressed
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

    //reset the tracker for last pressed and right answer button
    //reset the UI to default, remove any color markings on the buttons
    //reset the proceed button to invisible mode
    //get the following question and it's answers, and update the text of the question and the answer option buttons
    private void reset() {
        setProceedButtonToInvisibleMode();
        lastPressedButtonOrderNum = -1;

        //get the current question and it's answers
        Question currentQuestion = questions.get(questionNum);
        List<Answer> currentAnswers = accessAnswers.getAnswers(currentQuestion);
        Random.randomizeListItem(currentAnswers); //randomize the answers

        //get the position of the correct answer
        rightAnswerButtonOrderNum = accessAnswers.getCorrectAnswerPosition(currentAnswers);

        //set question text
        TextView questionTV = findViewById(R.id.questionTV);
        questionTV.setText(currentQuestion.getQuestionText());

        //set answer button text
        optionButton1.setText(currentAnswers.get(OPTION_BUTTON_1_ORDER_NUM).getAnswerText());
        optionButton2.setText(currentAnswers.get(OPTION_BUTTON_2_ORDER_NUM).getAnswerText());
        optionButton3.setText(currentAnswers.get(OPTION_BUTTON_3_ORDER_NUM).getAnswerText());
        optionButton4.setText(currentAnswers.get(OPTION_BUTTON_4_ORDER_NUM).getAnswerText());

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

        questionNum++;
    }

    //set the proceed button to invisible and unclickable
    private void setProceedButtonToInvisibleMode() {
        proceedButton.setVisibility(View.INVISIBLE);
        proceedButton.setEnabled(false);
    }

    //make the proceed button visible and usable
    //make it as a submit button
    //indicate which is the right answer and wrong if any
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
    //set if the last question is reached and the proceed button is pressed, back out to the quiz selection page,
    //else, reset this page
    private void setProceedButtonToNextMode() {
        isSubmitted = false;
        proceedButton.setText(R.string.next);
        proceedButton.setBackgroundResource(R.drawable.question_proceed_button_next);
        proceedButton.setOnClickListener(proceedButton -> {
            if(totalQuestionCount == questionNum){
                finish(); //get back to quiz selection page once reached the last question
            }else{
                //reset when still have more questions
                reset();
            }
        });
    }

    //mark the right answer option button to green, and red for the user's wrong answer option button
    private void indicateRightAndWrongAnswer() {
        getButtonByOrderNum(lastPressedButtonOrderNum).setBackgroundResource(R.drawable.question_option_button_wrong);
        getButtonByOrderNum(rightAnswerButtonOrderNum).setBackgroundResource(R.drawable.question_option_button_right);
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