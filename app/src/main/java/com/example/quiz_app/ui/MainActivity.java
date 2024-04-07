package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.quiz_app.R;
import com.example.quiz_app.model.QuestionModel;
import com.example.quiz_app.utils.LanguageManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    Button buttonExit;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LanguageManager.updateLanguage(this);

        buttonStart = findViewById(R.id.buttonStart);
        buttonExit = findViewById(R.id.buttonExit);

        buttonStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        buttonExit.setOnClickListener(v -> finish());

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
    }
}