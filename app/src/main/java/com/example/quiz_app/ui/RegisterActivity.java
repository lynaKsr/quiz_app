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

import com.example.quiz_app.R;

import com.example.quiz_app.utils.LanguageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText editTextEmail, editTextPassWord;
    Button buttonRegister;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // l'utilisateur est déja connecté, donc redirection vers l'activité d'accueil
            Log.d(TAG, "User already logged in, redirecting to AcuueilActivity");
            Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Met à jour la langue de l'application
        LanguageManager.updateLanguage(this);

        // Initialisation de FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email_input_register);
        editTextPassWord = findViewById(R.id.password_input_register);
        buttonRegister = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar_register);
        textView = findViewById(R.id.loginNow);

        // Clic pour ouvrir LoginActivity
        textView.setOnClickListener(v -> {
            Log.d(TAG, "Cicked on 'Login Now'");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Action d'inscription
        buttonRegister.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassWord.getText());

            if(TextUtils.isEmpty(email)) {
                Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if(TextUtils.isEmpty(password)) {
                Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Création du compte avec Firebase Auth
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // L'inscription est réussie, redirection de l'utilisateur vers LoginActivity
                            Log.d(TAG, "Registration successful: " + email);
                            Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // l'inscription a échoué
                            Log.e(TAG, "Registration failed: " + Objects.requireNonNull(task.getException()).getLocalizedMessage());
                            Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }
}