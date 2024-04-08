package com.example.quiz_app.model;

import java.util.Objects;

public class AnswerModel {
    private int code;
    private String answer;
    public AnswerModel(){}
    public AnswerModel(int code, String answer){
        this.code = code;
        this.answer = answer;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString(){
        return "AnswerModel{" +
                "code=" + code +
                ", answer='" + answer + '\''+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerModel that = (AnswerModel) o;

        if (code != that.code) return false;
        return Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}
