package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quiz_app.QuizData;
import com.example.quiz_app.R;
import com.example.quiz_app.common.enumerate.TypeQuestionEnum;
import com.example.quiz_app.model.QuestionModel;
import com.example.quiz_app.utils.CategoryQuestionMapper;
import com.example.quiz_app.utils.LanguageManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseCategoryActivity extends AppCompatActivity {

    ImageView musicImage;
    ImageView sportImage;
    ImageView knowledgeImage;
    ImageView cinemaImage;

    private Map<QuestionModel, String> answers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        LanguageManager.updateLanguage(this);

        cinemaImage = findViewById(R.id.cinemaImage);
        musicImage = findViewById(R.id.musicImage);
        knowledgeImage = findViewById(R.id.KnowledgeImage);
        sportImage = findViewById(R.id.sportImage);

        cinemaImage.setOnClickListener(v -> {
            // Lorsque l'utilisateur sélectionne la catégorie cinema
            Toast.makeText(ChooseCategoryActivity.this, "cinema category selected with success", Toast.LENGTH_SHORT).show();

            sendDataToNextActivity("cinema");
        });

        musicImage.setOnClickListener(v -> {
            // Lorsque l'utilisateur sélectionne la catégorie musique
            Toast.makeText(ChooseCategoryActivity.this, "music category selected with success", Toast.LENGTH_SHORT).show();

            sendDataToNextActivity("music");
        });

        knowledgeImage.setOnClickListener(v -> {
            // Lorsque l'utilisateur sélectionne la catégorie connaissance
            Toast.makeText(ChooseCategoryActivity.this, "knowledge category selected with success", Toast.LENGTH_SHORT).show();

            sendDataToNextActivity("knowledge");
        });

        sportImage.setOnClickListener(v -> {
            // Lorsque l'utilisateur sélectionne la catégorie sport
            Toast.makeText(ChooseCategoryActivity.this, "sport category selected with success", Toast.LENGTH_SHORT).show();

            sendDataToNextActivity("sport");
        });
    }

    private void sendDataToNextActivity(String category) {
        QuizData quizData = new QuizData();
        quizData.setCategory(category);

        // Ajout de logs pour vérifier les données
        Log.d("ChooseCategoryActivity", "Catégorie sélectionnée : " + category);
        Log.d("ChooseCategoryActivity", "quizData créé avec catégorie : " + quizData.getCategory());

        // Création d'un Intent pour la nouvelle activité et y ajouter l'objet quizData
        Intent intent = getIntentForCategory(CategoryQuestionMapper.getTypeQuestionsForCategory(category));
        intent.putExtra("quizData", quizData);
        startActivity(intent);
    }

    private Intent getIntentForCategory(List<TypeQuestionEnum> types) {
        Intent intent;
        if (types != null && types.contains(TypeQuestionEnum.MULTIPLE_CHOICE)) {
            intent = new Intent(this, QuestionT1Activity.class);
        } else if (types != null && types.contains(TypeQuestionEnum.YES_NO)) {
            intent = new Intent(this, QuestionT2Activity.class);
        } else {
            intent = new Intent(this, AccueilActivity.class);
        }
        return intent;
    }
}
