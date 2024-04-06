package com.example.quiz_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.quiz_app.R;
import com.example.quiz_app.common.adapter.CategorySpinnerAdapter;
import com.example.quiz_app.common.enumerate.TypeQuestionEnum;
import com.example.quiz_app.model.AnswerModel;
import com.example.quiz_app.model.CategoryModel;
import com.example.quiz_app.model.QuestionModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class SaveQuestionActivity extends AppCompatActivity {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private Spinner spinner;

    private List<CategoryModel> categories;

    private CategoryModel categoryModel;

    private TypeQuestionEnum typeQuestionEnum;

    private ConstraintLayout csMultipleChoice;

    private AppCompatEditText edQuestion;
    private AppCompatEditText edAnswer1;
    private AppCompatEditText edAnswer2;
    private AppCompatEditText edAnswer3;
    private AppCompatEditText edAnswer4;

    private AppCompatButton btnSaveQuestion;

    private ConstraintLayout csYN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_question);

        spinner = findViewById(R.id.spCategory);

        csMultipleChoice = findViewById(R.id.csMultipleChoice);
        csYN = findViewById(R.id.csYN);

        edQuestion = findViewById(R.id.edQuestion);
        edAnswer1 = findViewById(R.id.edAnswer1);
        edAnswer2 = findViewById(R.id.edAnswer2);
        edAnswer3 = findViewById(R.id.edAnswer3);
        edAnswer4 = findViewById(R.id.edAnswer4);

        btnSaveQuestion = findViewById(R.id.btnSaveQuestion);

        loadCategory();

        RadioGroup rdTypeQuestion = findViewById(R.id.rdGroup);
        getTypeQuestionEnum(rdTypeQuestion.getCheckedRadioButtonId());

        rdTypeQuestion.setOnCheckedChangeListener((group, checkedId) -> getTypeQuestionEnum(checkedId));

        RadioGroup rdExactAnswer = findViewById(R.id.rdExactAnswer);

        RadioGroup rdYN = findViewById(R.id.rdYN);

        saveQuestion(rdExactAnswer, rdYN);
    }

    private void loadCategory() {
        firebaseFirestore.collection("develop")
                .document("quizz")
                .collection("category")
                .get()
                .addOnCompleteListener(task -> {
                    categories = task.getResult().toObjects(CategoryModel.class);
                    if (categories.isEmpty()) {
                        finish();
                    } else {
                        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(getApplicationContext(), categories);
                        spinner.setAdapter(categorySpinnerAdapter);

                        long spinnerDefaultPosition = categorySpinnerAdapter.getItemId(0);
                        spinner.setSelection(Math.toIntExact(spinnerDefaultPosition));
                        categoryModel = (CategoryModel) spinner.getAdapter().getItem(0);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent != null && parent.getChildCount() != 0) {
                                    categoryModel = (CategoryModel) spinner.getAdapter().getItem(position);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });
    }

    private void getTypeQuestionEnum(int checkedId) {
        String idName = getResources().getResourceEntryName(checkedId);
        if (idName.equals("rdbMultipleChoice")) {
            csMultipleChoice.setVisibility(View.VISIBLE);
            csYN.setVisibility(View.GONE);

            typeQuestionEnum = TypeQuestionEnum.MULTIPLE_CHOICE;
        } else if (idName.equals("rdbYN")) {
            csMultipleChoice.setVisibility(View.GONE);
            csYN.setVisibility(View.VISIBLE);

            typeQuestionEnum = TypeQuestionEnum.YES_NO;
        }
    }

    private int getCodeExactAnswer(String id) {
        switch (id) {
            case "rdb1":
                return 1;
            case "rdb2":
                return 2;
            case "rdb3":
                return 3;
            case "rdb4":
                return 4;
        }
        return 0;
    }

    private String getAnswerYN(String id) {
        if (id.equals("rdYes")) {
            return "Y";
        }
        return "N";
    }

    private void saveQuestion(RadioGroup rdExactAnswer, RadioGroup rdYN) {
        btnSaveQuestion.setOnClickListener(v -> {
            QuestionModel questionModel = new QuestionModel();

            List<AnswerModel> answerModels = Arrays.asList(
                    new AnswerModel(1, String.valueOf(edAnswer1.getText())),
                    new AnswerModel(2, String.valueOf(edAnswer2.getText())),
                    new AnswerModel(3, String.valueOf(edAnswer3.getText())),
                    new AnswerModel(4, String.valueOf(edAnswer4.getText()))
            );


            questionModel.setQuestion(String.valueOf(edQuestion.getText()));
            questionModel.setTypeQuestionEnum(typeQuestionEnum);
            questionModel.setAnswerModels(answerModels);

            questionModel.setExactlyAnswer(getCodeExactAnswer(getResources().getResourceEntryName(rdExactAnswer.getCheckedRadioButtonId())));
            questionModel.setAnswerYN(getAnswerYN(getResources().getResourceEntryName(rdYN.getCheckedRadioButtonId())));
            questionModel.setCategoryCode(categoryModel.getCode());

            firebaseFirestore.collection("develop")
                    .document("quizz")
                    .collection("question")
                    .add(questionModel)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                        Log.d("SAVE_QUESTION", "DocumentSnapshot added with ID: " + documentReference.getId());
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT).show();
                        Log.w("SAVE_QUESTION", "Error adding document", e);
                    });
        });
    }
}
