package com.example.quiz_app.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quiz_app.R;
import com.example.quiz_app.common.adapter.QuestionTypeAdapter;
import com.example.quiz_app.model.QuestionModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class QuestionT1Activity extends AppCompatActivity {

    private ViewPager2 viewPagerAnswer;

    private TextView questionChoose;

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_t1);

        viewPagerAnswer = findViewById(R.id.viewPagerAnswer);
        questionChoose = findViewById(R.id.questionChoose1);
        backBtn = findViewById(R.id.back);

        Button buttonNext = findViewById(R.id.buttonNext1);
        buttonNext.setOnClickListener(v -> viewPagerAnswer.setCurrentItem(viewPagerAnswer.getCurrentItem() + 1, true));

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference questionRef = firebaseFirestore.collection("develop")
                .document("quizz")
                .collection("question");

        Query query = questionRef.whereEqualTo("cateCode", "CINEMA");
        query.addSnapshotListener((value, error) -> {
            if (value != null) {
                List<QuestionModel> questionModels = value.toObjects(QuestionModel.class);
                System.out.println("questionList: " + questionModels);
                viewPagerAnswer.setAdapter(new QuestionTypeAdapter(getApplicationContext(), questionModels, questionChoose));
            }
        });

        backBtn.setOnClickListener(v -> {
            finish();
        });
    }

}

