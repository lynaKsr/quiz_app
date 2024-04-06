package com.example.quiz_app.model;

import com.example.quiz_app.common.enumerate.TypeQuestionEnum;

import java.util.List;

public class QuestionModel {
    private String categoryCode;
    private String question;
    private TypeQuestionEnum typeQuestionEnum;
    private List<AnswerModel> answerModels;
    private int exactlyAnswer;
    private String answerYN;
    public QuestionModel() {
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public TypeQuestionEnum getTypeQuestionEnum() {
        return typeQuestionEnum;
    }

    public void setTypeQuestionEnum(TypeQuestionEnum typeQuestionEnum) {
        this.typeQuestionEnum = typeQuestionEnum;
    }

    public List<AnswerModel> getAnswerModels() {
        return answerModels;
    }

    public void setAnswerModels(List<AnswerModel> answerModels) {
        this.answerModels = answerModels;
    }

    public int getExactlyAnswer() {
        return exactlyAnswer;
    }

    public void setExactlyAnswer(int exactlyAnswer) {
        this.exactlyAnswer = exactlyAnswer;
    }

    public String getAnswerYN() {
        return answerYN;
    }

    public void setAnswerYN(String answerYN) {
        this.answerYN = answerYN;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "categoryCode='" + categoryCode + '\'' +
                ", question='" + question + '\'' +
                ", typeQuestionEnum=" + typeQuestionEnum +
                ", answerModels=" + answerModels +
                ", exactlyAnswer=" + exactlyAnswer +
                ", answerYN='" + answerYN + '\'' +
                '}';
    }
}
