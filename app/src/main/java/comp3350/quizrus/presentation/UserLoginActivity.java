package comp3350.quizrus.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import comp3350.quizrus.R;
import comp3350.quizrus.business.AccessUsers;

public class UserLoginActivity extends AppCompatActivity {
    TextInputEditText textInputEditTextUsername;
    TextInputEditText textInputEditTextPassword;
    Button buttonLogIn;
    Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            textInputEditTextUsername = findViewById(R.id.textInputETUsername);
            textInputEditTextPassword = findViewById(R.id.textInputETPassword);
            buttonLogIn = findViewById(R.id.buttonLogin);
            buttonSignUp = findViewById(R.id.buttonSignUp);

            buttonLogIn.setOnClickListener(button -> logIn());

            buttonSignUp.setOnClickListener(button -> {
                Intent intent = new Intent(this, SignUpActivity.class);
                this.startActivity(intent);
            });

            return insets;
        });
    }

    private void logIn() {
        String logInUsername = textInputEditTextUsername.getText().toString();
        String logInPassword = textInputEditTextPassword.getText().toString();

        AccessUsers accessUsers = new AccessUsers();
        boolean isLoginInfoCorrect = true; //TODO

        if(isLoginInfoCorrect){
            Intent intent = new Intent(this, QuizSelectionActivity.class);
            this.startActivity(intent);
            finish();
        }
    }
}