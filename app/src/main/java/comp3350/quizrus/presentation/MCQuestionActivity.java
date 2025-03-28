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

import java.util.Collections;
import java.util.List;

import comp3350.quizrus.R;
import comp3350.quizrus.business.AccessAnswers;
import comp3350.quizrus.business.AccessQuestions;
import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;

public class MCQuestionActivity extends AppCompatActivity {
    private final int MILLIS_CONVERT = 1000;
    private final AccessAnswers accessAnswers = new AccessAnswers();
    private Quiz quiz;
    private List<Question> questions;
    private User currUser;
    // Number of question in this quiz
    private int totalQuestionCount;
    // Question No.
    private int questionNum = 0;
    private int score = 0;
    // The button last pressed
    private Button lastPressedButton = null;
    // The right answer button
    private Button rightAnswerButton = null;
    private boolean isSubmitted = false;
    private Button optionButton1;
    private Button optionButton2;
    private Button optionButton3;
    private Button optionButton4;
    private Button[] optionButtons;
    // Either in invisible mode, submit, or next mode
    private Button proceedButton;
    // Time Left
    private long timeLeftInMillis;
    // Music
    private MediaPlayer quizzingMusic;
    private CountDownTimer countDownTimer;

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

        setUpQuestionsAndUser();
        setUpOptionButtons();
        setUpProceedButton();
        startTimer();
        startQuizMusic();
        reset();
    }

    /**
     * Stop music and timer when this activity is finished or stopped
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (quizzingMusic != null) {
            if (quizzingMusic.isPlaying()) {
                quizzingMusic.stop();
            }
            quizzingMusic.release();
            quizzingMusic = null;
        }

        //stop timer
        countDownTimer.cancel();
    }


    /**
     * Get intent passing variables (Quiz to be played, and User playing)
     */
    private void setUpQuestionsAndUser() {
        AccessQuestions accessQuestions = new AccessQuestions();
        Intent intent = getIntent();
        quiz = (Quiz) intent.getSerializableExtra("currQuiz");
        questions = accessQuestions.getQuestions(quiz);
        Collections.shuffle(questions); // randomize
        totalQuestionCount = questions.size();

        //get User
        currUser = (User) intent.getSerializableExtra("currUser");
    }


    /**
     * Set up the option buttons to have response when clicked
     */
    private void setUpOptionButtons() {
        optionButton1 = findViewById(R.id.button1);
        optionButton2 = findViewById(R.id.button2);
        optionButton3 = findViewById(R.id.button3);
        optionButton4 = findViewById(R.id.button4);

        optionButtons = new Button[]{optionButton1, optionButton2, optionButton3, optionButton4};

        for (Button optionButton : optionButtons) {
            optionButton.setOnClickListener(b -> handleOptionButtonClick((Button) b, optionButtons));
        }
    }

    /**
     * Set up setOnClickListener to keep track on clicked option button
     * @param clickedButton Clicked button
     * @param optionButtons List of the four option buttons
     */
    private void handleOptionButtonClick(Button clickedButton, Button[] optionButtons) {
        if (!isSubmitted) {
            setProceedButtonToSubmitMode();
        }

        lastPressedButton = clickedButton;

        for (Button button : optionButtons) {
            if (button == clickedButton) {
                button.setBackgroundResource(R.drawable.question_option_button_clicked_color);
            } else {
                button.setBackgroundResource(R.drawable.question_option_button_default_color);
            }
        }
    }

    private void setUpProceedButton() {
        proceedButton = findViewById(R.id.buttonProceed);
    }

    /**
     * Start quiz music in the background
     */
    private void startQuizMusic() {
        quizzingMusic = MediaPlayer.create(this, R.raw.quiz_music);
        quizzingMusic.setLooping(true);
        quizzingMusic.start();
    }

    /**
     * Reset the tracker for last pressed and right answer button
     * Reset the UI to default (remove any color markings on the buttons)
     * Reset the proceed button to invisible mode
     * Get the following question and it's answers, and display it
     */
    private void reset() {
        setProceedButtonToInvisibleMode();
        lastPressedButton = null;

        // get the current question and it's answers
        Question currentQuestion = questions.get(questionNum);
        List<Answer> currentAnswers = accessAnswers.getAnswers(currentQuestion);
        Collections.shuffle(currentAnswers); // randomize the answers

        // get the position of the correct answer
        int rightAnswerButtonOrderNum = accessAnswers.getCorrectAnswerPosition(currentAnswers);
        rightAnswerButton = getOptionButtonByOrder(rightAnswerButtonOrderNum);

        // set question text
        TextView questionTV = findViewById(R.id.questionTV);
        questionTV.setText(currentQuestion.getQuestionText());

        // set answer button text
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(currentAnswers.get(i).getAnswerText());
        }

        // clear the color of the selected option answer
        for (Button optionButton : optionButtons) {
            optionButton.setBackgroundResource(R.drawable.question_option_button_default_color);
        }

        // set the option button clickable
        for (Button optionButton : optionButtons) {
            optionButton.setClickable(true);
        }

        questionNum++;
    }

    /**
     * Set the proceed button to invisible and unclickable
     */
    private void setProceedButtonToInvisibleMode() {
        proceedButton.setVisibility(View.INVISIBLE);
        proceedButton.setEnabled(false);
    }

    /**
     * Make the proceed button visible and usable
     * Make it as a submit button
     * Indicate which is the right answer and wrong if any
     */
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
            for (Button optionButton : optionButtons) {
                optionButton.setClickable(false);
            }
        });
    }

    /**
     * Make proceed button as "next" button
     * If the last question is reached, quiz this page when "next" button is clicked
     * If it is not the last question, reset and get more question
     */
    private void setProceedButtonToNextMode() {
        isSubmitted = false;
        proceedButton.setText(R.string.next);
        proceedButton.setBackgroundResource(R.drawable.question_proceed_button_next);
        proceedButton.setOnClickListener(proceedButton -> {
            if (totalQuestionCount == questionNum) {
                goToQuizEndPage(); //Go to quiz end page after done
            } else {
                // reset when still have more questions
                reset();
            }
        });
    }

    /**
     * Mark the right answer option button to green, and red for the user's wrong
     */
    private void indicateRightAndWrongAnswer() {
        lastPressedButton.setBackgroundResource(R.drawable.question_option_button_wrong);
        rightAnswerButton.setBackgroundResource(R.drawable.question_option_button_right);

        boolean isRight = (lastPressedButton == rightAnswerButton);

        if (isRight) {
            score++;
        }

        putRightOrWrongAudio(isRight);
    }

    /**
     * Get the option button according to the order
     * @param buttonOrder Button order number (0-3)
     * @return Button object with the order number
     */
    private Button getOptionButtonByOrder(int buttonOrder) {
        // Integer to keep track the option buttons
        // eg. 0 will be the first option button, and 3 is the forth
        int OPTION_BUTTON_1_ORDER_NUM = 0;
        int OPTION_BUTTON_2_ORDER_NUM = 1;
        int OPTION_BUTTON_3_ORDER_NUM = 2;
        int OPTION_BUTTON_4_ORDER_NUM = 3;
        Button button = null;
        if (buttonOrder == OPTION_BUTTON_1_ORDER_NUM) {
            button = optionButton1;
        } else if (buttonOrder == OPTION_BUTTON_2_ORDER_NUM) {
            button = optionButton2;
        } else if (buttonOrder == OPTION_BUTTON_3_ORDER_NUM) {
            button = optionButton3;
        } else if (buttonOrder == OPTION_BUTTON_4_ORDER_NUM) {
            button = optionButton4;
        }
        return button;
    }

    /**
     * Audio for getting right or wrong
     * @param isRight User got question right
     */
    private void putRightOrWrongAudio(boolean isRight) {
        MediaPlayer mediaPlayer;
        if (isRight) {
            mediaPlayer = MediaPlayer.create(this, R.raw.yay);
        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.wrong);
        }

        mediaPlayer.start();
    }

    /**
     * Responsible for the count down timer, and go back to main page when time's up
     */
    private void startTimer() {
        TextView timerTV = findViewById(R.id.timerTV);
        timeLeftInMillis = (long) quiz.getTimeLimit() * MILLIS_CONVERT;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { // Tick every second
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int seconds = (int) (timeLeftInMillis / 1000);
                timerTV.setText(seconds + " s");
            }

            public void onFinish() {
                timerTV.setText(R.string.time_s_up);
                startFlashingEffect(timerTV);
                setProceedButtonToInvisibleMode();
                startTimesUpMusic();

                // Finish activity after 2 seconds (2000ms)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goToQuizEndPage(); //Go to quiz end page after done
                    }
                }, 2000);
            }
        }.start();
    }

    /**
     * Play rooster audio when times up
     */
    private void startTimesUpMusic() {
        MediaPlayer timesUpMusic = MediaPlayer.create(this, R.raw.rooster);
        timesUpMusic.start();
    }

    /**
     * @param timerTV TextView to have flashing effect
     */
    private void startFlashingEffect(TextView timerTV) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(timerTV, "alpha", 1f, 0f);
        animator.setDuration(500); // 500ms per blink
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    /**
     * Go to End page with intent passing variables (score, quiz, currUser, timePassed)
     */
    private void goToQuizEndPage() {
        int timePassed = (int) timeLeftInMillis / MILLIS_CONVERT;
        Intent intent = new Intent(this, QuizEndActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("currQuiz", quiz);
        intent.putExtra("currUser", currUser);
        intent.putExtra("timeLeft", timePassed);
        startActivity(intent);
        finish();
    }
}