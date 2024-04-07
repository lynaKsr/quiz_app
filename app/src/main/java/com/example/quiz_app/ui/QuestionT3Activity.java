package com.example.quiz_app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_app.R;
import com.example.quiz_app.utils.LanguageManager;


public class QuestionT3Activity extends AppCompatActivity {

    Button buttonNext;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_t3);

        LanguageManager.updateLanguage(this);

        buttonNext = findViewById(R.id.buttonNext3);

        back = findViewById(R.id.back);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.launchRandomQuestionActivity(QuestionT3Activity.this);
            }

        });

        back.setOnClickListener(v -> {
            finish();
        });

    }
}