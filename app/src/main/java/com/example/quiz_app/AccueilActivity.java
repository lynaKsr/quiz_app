package com.example.quiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else {
            String email = user.getEmail();
            textView.setText("");
            String newText = "you are connected with the email adress : " + email;
            textView.setText(newText);
        }

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pseudo = editTextPseudo.getText().toString();
                if(!pseudo.isEmpty()) {
                    Toast.makeText(AccueilActivity.this, "Successfully chosen holder " + pseudo, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AccueilActivity.this, SettingActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AccueilActivity.this, "Please enter your holder ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}