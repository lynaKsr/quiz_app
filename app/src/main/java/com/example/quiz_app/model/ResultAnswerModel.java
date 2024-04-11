package com.example.quiz_app.model;

public class ResultAnswerModel {
    private String answerYN;
    private QuestionModel questionModel;

    public String getAnswerYN() {
        return answerYN;
    }

    public void setAnswerYN(String answerYN) {
        this.answerYN = answerYN;
    }

    public QuestionModel getQuestionModel() {
        return questionModel;
    }

    public void setQuestionModel(QuestionModel questionModel) {
        this.questionModel = questionModel;
    }

    @Override
    public String toString() {
        return "ResultAnswerModel{" +
                "answerYN='" + answerYN + '\'' +
                ", questionModel=" + questionModel +
                '}';
    }
}
