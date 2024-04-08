package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    QuizData quizData;

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

        quizData = new QuizData();

        cinemaImage.setOnClickListener(v -> {
            quizData.setCategory("cinema");
            // Lorsque l'utilisateur sélectionne la catégorie cinema
            Toast.makeText(ChooseCategoryActivity.this, "cinema category selected with success", Toast.LENGTH_SHORT).show();

            List<TypeQuestionEnum> types = CategoryQuestionMapper.getTypeQuestionsForCategory("cinema");

            Intent intent = getIntentForCategory(types);
            intent.putExtra("quizData", quizData);
            startActivity(intent);
        });

        musicImage.setOnClickListener(v -> {
            quizData.setCategory("music");
            // Lorsque l'utilisateur sélectionne la catégorie musique
            Toast.makeText(ChooseCategoryActivity.this, "music category selected with success", Toast.LENGTH_SHORT).show();

            List<TypeQuestionEnum> types = CategoryQuestionMapper.getTypeQuestionsForCategory("music");

            Intent intent = getIntentForCategory(types);
            intent.putExtra("quizData", quizData);
            startActivity(intent);
        });

        knowledgeImage.setOnClickListener(v -> {
            quizData.setCategory("knowledge");
            // Lorsque l'utilisateur sélectionne la catégorie connaissance
            Toast.makeText(ChooseCategoryActivity.this, "knowledge category selected with success", Toast.LENGTH_SHORT).show();

            List<TypeQuestionEnum> types = CategoryQuestionMapper.getTypeQuestionsForCategory("knowledge");

            Intent intent = getIntentForCategory(types);
            intent.putExtra("quizData", quizData);
            startActivity(intent);
        });

        sportImage.setOnClickListener(v -> {
            quizData.setCategory("sport");
            // Lorsque l'utilisateur sélectionne la catégorie sport
            Toast.makeText(ChooseCategoryActivity.this, "sport category selected with success", Toast.LENGTH_SHORT).show();

            List<TypeQuestionEnum> types = CategoryQuestionMapper.getTypeQuestionsForCategory("sport");

            Intent intent = getIntentForCategory(types);
            intent.putExtra("quizData", quizData);
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
