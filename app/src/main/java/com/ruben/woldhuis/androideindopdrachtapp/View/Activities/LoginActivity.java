package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;

public class LoginActivity extends Activity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressbar);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    task.getResult().getUser().getIdToken(true)
                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                           if (task.isSuccessful()) {
                                                               String idToken = task.getResult().getToken();
                                                               UserPreferencesService.getInstance(getApplication()).saveAuthenticationKey(idToken);
                                                               TcpManagerService.getInstance().submitMessage(new IdentificationMessage(idToken));
                                                           } else
                                                               Log.e("IDENTIFICATION_TAG", task.getException().getMessage());

                                                       }
                                                   }
                            );
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Error logging in");
                    builder.setMessage("Username/Password combination does not exist.");
                    builder.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Not logged in!");
        builder.setMessage("Please log in to use this app.");
        builder.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }
}
