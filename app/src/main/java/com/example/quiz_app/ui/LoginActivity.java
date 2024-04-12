package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz_app.utils.LanguageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.quiz_app.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    EditText editTextEmail, editTextPassWord;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textViewSignup;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Log.d(TAG, "User is already logged in: " + currentUser.getEmail());
            navigateToHome();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Mise à jour de la langue de l'application
        LanguageManager.updateLanguage(this);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email_input);
        editTextPassWord = findViewById(R.id.password_input);
        buttonLogin = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        textViewSignup = findViewById(R.id.signupText);

        // redirection vers l'activité d'inscription
        textViewSignup.setOnClickListener(v->{
            navigateToRegister();
        });

        // Action de connexion
        buttonLogin.setOnClickListener(v -> {
            // Affichage de la barre de progression
            progressBar.setVisibility(View.VISIBLE);

            // Récupération des données du formulaire
            String email, password;
            email = editTextEmail.getText().toString();
            password = editTextPassWord.getText().toString();


            if(TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if(TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Connexion avec Firebase Auth
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    Log.d(TAG, "Login successful: " + email);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    navigateToHome();
                }
                else
                {
                    Log.e(TAG, "Authentication failed: " + task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    // Redirection vers l'activité principale
    private void navigateToHome() {
        Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
        startActivity(intent);
        finish();
    }

    // Redirection vers l'activité d'inscription
    private void navigateToRegister() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
