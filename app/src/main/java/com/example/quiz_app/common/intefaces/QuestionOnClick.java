package com.example.quiz_app.common.intefaces;

import com.example.quiz_app.model.QuestionModel;


public interface QuestionOnClick {
    /**
     *
     * @param position : la position de la question dans la liste
     * @param questionModel : objet de type QuestionModel
     * @param answer : la réponse sélectionnée par l'utilisateur
     */
    void onClick(int position, QuestionModel questionModel, String answer);
}
