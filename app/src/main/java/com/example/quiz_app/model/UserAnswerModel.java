package com.example.quiz_app.model;

import java.util.List;

public class UserAnswerModel {
    private String userId;
    private List<ResultAnswerModel> resultAnswerModels;

    public UserAnswerModel() {
    }

    public UserAnswerModel(String userId, List<ResultAnswerModel> resultAnswerModels){
        this.userId = userId;
        this.resultAnswerModels = resultAnswerModels;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ResultAnswerModel> getResultAnswerModels() {
        return resultAnswerModels;
    }

    public void setResultAnswerModels(List<ResultAnswerModel> resultAnswerModels) {
        this.resultAnswerModels = resultAnswerModels;
    }

    @Override
    public String toString() {
        return "UserAnswerModel{" +
                "userId='" + userId + '\'' +
                ", resultAnswerModels=" + resultAnswerModels +
                '}';
    }
}
