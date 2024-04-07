package com.example.quiz_app.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_app.R;
import com.example.quiz_app.model.QuestionModel;
import com.example.quiz_app.utils.LanguageManager;

import java.util.List;
public class QuestionT2Activity extends AppCompatActivity {

    Button buttonNext2;
    CheckBox checkBox, checkBox2, checkBox3, checkBox4;
    TextView questionChoose2, count2;

    ImageView back;

    List<QuestionModel> questionsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_t2);

        LanguageManager.updateLanguage(this);

        buttonNext2 = findViewById(R.id.buttonNext2);
        back = findViewById(R.id.back);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        questionChoose2 = findViewById(R.id.questionChoose2);
        count2 = findViewById(R.id.count2);

    }
}