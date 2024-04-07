package com.example.quiz_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_app.R;
import com.example.quiz_app.model.CategoryModel;
import com.example.quiz_app.utils.LanguageManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SaveCategoryActivity extends AppCompatActivity {
    Button btnSave;

    EditText code;

    EditText name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_category);

        LanguageManager.updateLanguage(this);

        btnSave = findViewById(R.id.btnSave);
        code = findViewById(R.id.edCode);
        name = findViewById(R.id.edName);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(v -> firebaseFirestore.collection("develop")
                .document("quizz")
                .collection("category")
                .get()
                .addOnCompleteListener(task -> {
                    List<CategoryModel> categories = task.getResult().toObjects(CategoryModel.class);
                    boolean filterCode = categories.stream().anyMatch(filter -> filter.getCode().equalsIgnoreCase(code.getText().toString()));

                    if (filterCode) {
                        Toast.makeText(this, "Category already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        firebaseFirestore.collection("develop")
                                .document("quizz")
                                .collection("category")
                                .add(new CategoryModel(this.code.getText().toString(), this.name.getText().toString()))
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                                    Log.d("SAVE_CATEGORY", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT).show();
                                    Log.w("SAVE_CATEGORY", "Error adding document", e);
                                });
                    }
                }));
    }
}