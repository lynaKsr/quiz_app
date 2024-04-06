package com.example.quiz_app.model;

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
}
