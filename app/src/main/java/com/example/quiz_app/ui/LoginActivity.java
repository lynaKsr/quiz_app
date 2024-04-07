package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz_app.QuizData;
import com.example.quiz_app.utils.LanguageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.quiz_app.R;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassWord;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textViewSignup;
    QuizData quizData;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
            // Transmission de l'objet QuizData à l'activité AccueilActivity
            intent.putExtra("quizData", quizData);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LanguageManager.updateLanguage(this);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email_input);
        editTextPassWord = findViewById(R.id.password_input);
        buttonLogin = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        textViewSignup = findViewById(R.id.signupText);

        textViewSignup.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            // Transmission de l'objet QuizData à l'activité RegisterActivity
            intent.putExtra("quizData", quizData);
            startActivity(intent);
            finish();
        });

        // récupération de l'objet QuizData transmis depuis MainActivity
        quizData = (QuizData) getIntent().getSerializableExtra("quizData");

        buttonLogin.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, password;
            email = editTextEmail.getText().toString();
            password = editTextPassWord.getText().toString();

            if(TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    // mise à jour de l'email dans l'objet QuizData
                    quizData.setEmail(email);
                    Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
                    // Transmission de l'objet QuizData à l'activité AccueilActivity
                    intent.putExtra("quizData", quizData);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
