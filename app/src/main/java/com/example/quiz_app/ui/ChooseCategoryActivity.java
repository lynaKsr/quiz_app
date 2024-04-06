package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quiz_app.R;
import com.example.quiz_app.common.enumerate.TypeQuestionEnum;
import com.example.quiz_app.utils.CategoryQuestionMapper;

import java.util.List;

public class ChooseCategoryActivity extends AppCompatActivity {

    ImageView musicImage;
    ImageView sportImage;
    ImageView knowledgeImage;
    ImageView cinemaImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        cinemaImage = findViewById(R.id.cinemaImage);
        musicImage = findViewById(R.id.musicImage);
        knowledgeImage = findViewById(R.id.KnowledgeImage);
        sportImage = findViewById(R.id.sportImage);

        cinemaImage.setOnClickListener(v -> {
            // Lorsque l'utilisateur sélectionne la catégorie cinema
            Toast.makeText(ChooseCategoryActivity.this, "cinema category selected with success", Toast.LENGTH_SHORT).show();

            List<TypeQuestionEnum> types = CategoryQuestionMapper.getTypeQuestionsForCategory("cinema");

            Intent intent = getIntentForCategory(types);
            startActivity(intent);
        });

        musicImage.setOnClickListener(v -> {
            // Lorsque l'utilisateur sélectionne la catégorie musique
            Toast.makeText(ChooseCategoryActivity.this, "music category selected with success", Toast.LENGTH_SHORT).show();

            List<TypeQuestionEnum> types = CategoryQuestionMapper.getTypeQuestionsForCategory("music");

            Intent intent = getIntentForCategory(types);
            startActivity(intent);
        });

        knowledgeImage.setOnClickListener(v -> {
            // Lorsque l'utilisateur sélectionne la catégorie connaissance
            Toast.makeText(ChooseCategoryActivity.this, "knowledge category selected with success", Toast.LENGTH_SHORT).show();

            List<TypeQuestionEnum> types = CategoryQuestionMapper.getTypeQuestionsForCategory("knowledge");

            Intent intent = getIntentForCategory(types);
            startActivity(intent);
        });

        sportImage.setOnClickListener(v -> {
            // Lorsque l'utilisateur sélectionne la catégorie sport
            Toast.makeText(ChooseCategoryActivity.this, "sport category selected with success", Toast.LENGTH_SHORT).show();

            List<TypeQuestionEnum> types = CategoryQuestionMapper.getTypeQuestionsForCategory("sport");

            Intent intent = getIntentForCategory(types);
            startActivity(intent);
        });
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
