package com.example.quiz_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.Manifest;

import com.example.quiz_app.R;
import com.example.quiz_app.utils.LanguageManager;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    Button buttonExit;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // méthode pour vérifier les autorisations de stockage
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mettre à jour la langue de l'application
        LanguageManager.updateLanguage(this);

        // initialisation des boutons
        buttonStart = findViewById(R.id.buttonStart);
        buttonExit = findViewById(R.id.buttonExit);

        // Configuration du bouton de démarrage pour ouvrir l'activité de connexion
        buttonStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Configuration du bouton de sortie pour fermer l'application
        buttonExit.setOnClickListener(v -> finish());

        verifyStoragePermissions(this);
    }
}