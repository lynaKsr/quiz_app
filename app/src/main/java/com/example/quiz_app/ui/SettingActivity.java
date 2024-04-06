package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.quiz_app.R;
import com.example.quiz_app.model.QuestionModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Set;

public class SettingActivity extends AppCompatActivity {

    Button btnSaveQuestion;
    Button btnSaveCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.quiz_app.R.layout.activity_setting);

        btnSaveQuestion = findViewById(R.id.btnSaveQuestion);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);

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


    }
}
