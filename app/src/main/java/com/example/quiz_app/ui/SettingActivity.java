package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

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
    Switch swithDarkMode;

    RadioGroup radioGroupLanguage;
    Button buttonSave;
    Button buttonLogout;
    Button btnSaveQuestion;
    Button btnSaveCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.quiz_app.R.layout.activity_setting);

        editTextUserName = findViewById(R.id.editTextUsername);
        swithDarkMode = findViewById(R.id.switchDarkMode);
        radioGroupLanguage = findViewById(R.id.radioGroupLanguage);
        buttonSave = findViewById(R.id.buttonSave);
        buttonLogout = findViewById(R.id.buttonLogout);
        btnSaveQuestion = findViewById(R.id.btnSaveQuestion);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);

        // chargement des préférences utilisateur pour afficher les valeurs actuelles
        SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        String username = preferences.getString("USERNAME", "");
        editTextUserName.setText(username);

        boolean darkModeEnabled = preferences.getBoolean("DARK_MODE", false);
        swithDarkMode.setChecked(darkModeEnabled);

        //gestion des changement de langue dans le radio group (à faire)
        radioGroupLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            // verification de quel bouton radio est selectionné
            if(checkedId == R.id.radioButtonEnglish) {
                LanguageManager.setLanguage(SettingActivity.this, "en"); // changer en anglais
            }

            if(checkedId == R.id.radioButtonFrench) {
                LanguageManager.setLanguage(SettingActivity.this, "fr");
            }
        });

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
            editor.putBoolean("DARK_MODE", swithDarkMode.isChecked());
            // enregistrement de la langue selectionnée
            int selectedLanguageId = radioGroupLanguage.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedLanguageId);
            String selectedLanguage = selectedRadioButton.getText().toString();
            editor.putString("LANGUAGE", selectedLanguage);
            editor.apply();

            // redémarrage de toutes les activités pour que les changement de langue prennent effet immédiatement
            Utils.restartAllActivities(SettingActivity.this);
            // affichage d'un message de succés
            Toast.makeText(SettingActivity.this, "Settings saved successfully", Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}
