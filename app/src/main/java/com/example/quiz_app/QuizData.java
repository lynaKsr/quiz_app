package com.example.quiz_app;

import android.os.Parcel;
import android.os.Parcelable;

public class QuizData implements Parcelable {

    private String username;
    private String email;
    private String category;
    private int totalQuestions;

    public QuizData() {
        this.username = "";
        this.email = "";
        this.category = "";
        this.totalQuestions = 0;
    }

    protected QuizData(Parcel in) {
        username = in.readString();
        category = in.readString();
        totalQuestions = in.readInt();
    }

    public static final Creator<QuizData> CREATOR = new Creator<QuizData>() {
        @Override
        public QuizData createFromParcel(Parcel in) {
            return new QuizData(in);
        }

        @Override
        public QuizData[] newArray(int size) {
            return new QuizData[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(category);
        dest.writeInt(totalQuestions);
    }
}
