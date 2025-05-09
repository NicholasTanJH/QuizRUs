package comp3350.quizrus.presentation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import comp3350.quizrus.R;
import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.application.Main;

public class UserLoginActivity extends AppCompatActivity {
    TextInputEditText textInputEditTextUsername;
    TextInputEditText textInputEditTextPassword;
    Button buttonLogIn;
    Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDatabase();
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

    /**
     * Get username and password from user and tried to log in
     * get the response from logic layer
     * do animation corresponding to login is successful or not
     */
    private void logIn() {
        String logInUsername = textInputEditTextUsername.getText().toString();
        String logInPassword = textInputEditTextPassword.getText().toString();

        if (logInUsername.isEmpty() || logInPassword.isEmpty()) {
            setAlertMessage(getString(R.string.failed_to_log_in),
                    getString(R.string.login_information_cannot_be_empty));
        } else {
            AccessUsers accessUsers = new AccessUsers();
            User user = accessUsers.loginUser(logInUsername, logInPassword);
            boolean isLoginInfoCorrect = user != null;
            logInAnimation(isLoginInfoCorrect, user);
        }
    }

    /**
     * Give popup when login failed
     * Animate button when login succeeded
     * 
     * @param isLoginInfoCorrect login info is right
     * @param user               User object that was logged in
     */
    private void logInAnimation(boolean isLoginInfoCorrect, User user) {
        buttonLogIn.setText(R.string.logging_in);
        if (isLoginInfoCorrect) {
            new Handler().postDelayed(() -> {
                buttonLogIn.setText("✓");
            }, 1000);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(this, QuizSelectionActivity.class);
                intent.putExtra("loggedInUser", user);
                this.startActivity(intent);
                finish();
            }, 1500);
        } else {
            new Handler().postDelayed(() -> {
                setAlertMessage(getString(R.string.failed_to_log_in),
                        getString(R.string.incorrect_username_or_password_please_try_again));
                buttonLogIn.setText(R.string.log_in);
            }, 1000);
        }
    }

    /**
     * Create popup
     * 
     * @param alertTitle   title popup
     * @param alertMessage message popup
     */
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

    private void initializeDatabase() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            System.err.println("Unable to access application data: " + ioe.getMessage());
        }
    }

    private void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}