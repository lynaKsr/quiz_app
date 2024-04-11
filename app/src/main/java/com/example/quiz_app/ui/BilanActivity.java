package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;
import android.widget.TextView;

//import com.example.quiz_app.QuizData;
import com.example.quiz_app.R;
import com.example.quiz_app.model.CategoryModel;
import com.example.quiz_app.model.ResultAnswerModel;
import com.example.quiz_app.model.UserAnswerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.stream.Collectors;

public class BilanActivity extends AppCompatActivity {

    private TextView textViewCategoryName;

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private TextView textViewUserNameChosen;

    private  TextView totalNumberQuestion;

    private TextView totalNumberCorrectAnswer;

    //private QuizData quizData;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilan);

        textViewCategoryName = findViewById(R.id.textViewCategoryName);
        textViewUserNameChosen = findViewById(R.id.textViewUserNameChosen);
        totalNumberQuestion = findViewById(R.id.textViewTotalQuestions);
        totalNumberCorrectAnswer = findViewById(R.id.textViewNbCorrect);

        Intent intent = getIntent();
        String answerId = intent.getStringExtra("answerId");
        String cateCode = intent.getStringExtra("cateCode");

        SharedPreferences sharedPref = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        if (null != sharedPref) {
            textViewUserNameChosen.setText(sharedPref.getString("USERNAME", ""));
        }

        System.out.println("cateCode");
        if (null != cateCode) {
            CollectionReference questionRef = firebaseFirestore.collection("develop")
                    .document("quizz")
                    .collection("category");

            Query query = questionRef.whereEqualTo("code", cateCode);
            query.addSnapshotListener((value, error) -> {
                if (value != null) {
                    System.out.println("cateCode: " + value);
                    CategoryModel categoryModel = value.toObjects(CategoryModel.class).get(0);
                    textViewCategoryName.setText(categoryModel.getName());
                }
            });
        }

        if (null != answerId) {
            firebaseFirestore.collection("develop")
                    .document("quizz")
                    .collection("answer")
                    .document(answerId)
                    .get()
                    .addOnCompleteListener(task -> {
                        UserAnswerModel userAnswerModel = task.getResult().toObject(UserAnswerModel.class);
                        if (userAnswerModel != null) {
                            int totalQuestion = userAnswerModel.getResultAnswerModels().size();
                            List<ResultAnswerModel> totalAnswerCorrect = userAnswerModel.getResultAnswerModels().stream().filter(v -> v.getAnswerYN().equals("Y")).collect(Collectors.toList());

                            totalNumberQuestion.setText(String.valueOf(totalQuestion));
                            totalNumberCorrectAnswer.setText(totalAnswerCorrect.size() + "/" + totalQuestion);

                        }
                        System.out.println("resultAnswerModel: " + userAnswerModel);
                    });
        }

        /*
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
        }*/
    }
}