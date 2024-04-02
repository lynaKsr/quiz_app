package com.example.quiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class ChooseCategoryActivity extends AppCompatActivity {

    ImageView musicImage;
    ImageView sportImage;
    ImageView knowledgeImage;
    ImageView cinemaImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_category);

        musicImage = findViewById(R.id.musicImage);
        sportImage = findViewById(R.id.sportImage);
        knowledgeImage = findViewById(R.id.KnowledgeImage);
        cinemaImage = findViewById(R.id.cinemaImage);

        musicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCategoryActivity.this, "music category selected with success", Toast.LENGTH_SHORT).show();

                launchRandomQuestionActivity();
            }
        });

        sportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCategoryActivity.this, "sport category selected with success", Toast.LENGTH_SHORT).show();

                launchRandomQuestionActivity();
            }
        });

        knowledgeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCategoryActivity.this, "knowledge category selected with success", Toast.LENGTH_SHORT).show();

                launchRandomQuestionActivity();
            }
        });

        cinemaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCategoryActivity.this, "cinema category selected with success", Toast.LENGTH_SHORT).show();

                launchRandomQuestionActivity();
            }
        });
    }

    private void launchRandomQuestionActivity() {
        Random random = new Random();
        int randomNumber = random.nextInt(4) + 1; // génère un nombre aléatoire entre 1 et 4

        Intent intent;
        switch(randomNumber) {
            case 1 :
                intent = new Intent(ChooseCategoryActivity.this, QuestionT1Activity.class);
                break;
            case 2 :
                intent = new Intent(ChooseCategoryActivity.this, QuestionT2Activity.class);
                break;
            case 3 :
                intent = new Intent(ChooseCategoryActivity.this, QuestionT3Activity.class);
                break;
            case 4 :
                intent = new Intent(ChooseCategoryActivity.this, QuestionT4Activity.class);
                break;
            default :
                intent = null;
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Une erreur s'est produite lors du choix de la catégorie", Toast.LENGTH_SHORT).show();
        }
    }
}