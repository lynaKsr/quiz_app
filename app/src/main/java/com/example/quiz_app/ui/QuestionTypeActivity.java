package com.example.quiz_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quiz_app.QuizData;
import com.example.quiz_app.R;
import com.example.quiz_app.common.adapter.QuestionTypeAdapter;
import com.example.quiz_app.model.QuestionModel;
import com.example.quiz_app.model.ResultAnswerModel;
import com.example.quiz_app.model.UserAnswerModel;
import com.example.quiz_app.utils.LanguageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuestionTypeActivity extends AppCompatActivity {

    private ViewPager2 viewPagerAnswer;

    private TextView questionChoose;

    private ImageView backBtn;

    private Map<QuestionModel, String> map = new HashMap<>();

    private int currentPosition = -1;

    private QuestionModel currentQuestion;

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private QuizData quizData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_type);

        Intent intentCategory = getIntent();
        if(intentCategory != null) {
            quizData = intentCategory.getParcelableExtra("quizData");
            if (quizData != null) {
                Log.d("QuestionTypeActivity", "QuizData récupérée avec succès : " + quizData);
            } else {
                Log.d("QuestionTypeActivity", "Aucune donnée QuizData trouvée dans l'intent");
            }
        }

        LanguageManager.updateLanguage(this);

        viewPagerAnswer = findViewById(R.id.viewPagerAnswer);
        viewPagerAnswer.setUserInputEnabled(false);

        questionChoose = findViewById(R.id.questionChoose1);
        backBtn = findViewById(R.id.back);

        Button buttonNext = findViewById(R.id.buttonNext1);
        buttonNext.setOnClickListener(v -> {
            boolean isLastPage = (viewPagerAnswer.getCurrentItem() + 1) >= Objects.requireNonNull(viewPagerAnswer.getAdapter()).getItemCount();

            if ((viewPagerAnswer.getCurrentItem() + 1) != Objects.requireNonNull(viewPagerAnswer.getAdapter()).getItemCount()) {
                if (currentPosition < 0) {
                    Toast.makeText(getApplicationContext(), "Please choose your answer", Toast.LENGTH_SHORT).show();
                } else {
                    String currentAnswer = map.getOrDefault(currentQuestion, "-1");

                    if (currentAnswer == null || currentAnswer.equals("-1")) {
                        Toast.makeText(getApplicationContext(), "Please choose your answer", Toast.LENGTH_SHORT).show();
                    } else {
                        currentPosition = -1;
                        currentQuestion = null;
                        viewPagerAnswer.setCurrentItem(viewPagerAnswer.getCurrentItem() + 1);
                    }
                }
            }

            // si c'est la derniere question
            if (isLastPage) {
                if (currentPosition < 0) {
                    Toast.makeText(getApplicationContext(), "Please choose your answer", Toast.LENGTH_SHORT).show();
                } else {
                    String currentAnswer = map.getOrDefault(currentQuestion, "-1");

                    if (currentAnswer == null || currentAnswer.equals("-1")) {
                        Toast.makeText(getApplicationContext(), "Please choose your answer", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(QuestionTypeActivity.this, BilanActivity.class);
                        intent.putExtra("quizData", quizData);
                        startActivity(intent);
                        finish();

                        List<ResultAnswerModel> resultAnswerModels = new ArrayList<>();
                        for (Map.Entry<QuestionModel, String> entry : map.entrySet()) {
                            ResultAnswerModel resultAnswerModel = new ResultAnswerModel();
                            resultAnswerModel.setQuestionModel(entry.getKey());
                            resultAnswerModel.setAnswerYN(entry.getValue());
                            resultAnswerModels.add(resultAnswerModel);
                        }

                        UserAnswerModel userAnswerModel = new UserAnswerModel();
                        userAnswerModel.setUserId(firebaseUser.getUid());
                        userAnswerModel.setResultAnswerModels(resultAnswerModels);

                        firebaseFirestore.collection("develop")
                                .document("quizz")
                                .collection("answer")
                                .add(userAnswerModel)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                                    Log.d("SAVE_ANSWER", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT).show();
                                    Log.w("SAVE_ANSWER", "Error adding document", e);
                                });
                    }
                }
            }
        });

        CollectionReference questionRef = firebaseFirestore.collection("develop")
                .document("quizz")
                .collection("question");

        Query query = questionRef.whereEqualTo("cateCode", "CINEMA");
        query.addSnapshotListener((value, error) -> {
            if (value != null) {
                List<QuestionModel> questionModels = value.toObjects(QuestionModel.class);
                QuestionTypeAdapter questionTypeAdapter = new QuestionTypeAdapter(getApplicationContext(), questionModels, questionChoose);

                viewPagerAnswer.setAdapter(questionTypeAdapter);
                questionTypeAdapter.setQuestionOnclick((position, questionModel, answer) -> {
                    map.put(questionModel, answer);
                    currentPosition = position;
                    currentQuestion = questionModel;

                });
            }
        });

        backBtn.setOnClickListener(v ->
                finish());
    }
}

