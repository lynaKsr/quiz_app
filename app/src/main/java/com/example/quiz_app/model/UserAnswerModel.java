package com.example.quiz_app.model;

import java.util.List;

public class UserAnswerModel {
    private String userId;
    private List<ResultAnswerModel> resultAnswerModels;

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
}
