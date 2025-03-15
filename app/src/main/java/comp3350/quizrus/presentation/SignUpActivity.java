package comp3350.quizrus.presentation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import comp3350.quizrus.R;
import comp3350.quizrus.business.AccessUsers;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText textInputEditTextUsername;
    TextInputEditText textInputEditTextPassword;
    TextInputEditText textInputEditTextConfirmPassword;
    TextInputEditText textInputEditTextFirstName;
    TextInputEditText textInputEditTextLastName;
    Button buttonCreateAccount;
    ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            textInputEditTextUsername = findViewById(R.id.textInputETUsername);
            textInputEditTextPassword = findViewById(R.id.textInputETPassword);
            textInputEditTextConfirmPassword = findViewById(R.id.textInputETConfirmPassword);
            textInputEditTextFirstName = findViewById(R.id.textInputETFirstName);
            textInputEditTextLastName = findViewById(R.id.textInputETLastName);
            buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
            buttonBack = findViewById(R.id.buttonBack);

            buttonCreateAccount.setOnClickListener(button -> checkSignInInfo());

            buttonBack.setOnClickListener(button -> finish());
            return insets;
        });
    }

    private void checkSignInInfo() {
        String newUsername = textInputEditTextUsername.getText().toString();
        String newPassword = textInputEditTextPassword.getText().toString();
        String newConfirmPassword = textInputEditTextConfirmPassword.getText().toString();
        String newFirstName = textInputEditTextFirstName.getText().toString();
        String newLastName = textInputEditTextLastName.getText().toString();

        AccessUsers accessUsers = new AccessUsers();

        String errorMessageUsername = accessUsers.authenticateUsername(newUsername);
        String errorMessagePassword = accessUsers.authenticatePassword(newPassword);

        boolean isValidUsername = errorMessageUsername.isEmpty();
        boolean isValidPassword = errorMessagePassword.isEmpty();
        boolean isValidConfirmPassword = newPassword.equals(newConfirmPassword);
        boolean isValidFirstName = accessUsers.authenticateName(newFirstName);
        boolean isValidLastName = accessUsers.authenticateName(newLastName);

        if (!isValidUsername) {
            setAlertMessage("Invalid Username", "Username must be:" + errorMessageUsername);
            return;
        } else if (!isValidPassword) {
            setAlertMessage("Invalid Password", "Password must have:" + errorMessagePassword);
            return;
        } else if (!isValidConfirmPassword) {
            setAlertMessage("Invalid Confirm Password", "Please ensure the confirm password matches your password.");
            return;
        } else if (!isValidFirstName) {
            setAlertMessage("Invalid First Name", "Please fill in your first name.");
            return;
        } else if (!isValidLastName) {
            setAlertMessage("Invalid Last Name", "Please fill in your last name.");
            return;
        } else {
            accessUsers.createUser(newUsername, newPassword, newFirstName, newLastName);
            successfulSignUpAnimation();
        }
    }

    // animation for changing the button text when registering
    private void successfulSignUpAnimation() {
        buttonCreateAccount.setText("Registering...");
        new Handler().postDelayed(() -> {
            buttonCreateAccount.setText("✓");
        }, 1000);
        new Handler().postDelayed(() -> {
            finish();
        }, 1500);
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