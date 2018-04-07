package com.codeblasters.qrcampus;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends WithMenuActivity {

    private EditText e_email, e_pass;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button b_sign_up, b_login, b_forget_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            this.startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        e_email = findViewById(R.id.email);
        e_pass = findViewById(R.id.password)    ;
        progressBar = findViewById(R.id.progressBar);
        b_login = findViewById(R.id.btn_login);
        b_forget_pass = findViewById(R.id.btn_reset_password);
        auth = FirebaseAuth.getInstance();

        b_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                finish();
            }
        });

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail_id = e_email.getText().toString().trim();
                final String pass = e_pass.getText().toString().trim();

                if (TextUtils.isEmpty(mail_id)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //to hide keyboard
                InputMethodManager inputMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                EditText editText = (EditText) findViewById(R.id.password);
                inputMgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                //end code of hide key board

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(mail_id, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            if (pass.length() < 6) {
                                e_pass.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}
