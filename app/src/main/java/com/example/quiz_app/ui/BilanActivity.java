package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.quiz_app.R;
import com.example.quiz_app.model.CategoryModel;
import com.example.quiz_app.model.ResultAnswerModel;
import com.example.quiz_app.model.UserAnswerModel;
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
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private TextView textViewCategoryName;
    private TextView textViewUserNameChosen;
    private  TextView totalNumberQuestion;
    private TextView totalNumberCorrectAnswer;
    private Button buttonExit;
    private Button buttonSaveReport;
    private RatingBar ratingBar;
    private TextView textViewRemark;

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

        // Récupération des données de l'intent
        Intent intent = getIntent();
        String answerId = intent.getStringExtra("answerId");
        String cateCode = intent.getStringExtra("cateCode");

        // Récupération du nom d'utilisateur depuis les préférences partagées
        SharedPreferences sharedPref = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        if (sharedPref != null) {
            textViewUserNameChosen.setText(sharedPref.getString("USERNAME", ""));
        }

        // Récupération du nom de la catégorie et mise à jour du TextView correspondant
        if (cateCode != null) {
            CollectionReference questionRef = firebaseFirestore.collection("develop")
                    .document("quizz")
                    .collection("category");

            Query query = questionRef.whereEqualTo("code", cateCode);
            query.addSnapshotListener((value, error) -> {
                if (value != null) {
                    CategoryModel categoryModel = value.toObjects(CategoryModel.class).get(0);
                    textViewCategoryName.setText(categoryModel.getName());
                }
            });
        }

        if (answerId != null) {
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

                            // Calcul du pourcentage de réponses correctes
                            int totalCorrectAnswer = Integer.parseInt(totalNumberCorrectAnswer.getText().toString().split("/")[0]);
                            double percentageCorrect = ((double) totalCorrectAnswer / totalQuestion) * 100;

                            // Affichage d'une remarque en fonction du pourcentage
                            if (percentageCorrect >= 50) {
                                textViewRemark.setText("Félicitations ! Vous avez bien joué !");
                            } else {
                                textViewRemark.setText("Vous pouvez faire mieux la prochaine fois.");
                            }

                            // Attribution de la note en fonction du pourcentage
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
                    });
        }
        buttonExit.setOnClickListener(v -> finishAffinity());
        buttonSaveReport.setOnClickListener(v -> showConfirmationDialog());

    }

    // Méthode pour écrire le bilan dans un fichier texte
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
        builder.setPositiveButton("yes", (dialog, which) -> write_bilan_in_file());

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}