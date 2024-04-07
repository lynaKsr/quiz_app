package com.example.quiz_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccueilActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button buttonLogout;
    Button buttonConfirm;
    TextView textView;
    EditText editTextPseudo;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        auth = FirebaseAuth.getInstance();
        buttonLogout = findViewById(R.id.logout);
        buttonConfirm= findViewById(R.id.buttonConfirmPseudo);
        textView = findViewById(R.id.user_details);
        editTextPseudo = findViewById(R.id.editTextPseudo);
        user = auth.getCurrentUser();

        // on verifie si l'utilisateur est connecté
        if(user == null) {
            // on le redirige vers l'écran deconnexion s'il n'est pas connecté
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            // sinon, on affiche les détails de l'utilisateur connecté
            String email = user.getEmail();
            textView.setText("");
            String newText = "You are connected with the email adress : " + email;
            textView.setText(newText);
        }

        buttonLogout.setOnClickListener(v ->{
            //FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
            //finish();
        });

        // gestion du click sur le bouton "confirmer" pour le choix du nom d'utilisateur
        buttonConfirm.setOnClickListener(v -> {
            String pseudo = editTextPseudo.getText().toString();
            if(!pseudo.isEmpty()) {
                Toast.makeText(AccueilActivity.this, "Successfully chosen holder " + pseudo, Toast.LENGTH_SHORT).show();
                // enregistrement du nom d'utilisateur dnas les préférences de l'application
                SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
                preferences.edit().putString("USERNAME", pseudo).apply();

                // redirection de l'utilisateur vers l'activité de choix de catégorie afin de commencer le quiz
                Intent intent = new Intent(AccueilActivity.this, ChooseCategoryActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AccueilActivity.this, "Please enter your holder ! ", Toast.LENGTH_SHORT).show();
            }
        });
    }


}