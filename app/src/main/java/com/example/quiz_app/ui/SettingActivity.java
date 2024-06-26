package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.quiz_app.R;
import com.example.quiz_app.utils.LanguageManager;
import com.example.quiz_app.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class SettingActivity extends AppCompatActivity {

    EditText editTextUserName;
    Switch switchLanguage;
    Button buttonSave;
    Button buttonLogout;
    Button btnSaveQuestion;
    Button btnSaveCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.quiz_app.R.layout.activity_setting);

        editTextUserName = findViewById(R.id.editTextUsername);
        switchLanguage = findViewById(R.id.switchLanguage);
        buttonSave = findViewById(R.id.buttonSave);
        buttonLogout = findViewById(R.id.buttonLogout);
        btnSaveQuestion = findViewById(R.id.btnSaveQuestion);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);

        // chargement des préférences utilisateur pour afficher les valeurs actuelles
        SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        String username = preferences.getString("USERNAME", "");
        editTextUserName.setText(username);

        String languageEnabled = preferences.getString("LANGUAGE", "");
        switchLanguage.setChecked(Boolean.parseBoolean(languageEnabled));
        switchLanguage.setText(Boolean.parseBoolean(languageEnabled) ? R.string.french : R.string.english);

        switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Changer la langue lors du changement de l'état du commutateur
            String language = isChecked ? "fr" : "en";
            LanguageManager.setLanguage(SettingActivity.this, language);
            switchLanguage.setText(isChecked ? R.string.french : R.string.english);
        });

        // gestion du clic sur le bouton "logout"
        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Gestion du clic sur le bouton "save question"
        btnSaveQuestion.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, SaveQuestionActivity.class);
            startActivity(intent);
        });

        // Gestion du clic sur le bouton "save category"
        btnSaveCategory.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, SaveCategoryActivity.class);
            startActivity(intent);
        });

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference questionRef = firebaseFirestore.collection("develop")
                .document("quizz")
                .collection("question");

        Query query = questionRef.whereEqualTo("cateCode", "CINEMA");
        query.addSnapshotListener((value, error) -> {
        });

        // gestion du clic sur le bouton "save"
        buttonSave.setOnClickListener(v -> {
            String newUserName = editTextUserName.getText().toString();
            // enregistrement des modifications de l'utilisateur
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("USERNAME", newUserName);
            editor.putString("LANGUAGE", String.valueOf(switchLanguage.isChecked()));
            editor.apply();

            // redémarrage de toutes les activités pour que les changement de langue prennent effet
            Utils.restartAllActivities(SettingActivity.this);
            Toast.makeText(SettingActivity.this, "Settings saved successfully", Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}
