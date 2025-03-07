package comp3350.quizrus.presentation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import comp3350.quizrus.business.Random;
import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;

public class MCQuestionActivity extends AppCompatActivity {
    // integer to keep track the option buttons
    // eg. 1 will be the first option button, and 4 is the forth
    private final int OPTION_BUTTON_1_ORDER_NUM = 0;
    private final int OPTION_BUTTON_2_ORDER_NUM = 1;
    private final int OPTION_BUTTON_3_ORDER_NUM = 2;
    private final int OPTION_BUTTON_4_ORDER_NUM = 3;
    // For getting data
    private final AccessAnswers accessAnswers = new AccessAnswers();
    private Quiz quiz;
    private List<Question> questions;
    // Number of question in this quiz
    private int totalQuestionCount;
    // Track the question no.
    private int questionNum = 0;
    // The button last pressed
    private int lastPressedButtonOrderNum = -1;
    // The right answer button
    private int rightAnswerButtonOrderNum = -1;
    private boolean isSubmitted = false;
    private Button optionButton1;
    private Button optionButton2;
    private Button optionButton3;
    private Button optionButton4;
    // A button for submit and next
    // proceed button is either in invisible mode, submit, or next mode
    private Button proceedButton;

    private long timeLeftInMillis;

    private MediaPlayer quizzingMusic;

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
        startTimer();
        startQuizMusic();
        reset();
    }

    @Override
    protected void onPause() {
        super.onPause();
        quizzingMusic.stop();
        quizzingMusic.release();
    }

    //get the Quiz object that is pressed and set up the page
    private void setUpQuestions() {
        AccessQuestions accessQuestions = new AccessQuestions();
        Intent intent = getIntent();

        quiz = (Quiz) intent.getSerializableExtra("currQuiz");
        questions = accessQuestions.getQuestions(quiz);
        Random.randomizeListItem(questions); // randomize
        totalQuestionCount = questions.size();
    }

    /*
     * initialize the option buttons
     * add onclick listener that:
     * - track the last pressed button id
     * - change the option button color when pressed
     * - make the proceed button into submit mode once any of the option button is
     * pressed
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

    private void setUpProceedButton() {
        proceedButton = findViewById(R.id.buttonProceed);
    }

    private void startQuizMusic() {
        quizzingMusic = MediaPlayer.create(this, R.raw.quiz_music);
        quizzingMusic.setLooping(true);
        quizzingMusic.start();
    }

    // reset the tracker for last pressed and right answer button
    // reset the UI to default, remove any color markings on the buttons
    // reset the proceed button to invisible mode
    // get the following question and it's answers, and update the text of the
    // question and the answer option buttons
    private void reset() {
        setProceedButtonToInvisibleMode();
        lastPressedButtonOrderNum = -1;

        // get the current question and it's answers
        Question currentQuestion = questions.get(questionNum);
        List<Answer> currentAnswers = accessAnswers.getAnswers(currentQuestion);
        Random.randomizeListItem(currentAnswers); // randomize the answers

        // get the position of the correct answer
        rightAnswerButtonOrderNum = accessAnswers.getCorrectAnswerPosition(currentAnswers);

        // set question text
        TextView questionTV = findViewById(R.id.questionTV);
        questionTV.setText(currentQuestion.getQuestionText());

        // set answer button text
        optionButton1.setText(currentAnswers.get(OPTION_BUTTON_1_ORDER_NUM).getAnswerText());
        optionButton2.setText(currentAnswers.get(OPTION_BUTTON_2_ORDER_NUM).getAnswerText());
        optionButton3.setText(currentAnswers.get(OPTION_BUTTON_3_ORDER_NUM).getAnswerText());
        optionButton4.setText(currentAnswers.get(OPTION_BUTTON_4_ORDER_NUM).getAnswerText());

        // clear the color of the selected option answer
        optionButton1.setBackgroundResource(R.drawable.question_option_button_default_color);
        optionButton2.setBackgroundResource(R.drawable.question_option_button_default_color);
        optionButton3.setBackgroundResource(R.drawable.question_option_button_default_color);
        optionButton4.setBackgroundResource(R.drawable.question_option_button_default_color);

        // set the option button clickable
        optionButton1.setClickable(true);
        optionButton2.setClickable(true);
        optionButton3.setClickable(true);
        optionButton4.setClickable(true);

        questionNum++;
    }

    // set the proceed button to invisible and unclickable
    private void setProceedButtonToInvisibleMode() {
        proceedButton.setVisibility(View.INVISIBLE);
        proceedButton.setEnabled(false);
    }

    // make the proceed button visible and usable
    // make it as a submit button
    // indicate which is the right answer and wrong if any
    private void setProceedButtonToSubmitMode() {
        isSubmitted = true;
        proceedButton.setText(R.string.submit);
        proceedButton.setVisibility(View.VISIBLE);
        proceedButton.setEnabled(true);
        proceedButton.setBackgroundResource(R.drawable.question_proceed_button_submit);
        proceedButton.setOnClickListener(proceedButton -> {
            // set the proceed button from the submit mode to next mode
            setProceedButtonToNextMode();
            // indicate which is the right answer by changing the color of the selection
            // item
            indicateRightAndWrongAnswer();
            // set the answer option buttons to unusable
            optionButton1.setClickable(false);
            optionButton2.setClickable(false);
            optionButton3.setClickable(false);
            optionButton4.setClickable(false);
        });
    }

    // make it as a next button
    // set if the last question is reached and the proceed button is pressed, back
    // out to the quiz selection page,
    // else, reset this page
    private void setProceedButtonToNextMode() {
        isSubmitted = false;
        proceedButton.setText(R.string.next);
        proceedButton.setBackgroundResource(R.drawable.question_proceed_button_next);
        proceedButton.setOnClickListener(proceedButton -> {
            if (totalQuestionCount == questionNum) {
                finish(); // get back to quiz selection page once reached the last question
            } else {
                // reset when still have more questions
                reset();
            }
        });
    }

    // mark the right answer option button to green, and red for the user's wrong
    // answer option button
    private void indicateRightAndWrongAnswer() {
        getButtonByOrderNum(lastPressedButtonOrderNum).setBackgroundResource(R.drawable.question_option_button_wrong);
        getButtonByOrderNum(rightAnswerButtonOrderNum).setBackgroundResource(R.drawable.question_option_button_right);

        boolean isRight = (lastPressedButtonOrderNum == rightAnswerButtonOrderNum);
        putRightOrWrongSound(isRight);
    }

    private void putRightOrWrongSound(boolean isRight) {
        MediaPlayer mediaPlayer;
        if(isRight){
            mediaPlayer = MediaPlayer.create(this, R.raw.yay);
        }else{
            mediaPlayer = MediaPlayer.create(this, R.raw.wrong);
        }

        mediaPlayer.start();
    }

    // find the Button using the given constant
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
            return null; // something is wrong
        }
    }

    //Responsible for the count down timer, and go back to main page when time's up
    private void startTimer() {
        TextView timerTV = findViewById(R.id.timerTV);
        timeLeftInMillis = quiz.getTimeLimit() * 1000L;

        new CountDownTimer(timeLeftInMillis, 1000) { // Tick every second
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int seconds = (int) (timeLeftInMillis / 1000);
                timerTV.setText(seconds + " s");
            }

            public void onFinish() {
                timerTV.setText("Time's up!");
                startFlashingEffect(timerTV);
                setProceedButtonToInvisibleMode();
                setSound();

                // Finish activity after 2 seconds (2000ms)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish(); // Close activity
                    }
                }, 2000);
            }
        }.start();
    }

    private void startFlashingEffect(TextView timerTV) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(timerTV, "alpha", 1f, 0f);
        animator.setDuration(500); // 500ms per blink
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    private void setSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.rooster);
        mediaPlayer.start();
    }
}