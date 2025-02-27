package comp3350.quizrus.presentation;

import android.content.DialogInterface;
import android.os.Bundle;
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
    TextInputEditText textInputEditTextEmail;
    TextInputEditText textInputEditTextName;
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
            textInputEditTextEmail = findViewById(R.id.textInputETEmail);
            textInputEditTextName = findViewById(R.id.textInputETName);
            buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
            buttonBack = findViewById(R.id.buttonBack);

            buttonCreateAccount.setOnClickListener(button ->
                checkSignInInfo()
            );

            buttonBack.setOnClickListener(button ->
                finish()
            );
            return insets;
        });
    }

    private void checkSignInInfo() {
        String newUsername = textInputEditTextUsername.getText().toString();
        String newPassword = textInputEditTextPassword.getText().toString();
        String newEmail = textInputEditTextEmail.getText().toString();
        String newName = textInputEditTextName.getText().toString();

        AccessUsers accessUsers = new AccessUsers();
        boolean isValidUsername = accessUsers.authenticateUsername(newUsername);

        String errorMessagePassword = accessUsers.authenticatePassword(newPassword);
        boolean isValidPassword = errorMessagePassword.isEmpty();
        boolean isValidEmail = accessUsers.authenticateEmail(newEmail);
        boolean isValidName = accessUsers.authenticateName(newName);

        if(!isValidUsername){
            setAlertMessage("Invalid Username","Please fill in your username and it must be 20 characters or shorter");
            return;
        }else if(!isValidPassword){
            setAlertMessage("Invalid Password", "Password must have:" + errorMessagePassword);
            return;
        }else if(!isValidEmail){
            setAlertMessage("Invalid Email", "Please fill in your email");
            return;
        }else if(!isValidName){
            setAlertMessage("Invalid Name", "Please fill in your name");
            return;
        }else{
//            accessUsers.createUser(newUsername, newPassword, newEmail, newName);
        }
    }

    private void setAlertMessage(String alertTitle, String alertMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        SpannableString spannableMessage = new SpannableString(alertMessage);
        spannableMessage.setSpan(new AbsoluteSizeSpan(30, true), 0, alertMessage.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

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