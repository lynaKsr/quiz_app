package com.example.quiz_app.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz_app.R;
import com.example.quiz_app.common.enumerate.TypeQuestionEnum;
import com.example.quiz_app.model.QuestionModel;

import java.util.List;

public class QuestionT1Adapter extends RecyclerView.Adapter<QuestionT1Adapter.PageVH> {

    private Context context;

    private List<QuestionModel> questionModels;

    private TextView textView;

    public QuestionT1Adapter(Context context, List<QuestionModel> questionModels, TextView textView) {
        this.context = context;
        this.questionModels = questionModels;
        this.textView = textView;
    }

    @NonNull
    @Override
    public PageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PageVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_question, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PageVH holder, int position) {
        QuestionModel questionModel = questionModels.get(position);
        if (questionModel.getTypeQuestionEnum().equals(TypeQuestionEnum.MULTIPLE_CHOICE)) {
            holder.buttonChoose1.setText(questionModel.getAnswerModels().get(0).getAnswer());
            holder.buttonChoose2.setText(questionModel.getAnswerModels().get(1).getAnswer());
            holder.buttonChoose3.setText(questionModel.getAnswerModels().get(2).getAnswer());
            holder.buttonChoose4.setText(questionModel.getAnswerModels().get(3).getAnswer());
            textView.setText(questionModel.getQuestion());
//        Toast.makeText(context, "position: " + position, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return questionModels.size();
    }

    static class PageVH extends RecyclerView.ViewHolder {
        final Button buttonChoose1;
        final Button buttonChoose2;
        final Button buttonChoose3;
        final Button buttonChoose4;

        PageVH(View row) {
            super(row);
            this.buttonChoose1 = row.findViewById(R.id.buttonChoose1);
            this.buttonChoose2 = row.findViewById(R.id.buttonChoose2);
            this.buttonChoose3 = row.findViewById(R.id.buttonChoose3);
            this.buttonChoose4 = row.findViewById(R.id.buttonChoose4);
        }
    }
}
