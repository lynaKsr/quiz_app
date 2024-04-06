package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.quiz_app.R;
import com.example.quiz_app.utils.Utils;

import java.util.List;




public class QuestionT3Activity extends AppCompatActivity {

    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_t3);

        buttonNext = findViewById(R.id.buttonNext3);

        buttonNext.setOnClickListener(v -> {
        });
    }
}