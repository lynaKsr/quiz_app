package com.example.quiz_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_app.QuizData;
import com.example.quiz_app.R;
import com.example.quiz_app.utils.LanguageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccueilActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button buttonLogout;
    Button buttonConfirm;
    TextView textView;
    EditText editTextPseudo;
    FirebaseUser user;
    ImageView imageViewStart;
    QuizData quizData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        LanguageManager.updateLanguage(this);

        auth = FirebaseAuth.getInstance();
        buttonLogout = findViewById(R.id.logout);
        buttonConfirm = findViewById(R.id.buttonConfirmPseudo);
        textView = findViewById(R.id.user_details);
        editTextPseudo = findViewById(R.id.editTextPseudo);
        imageViewStart = findViewById(R.id.imageViewStart);

        user = auth.getCurrentUser();

        quizData = (QuizData) getIntent().getSerializableExtra("quizData");

        // mettre à jour le pseudo à chaque fois que l'activité est créée
        updateDisplayPseudo();

        // on verifie si l'utilisateur est connecté
        if (user == null) {
            // on le redirige vers l'écran deconnexion s'il n'est pas connecté
            Intent intent = new Intent(AccueilActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, SettingActivity.class);
            intent.putExtra("quizData", quizData);
            startActivity(intent);
        });

        SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        String pseudo = preferences.getString("USERNAME", "");

        if (!pseudo.isEmpty()) {

            editTextPseudo.setText(pseudo);
            editTextPseudo.setEnabled(false);
            editTextPseudo.setFocusable(false);
            editTextPseudo.setClickable(false);
            editTextPseudo.setCursorVisible(false);
            buttonConfirm.setEnabled(false);
            editTextPseudo.setOnClickListener(v -> Toast.makeText(AccueilActivity.this, "To modify the username, please go to settings", Toast.LENGTH_SHORT).show());
        } else {

            // gestion du click sur le bouton "confirmer" pour le choix du nom d'utilisateur
            buttonConfirm.setOnClickListener(v -> {
                String newPseudo = editTextPseudo.getText().toString();
                if (!newPseudo.isEmpty()) {
                    Toast.makeText(AccueilActivity.this, "Successfully chosen holder " + pseudo, Toast.LENGTH_SHORT).show();
                    // enregistrement du nom d'utilisateur dnas les préférences de l'application
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("USERNAME", newPseudo).apply();
                    // Mettre à jour QuizData
                    quizData.setUsername(newPseudo);

                    updateDisplayPseudo();
                    editTextPseudo.setEnabled(false); // Désactiver le champ de texte après avoir choisi le pseudo
                    editTextPseudo.setFocusable(false);
                    editTextPseudo.setClickable(false);
                    editTextPseudo.setCursorVisible(false);
                    buttonConfirm.setEnabled(false); // Désactiver le bouton de confirmation
                } else {
                    Toast.makeText(AccueilActivity.this, "Please enter your holder ! ", Toast.LENGTH_SHORT).show();
                }
            });
        }
        imageViewStart.setOnClickListener(v -> {
            // redirection de l'utilisateur vers l'activité de choix de catégorie afin de commencer le quiz
            Intent intent = new Intent(AccueilActivity.this, ChooseCategoryActivity.class);
            intent.putExtra("quizData", quizData);
            startActivity(intent);
        });
    }

    private void updateDisplayPseudo() {
        SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        String pseudo = preferences.getString("USERNAME", "");
        textView.setText(getString(R.string.connected_with_email, user.getEmail(), pseudo));
    }


}