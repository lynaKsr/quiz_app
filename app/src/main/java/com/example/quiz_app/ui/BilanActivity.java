package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.quiz_app.QuizData;
import com.example.quiz_app.R;

public class BilanActivity extends AppCompatActivity {

    private TextView textViewCategoryName;
    private QuizData quizData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilan);

        textViewCategoryName = findViewById(R.id.textViewCategoryName);

        Intent intent = getIntent();
        if (intent != null) {
            quizData = intent.getParcelableExtra("quizData");
            if (quizData != null) {
                Log.d("BilanActivity", "QuizData récupérée avec succès : " + quizData);
                String category = quizData.getCategory();
                Log.d("BilanActivity", "Catégorie récupérée depuis quizData : " + category);
                textViewCategoryName.setText(category);
            }
            else {
                Log.d("BilanActivity", "Aucune donnée QuizData trouvée dans l'intent");
            }
        }
    }
}