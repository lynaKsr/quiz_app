package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quiz_app.R;
import com.example.quiz_app.common.enumerate.TypeQuestionEnum;
import com.example.quiz_app.utils.CategoryQuestionMapper;
import com.example.quiz_app.utils.LanguageManager;

import java.util.List;

public class ChooseCategoryActivity extends AppCompatActivity {
    private static final String TAG = "ChooseCategoryActivity";
    ImageView musicImage;
    ImageView sportImage;
    ImageView knowledgeImage;
    ImageView cinemaImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        // Mise à jour de la langue de l'application
        LanguageManager.updateLanguage(this);

        cinemaImage = findViewById(R.id.cinemaImage);
        musicImage = findViewById(R.id.musicImage);
        knowledgeImage = findViewById(R.id.KnowledgeImage);
        sportImage = findViewById(R.id.sportImage);

        // Gestion des clics sur les images de catégories
        cinemaImage.setOnClickListener(v -> {
            Toast.makeText(ChooseCategoryActivity.this, "cinema category selected with success", Toast.LENGTH_SHORT).show();
            sendDataToNextActivity("CINEMA");
        });

        musicImage.setOnClickListener(v -> {
            Toast.makeText(ChooseCategoryActivity.this, "music category selected with success", Toast.LENGTH_SHORT).show();
            sendDataToNextActivity("MUSIC");
        });

        knowledgeImage.setOnClickListener(v -> {
            Toast.makeText(ChooseCategoryActivity.this, "knowledge category selected with success", Toast.LENGTH_SHORT).show();
            sendDataToNextActivity("KNOWLEDGE");
        });

        sportImage.setOnClickListener(v -> {
            Toast.makeText(ChooseCategoryActivity.this, "sport category selected with success", Toast.LENGTH_SHORT).show();
            sendDataToNextActivity("SPORT");
        });
    }

    private void sendDataToNextActivity(String category) {
        // Création d'un Intent pour la nouvelle activité et y ajouter l'objet quizData
        Intent intent = getIntentForCategory(CategoryQuestionMapper.getTypeQuestionsForCategory(category));
        intent.putExtra("cateCode", category);
        startActivity(intent);
    }

    // Création d'un Intent en fonction de la catégorie sélectionnée
    private Intent getIntentForCategory(List<TypeQuestionEnum> types) {
        Intent intent;
        if (types != null) {
            intent = new Intent(this, QuestionTypeActivity.class);

        } else {
            intent = new Intent(this, AccueilActivity.class);
        }
        return intent;
    }
}
