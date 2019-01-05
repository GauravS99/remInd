package com.gaurav.remindersapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


/*
 * Android login, signup and authentication activities adapted from Kam Low's github
 * <github.com/sourcey/materiallogindemo
 * ?fbclid=IwAR3vz3GG7AbyZzuz8Uswi2lfYSMTBDMLzQzy3ss4v0OsNvpcsKWy_C44fn0>
 * Retrieved: October 2018
 * Title: Android login and signup demo with material design
 * Author: Kam Low
 * Date of Last commit from author: 2015
 */

/**
 * A an activity for signing up a user
 */
public class SignupActivity extends AuthenticationActivity {
    /**
     * a TAG string for logging
     */
    private static final String TAG = "SignupActivity";
    /**
     * The text for entering email
     */
    EditText _emailText;
    /**
     * The text for entering password
     */
    EditText _passwordText;
    /**
     * The text for reentering password
     */
    EditText _reEnterPasswordText;
    /**
     * The text switching to log in
     */
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ScrollView sv = findViewById(R.id.signup_scroll);
        sv.getBackground().setAlpha(120);

        _emailText = findViewById(R.id.input_email) ;
        _passwordText = findViewById(R.id.input_password) ;
        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword) ;
        _loginLink = findViewById(R.id.link_login);
        _enterButton = findViewById(R.id.btn_signup);




        _enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    /**
     * Attempts to sign in the user after the sign in button has been pressed
     */
    public void signUp() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onFailure();
            return;
        }

        _enterButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        login.createAccount(email, password);
    }

    /**
     * Validates the data inputted in the fields
     *
     * @return whether the input is valid or not
     */
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            _passwordText.setError("between 6 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 ||
                reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

}
