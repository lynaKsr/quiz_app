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

                Utils.launchRandomQuestionActivity(ChooseCategoryActivity.this);
            }
        });

        sportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCategoryActivity.this, "sport category selected with success", Toast.LENGTH_SHORT).show();

                Utils.launchRandomQuestionActivity(ChooseCategoryActivity.this);
            }
        });

        knowledgeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCategoryActivity.this, "knowledge category selected with success", Toast.LENGTH_SHORT).show();

                Utils.launchRandomQuestionActivity(ChooseCategoryActivity.this);
            }
        });

        cinemaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCategoryActivity.this, "cinema category selected with success", Toast.LENGTH_SHORT).show();

                Utils.launchRandomQuestionActivity(ChooseCategoryActivity.this);
            }
        });
    }


}