package com.example.quiz_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageManager {
    private static final String PREF_LANGUAGE_KEY = "langage";
    private static final String DEFAULT_LANGUAGE = "en";

    public static void setLanguage(Context context, String languageCode) {
        SharedPreferences preferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        preferences.edit().putString(PREF_LANGUAGE_KEY, languageCode).apply();
        updateLanguage(context, languageCode);
    }

    public static void updateLanguage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        String languageCode = preferences.getString(PREF_LANGUAGE_KEY, DEFAULT_LANGUAGE);
        updateLanguage(context, languageCode);
    }

    public static void updateLanguage(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}

