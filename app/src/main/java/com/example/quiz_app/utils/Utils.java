package com.example.quiz_app.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.example.quiz_app.ui.MainActivity;

import java.util.List;

public class Utils {

    // méthode qui redémarre toute les activités
    public static void restartAllActivities(Activity currentActivity) {
        // Démarrer à nouveau l'activité principale
        Intent intent = new Intent(currentActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
    }

}
