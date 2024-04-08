package com.example.quiz_app.model;

import com.example.quiz_app.common.enumerate.TypeQuestionEnum;

import java.util.List;
import java.util.Objects;

public class QuestionModel {
    private String cateCode;
    private String question;
    private TypeQuestionEnum typeQuestionEnum;
    private List<AnswerModel> answerModels;
    private int exactlyAnswer;
    private String answerYN;

    public QuestionModel() {
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
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
                "cateCode='" + cateCode + '\'' +
                ", question='" + question + '\'' +
                ", typeQuestionEnum=" + typeQuestionEnum +
                ", answerModels=" + answerModels +
                ", exactlyAnswer=" + exactlyAnswer +
                ", answerYN='" + answerYN + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        QuestionModel that = (QuestionModel) o;

        if (exactlyAnswer != that.exactlyAnswer)
            return false;
        if (!Objects.equals(cateCode, that.cateCode))
            return false;
        if (!Objects.equals(question, that.question))
            return false;
        if (typeQuestionEnum != that.typeQuestionEnum)
            return false;
        if (!Objects.equals(answerModels, that.answerModels))
            return false;

        return Objects.equals(answerYN, that.answerYN);
    }

    @Override
    public int hashCode() {
        int result = cateCode != null ? cateCode.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (typeQuestionEnum != null ? typeQuestionEnum.hashCode() : 0);
        result = 31 * result + (answerModels != null ? answerModels.hashCode() : 0);
        result = 31 * result + exactlyAnswer;
        result = 31 * result + (answerYN != null ? answerYN.hashCode() : 0);
        return result;
    }
}
