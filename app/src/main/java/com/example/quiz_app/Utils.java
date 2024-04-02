package com.example.quiz_app;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Random;

public class Utils {
    public static void launchRandomQuestionActivity(Context context) {
        Random random = new Random();
        int randomNumber = random.nextInt(4) + 1; // génère un nombre aléatoire entre 1 et 4

        Intent intent;
        switch(randomNumber) {
            case 1 :
                intent = new Intent(context, QuestionT1Activity.class);
                break;
            case 2 :
                intent = new Intent(context, QuestionT2Activity.class);
                break;
            case 3 :
                intent = new Intent(context, QuestionT3Activity.class);
                break;
            case 4 :
                intent = new Intent(context, QuestionT4Activity.class);
                break;
            default :
                intent = null;
                break;
        }

        if(intent != null) {
            context.startActivity(intent);
        }
        else {
            Toast.makeText(context, "Une erreur s'est produite lors du choix de la catégorie", Toast.LENGTH_SHORT).show();
        }
    }
}
