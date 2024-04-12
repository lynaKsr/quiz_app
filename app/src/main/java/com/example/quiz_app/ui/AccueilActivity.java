package com.example.quiz_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_app.R;
import com.example.quiz_app.utils.LanguageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccueilActivity extends AppCompatActivity {
    private static final String TAG = "AccueilActivity";
    FirebaseAuth auth;
    Button buttonSetting;
    Button buttonConfirm;
    TextView userDetailsTextView;
    EditText pseudoEditText;
    FirebaseUser user;
    ImageView startQuizImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // mettre à jour la langue de l'application
        LanguageManager.updateLanguage(this);

        auth = FirebaseAuth.getInstance();
        buttonSetting = findViewById(R.id.buttonSetting);
        buttonConfirm = findViewById(R.id.buttonConfirmPseudo);
        userDetailsTextView = findViewById(R.id.user_details);
        pseudoEditText = findViewById(R.id.editTextPseudo);
        startQuizImageView = findViewById(R.id.imageViewStart);
        user = auth.getCurrentUser();

        if (user != null) {
            // mettre à jour le pseudo à chaque création de l'activité
            updateUserDetails();
            String pseudoKey = user.getUid() + "USERNAME";
            SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
            String pseudo = preferences.getString(pseudoKey + "USERNAME", "");

            if (!pseudo.isEmpty()) {
                // pré-remplir le pseudo si déja enregistré
                pseudoEditText.setText(pseudo);
                buttonConfirm.setEnabled(false);
            }
            else {
                pseudoEditText.setEnabled(true);
                buttonConfirm.setEnabled(true);
            }
        }
        else {
            // redirection vers l'écran de connexion s'il n'est pas connecté
            Intent intent = new Intent(AccueilActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // gestion du clic sur le bouton "settings"
        buttonSetting.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        // gestion du clic sur le bouton "confirmer" pour le choix du nom d'utilisateur
        buttonConfirm.setOnClickListener(v -> {
            String newPseudo = pseudoEditText.getText().toString();
            if (!newPseudo.isEmpty()) {
                Toast.makeText(AccueilActivity.this, "Successfully chosen username " + newPseudo, Toast.LENGTH_SHORT).show();
                // enregistrement du nom d'utilisateur dnas les préférences de l'application
                SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("USERNAME", newPseudo).apply();
                updateUserDetails();
                pseudoEditText.setEnabled(false); // Désactiver le champ de texte après avoir choisi le pseudo
                buttonConfirm.setEnabled(false); // Désactiver le bouton de confirmation
            } else {
                Toast.makeText(AccueilActivity.this, "Please enter your username ! ", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion du clic sur l'image pour démarrer le quiz
        startQuizImageView.setOnClickListener(v -> {
            String pseudo = pseudoEditText.getText().toString();
            if(!pseudo.isEmpty()) {
                // redirection de l'utilisateur vers l'activité de choix de catégorie afin de commencer le quiz
                Intent intent = new Intent(AccueilActivity.this, ChooseCategoryActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(AccueilActivity.this, "Please enter a username first !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // mettre à jour l'affichage des détails de l'utilisateur
    private void updateUserDetails() {
        SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        String pseudo = preferences.getString("USERNAME", "");
        userDetailsTextView.setText(getString(R.string.connected_with_email, user.getEmail(), pseudo));
    }

}