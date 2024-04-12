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


public class QuestionTypeActivity extends AppCompatActivity {
    private static final String TAG = "QuestionTypeActivity";
    private ViewPager2 viewPagerAnswer;
    private TextView questionChoose;
    private ImageView backBtn;
    private Map<QuestionModel, String> map = new HashMap<>();
    private int currentPosition = -1;
    private QuestionModel currentQuestion;
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private TextView count;
    private List<QuestionModel> questionModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_type);

        // Mise à jour de la langue de l'application
        LanguageManager.updateLanguage(this);

        viewPagerAnswer = findViewById(R.id.viewPagerAnswer);
        viewPagerAnswer.setUserInputEnabled(false);
        questionChoose = findViewById(R.id.questionChoose1);
        backBtn = findViewById(R.id.back);
        count = findViewById(R.id.count1);

        Intent intentBundle = getIntent();
        String cateCode = intentBundle.getStringExtra("cateCode");

        // Bouton next pour passer à la question suivante
        Button buttonNext = findViewById(R.id.buttonNext1);

        // Affichage du numéro de la question actuelle
        viewPagerAnswer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                count.setText((position + 1) + "/" + questionModels.size());
            }
        });

        buttonNext.setOnClickListener(v -> onNextButtonClick());

        // récupération des questions depuis Firestore
        CollectionReference questionRef = firebaseFirestore.collection("develop")
                .document("quizz")
                .collection("question");

        Query query = questionRef.whereEqualTo("cateCode", cateCode);
        query.addSnapshotListener((value, error) -> {
            if (value != null) {
                questionModels = value.toObjects(QuestionModel.class);
                QuestionTypeAdapter questionTypeAdapter = new QuestionTypeAdapter(getApplicationContext(), questionModels);

                viewPagerAnswer.setAdapter(questionTypeAdapter);
                viewPagerAnswer.setCurrentItem(viewPagerAnswer.getCurrentItem());
                questionChoose.setText(questionModels.get(0).getQuestion());

                questionTypeAdapter.setQuestionOnclick((position, questionModel, answer) -> {
                    map.put(questionModel, answer);
                    currentPosition = position;
                    currentQuestion = questionModel;
                });
            }
        });

        // bouton retour
        backBtn.setOnClickListener(v -> finish());
    }

    // Gestion du clic sur le bouton "Next"
    private void onNextButtonClick() {
        boolean isLastPage = (viewPagerAnswer.getCurrentItem() + 1) >= questionModels.size();

        // verification si toutes les questions ont été répondues
        if (currentPosition < 0) {
            Toast.makeText(getApplicationContext(), "Please choose your answer", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentAnswer = map.getOrDefault(currentQuestion, "-1");
        if (currentAnswer == null || currentAnswer.equals("-1")) {
            Toast.makeText(getApplicationContext(), "Please choose your answer", Toast.LENGTH_SHORT).show();
            return;
        }

        // Si c'est la dernière question, enregistrement des réponses
        if(isLastPage) {
            saveAnswersAndFinish();
        }
        else {
            // Passer à la question suivante
            questionChoose.setText(questionModels.get(viewPagerAnswer.getCurrentItem() + 1).getQuestion());
            currentPosition = -1;
            currentQuestion = null;
            viewPagerAnswer.setCurrentItem(viewPagerAnswer.getCurrentItem() + 1);
        }
    }

    // Enregistrement les réponses dans le Firestore et affichage du résultat
    private void saveAnswersAndFinish() {
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
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    Intent intent = new Intent(QuestionTypeActivity.this, BilanActivity.class);
                    intent.putExtra("answerId", documentReference.getId());
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding document", e);
                });
    }
}
