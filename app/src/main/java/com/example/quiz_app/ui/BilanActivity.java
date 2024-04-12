package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

public class BilanActivity extends AppCompatActivity {

    private TextView textViewCategoryName;

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    //private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private TextView textViewUserNameChosen;

    private  TextView totalNumberQuestion;

    private TextView totalNumberCorrectAnswer;
    private Button buttonExit;
    private Button buttonSaveReport;
    private RatingBar ratingBar;
    private TextView textViewRemark;

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
        buttonExit = findViewById(R.id.buttonBilanExit);
        buttonSaveReport = findViewById(R.id.buttonSaveBilan);
        ratingBar = findViewById(R.id.ratingBar);
        textViewRemark = findViewById(R.id.textViewRemark);

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

                            // Récupération des valeurs depuis les TextView
                            String totalCorrectAnswerText = totalNumberCorrectAnswer.getText().toString();
                            String totalQuestionText = totalNumberQuestion.getText().toString();

                            // Conversion des valeurs en entiers
                            int totalCorrectAnswer = Integer.parseInt(totalCorrectAnswerText.substring(0, totalCorrectAnswerText.indexOf("/")));
                            totalQuestion = Integer.parseInt(totalQuestionText);

                            double percentageCorrect = ((double) totalCorrectAnswer / totalQuestion) * 100;
                            if (percentageCorrect >= 50) {
                                textViewRemark.setText("Félicitations ! Vous avez bien joué !");
                            } else {
                                textViewRemark.setText("Vous pouvez faire mieux la prochaine fois.");
                            }

                            float rating;
                            if (percentageCorrect >= 75) {
                                rating = 5.0f;
                            } else if (percentageCorrect >= 50) {
                                rating = 4.0f;
                            } else if (percentageCorrect >= 25) {
                                rating = 3.0f;
                            } else {
                                rating = 2.0f;
                            }

                            ratingBar.setRating(rating);
                            ratingBar.setIsIndicator(true);
                        }
                        System.out.println("resultAnswerModel: " + userAnswerModel);
                    });
        }

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        buttonSaveReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

    }

    public void write_bilan_in_file() {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File fileout = new File(folder, "quiz_bilan.txt");
        try (FileOutputStream fos = new FileOutputStream(fileout)) {
            PrintStream ps = new PrintStream(fos);
            ps.println("Quiz Bilan:");
            ps.println("Nom d'utilisateur : " + textViewUserNameChosen.getText().toString());
            ps.println("Nombre de réponses correctes: " + totalNumberCorrectAnswer.getText().toString());
            ps.println("Nombre total de questions : " + totalNumberQuestion.getText().toString());
            ps.println("remarque : " + textViewRemark.getText().toString());


            ps.close();
            Log.d("BilanActivity", "Bilan du quiz enregistré avec succès dans le fichier " + fileout.getAbsolutePath());
        } catch (FileNotFoundException e) {
            Log.e("BilanActivity", "Erreur lors de l'enregistrement du bilan du quiz dans le fichier", e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("BilanActivity", "Erreur d'E/S lors de l'enregistrement du bilan du quiz dans le fichier", e);
        }
    }


    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("save report");
        builder.setMessage("do you want to save the report ? ");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                write_bilan_in_file();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}