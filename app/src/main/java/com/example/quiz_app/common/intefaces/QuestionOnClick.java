package com.example.quiz_app.common.intefaces;

import com.example.quiz_app.model.QuestionModel;

public interface QuestionOnClick {
    void onClick(int position, QuestionModel questionModel, String answer);
}
