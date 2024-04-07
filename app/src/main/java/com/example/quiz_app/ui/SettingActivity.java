package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.quiz_app.QuizData;
import com.example.quiz_app.R;
import com.example.quiz_app.model.QuestionModel;
import com.example.quiz_app.utils.LanguageManager;
import com.example.quiz_app.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class SettingActivity extends AppCompatActivity {

    EditText editTextUserName;
    Switch switchLanguage;
    Button buttonSave;
    Button buttonLogout;
    Button btnSaveQuestion;
    Button btnSaveCategory;
    QuizData quizData;

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

        quizData = (QuizData) getIntent().getSerializableExtra("quizData");

        // chargement des préférences utilisateur pour afficher les valeurs actuelles
        SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        String username = preferences.getString("USERNAME", "");
        editTextUserName.setText(username);

        if(quizData != null)
            quizData.setUsername(username);

        String languageEnabled = preferences.getString("LANGUAGE", "");
        switchLanguage.setChecked(languageEnabled.equals("true"));
        switchLanguage.setText(languageEnabled.equals("true") ? R.string.french : R.string.english);
        switchLanguage.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                LanguageManager.setLanguage(SettingActivity.this, "fr");
                switchLanguage.setText(R.string.french);
            }

            else {
                LanguageManager.setLanguage(SettingActivity.this, "en");
                switchLanguage.setText(R.string.english);
            }
        }));
        // gestion du clic sur le bouton "logout"
        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
        btnSaveQuestion.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, SaveQuestionActivity.class);
            startActivity(intent);
        });

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
            if (value != null) {
                List<QuestionModel> questionModels = value.toObjects(QuestionModel.class);
                System.out.println("questionList: " + questionModels);
                //viewPagerAnswer.setAdapter(new QuestionT1Adapter(getApplicationContext(), questionModels, questionChoose));
            }
        });

        // gestion du clic sur le bouton "save"
        buttonSave.setOnClickListener(v -> {
            String newUserName = editTextUserName.getText().toString();
            // enregistrement des changements de langue
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("USERNAME", newUserName);
            editor.putString("LANGUAGE", String.valueOf(switchLanguage.isChecked()));
            editor.apply();

            // redémarrage de toutes les activités pour que les changement de langue prennent effet immédiatement
            Utils.restartAllActivities(SettingActivity.this);
            // affichage d'un message de succés
            Toast.makeText(SettingActivity.this, "Settings saved successfully", Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}
