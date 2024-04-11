package com.example.quiz_app.common.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz_app.R;
import com.example.quiz_app.common.enumerate.TypeQuestionEnum;
import com.example.quiz_app.common.intefaces.QuestionOnClick;
import com.example.quiz_app.model.QuestionModel;

import java.util.List;

public class QuestionTypeAdapter extends RecyclerView.Adapter<QuestionTypeAdapter.PageVH> {

    private Context context;

    private List<QuestionModel> questionModels;

    //private TextView textView;

    private QuestionOnClick questionOnClick;

    public QuestionTypeAdapter(Context context, List<QuestionModel> questionModels) {
        this.context = context;
        this.questionModels = questionModels;
    }

    // cette méthode est appellée lorque la RecyclerView a besoin d'un nouveau ViewHolder pour repésenter un type de réponse
    // elle crée un pageVH qui contient les vues pour chaque élément de item_answer_question
    @NonNull
    @Override
    public PageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PageVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_question, parent, false));
    }

    // cette methode est appellée lorsque RecyclerView souhaite afficher les questions à une position spécifiques
    // les différentes vues de type de questions sont affichées ou masquées en fonction du type de question
    @Override
    public void onBindViewHolder(@NonNull PageVH holder, int position) {
        QuestionModel questionModel = questionModels.get(position);

        if (questionModel.getTypeQuestionEnum().equals(TypeQuestionEnum.MULTIPLE_CHOICE)) {
            holder.csMultipleChoice.setVisibility(View.VISIBLE);
            holder.csYN.setVisibility(View.GONE);

            holder.buttonChoose1.setText(questionModel.getAnswerModels().get(0).getAnswer());
            holder.buttonChoose2.setText(questionModel.getAnswerModels().get(1).getAnswer());
            holder.buttonChoose3.setText(questionModel.getAnswerModels().get(2).getAnswer());
            holder.buttonChoose4.setText(questionModel.getAnswerModels().get(3).getAnswer());

            setAnswerClick(holder.buttonChoose1, questionModel, questionModel.getExactlyAnswer() == 1 ? "Y" : "N", position, holder);
            setAnswerClick(holder.buttonChoose2, questionModel, questionModel.getExactlyAnswer() == 2 ? "Y" : "N", position, holder);
            setAnswerClick(holder.buttonChoose3, questionModel, questionModel.getExactlyAnswer() == 3 ? "Y" : "N", position, holder);
            setAnswerClick(holder.buttonChoose4, questionModel, questionModel.getExactlyAnswer() == 4 ? "Y" : "N", position, holder);
        }
        else if (questionModel.getTypeQuestionEnum().equals(TypeQuestionEnum.YES_NO)) {
            holder.csMultipleChoice.setVisibility(View.GONE);
            holder.csYN.setVisibility(View.VISIBLE);

            setAnswerClick(holder.ansY, questionModel, questionModel.getAnswerYN().equals("Y") ? "Y" : "N", position, holder);
            setAnswerClick(holder.ansN, questionModel, questionModel.getAnswerYN().equals("N") ? "Y" : "N", position, holder);
        }

    }

    private void setAnswerClick(View view, QuestionModel questionModel, String answer, int position, PageVH holder) {
        view.setOnClickListener(v -> {
            if(questionOnClick != null) {
                String viewName = context.getResources().getResourceEntryName(view.getId());

                if (context.getResources().getResourceEntryName(view.getId()).contains("buttonChoose")){
                    view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.selectedButtonColor)));

                    if (viewName.equals(context.getResources().getResourceEntryName(holder.buttonChoose1.getId())))
                    {
                        holder.buttonChoose2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                        holder.buttonChoose3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                        holder.buttonChoose4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                    }

                    if (viewName.equals(context.getResources().getResourceEntryName(holder.buttonChoose2.getId())))
                    {
                        holder.buttonChoose1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                        holder.buttonChoose3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                        holder.buttonChoose4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                    }

                    if (viewName.equals(context.getResources().getResourceEntryName(holder.buttonChoose3.getId())))
                    {
                        holder.buttonChoose1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                        holder.buttonChoose2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                        holder.buttonChoose4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                    }

                    if (viewName.equals(context.getResources().getResourceEntryName(holder.buttonChoose4.getId())))
                    {
                        holder.buttonChoose1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                        holder.buttonChoose2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                        holder.buttonChoose3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.matter_brown)));
                    }
                }

                questionOnClick.onClick(position, questionModel, answer);
            }
        });
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

        final ConstraintLayout csMultipleChoice;

        final ConstraintLayout csYN;

        final RadioGroup rdYN;

        final RadioButton ansY;

        final RadioButton ansN;

        PageVH(View row) {
            super(row);
            this.buttonChoose1 = row.findViewById(R.id.buttonChoose1);
            this.buttonChoose2 = row.findViewById(R.id.buttonChoose2);
            this.buttonChoose3 = row.findViewById(R.id.buttonChoose3);
            this.buttonChoose4 = row.findViewById(R.id.buttonChoose4);

            this.csMultipleChoice = row.findViewById(R.id.csMultipleChoice);
            this.csYN = row.findViewById(R.id.csYN);

            this.rdYN = row.findViewById(R.id.rdYN);
            this.ansY = row.findViewById(R.id.ansY);
            this.ansN = row.findViewById(R.id.ansN);
        }
    }

    public void setQuestionOnclick(QuestionOnClick questionOnclick) {
        this.questionOnClick = questionOnclick;
    }
}
