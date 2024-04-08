package com.example.quiz_app;

import android.os.Parcel;
import android.os.Parcelable;

public class QuizData implements Parcelable {

    private String category;
    private int totalQuestions;

    public QuizData() {
        this.category = "";
        this.totalQuestions = 0;
    }

    protected QuizData(Parcel in) {
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

    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory() { return this.category; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeInt(totalQuestions);
    }
}
