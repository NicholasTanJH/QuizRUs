package comp3350.quizrus.presentation;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import comp3350.quizrus.R;

public class MCQuestionActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
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

        initButton();
    }

    /*
    initialize the button
    add onclick listener that track the last pressed button id
    and change color when pressed
     */
    private void initButton() {
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button1.setOnClickListener(b -> {
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.button_clicked_color);
            button2.setBackgroundResource(R.drawable.button_default_color);
            button3.setBackgroundResource(R.drawable.button_default_color);
            button4.setBackgroundResource(R.drawable.button_default_color);
        });

        button2.setOnClickListener(b -> {
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.button_clicked_color);
            button1.setBackgroundResource(R.drawable.button_default_color);
            button3.setBackgroundResource(R.drawable.button_default_color);
            button4.setBackgroundResource(R.drawable.button_default_color);
        });

        button3.setOnClickListener(b -> {
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.button_clicked_color);
            button1.setBackgroundResource(R.drawable.button_default_color);
            button2.setBackgroundResource(R.drawable.button_default_color);
            button4.setBackgroundResource(R.drawable.button_default_color);
        });

        button4.setOnClickListener(b -> {
            lastPressedButtonId = b.getId();
            b.setBackgroundResource(R.drawable.button_clicked_color);
            button1.setBackgroundResource(R.drawable.button_default_color);
            button2.setBackgroundResource(R.drawable.button_default_color);
            button3.setBackgroundResource(R.drawable.button_default_color);
        });
    }
}