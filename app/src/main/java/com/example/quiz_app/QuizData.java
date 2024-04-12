package com.example.quiz_app;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.quiz_app.model.QuestionModel;

import java.util.List;

public class QuizData implements Parcelable {
    private String username;
    private String category;
    private boolean loggedIn;
    private int totalQuestions;
    private int nbCorrectAnswers;

    public QuizData() {

    }

    protected QuizData(Parcel in) {
        username = in.readString();
        category = in.readString();
        loggedIn = in.readByte() != 0;
        nbCorrectAnswers = in.readInt();
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

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory() { return this.category; }

    public int getNbCorrectAnswers() {
        return nbCorrectAnswers;
    }
    public void setNbCorrectAnswers(int nbCorrectAnswers) {
        this.nbCorrectAnswers = nbCorrectAnswers;
    }

    public int getTotalQuestions() {
        return this.totalQuestions;
    }

    public void setTotalQuestions(int nbQuestions) {
        this.totalQuestions = nbQuestions;
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(category);
        dest.writeByte((byte) (loggedIn ? 1 : 0));
        dest.writeInt(nbCorrectAnswers);
        dest.writeInt(totalQuestions);
    }
}
