package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.quiz_app.R;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    Button buttonExit;

    Button btnSaveQuestion;
    Button btnSaveCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonExit = findViewById(R.id.buttonExit);
        btnSaveQuestion = findViewById(R.id.btnSaveQuestion);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);

        buttonStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        buttonExit.setOnClickListener(v -> finish());

        btnSaveQuestion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SaveQuestionActivity.class);
            startActivity(intent);
        });

        btnSaveCategory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SaveCategoryActivity.class);
            startActivity(intent);
        });
    }
}