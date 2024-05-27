package com.example.task61d;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<History> historyItems;

    public HistoryAdapter(List<History> historyItems) {
        this.historyItems = historyItems;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 加载单个历史记录项的布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        History historyItem = historyItems.get(position);
        String correctAnswer = historyItem.getCorrectAnswer();
        String userAnswer = historyItem.getUserAnswer();
        List<String> incorrectAnswers = historyItem.getIncorrectAnswers();

        holder.textQuestion.setText(historyItem.getQuestionText());

        if (userAnswer.equals(correctAnswer)) {
            // 用户答案正确时的处理
            holder.textViewCorrectAnswer.setText(correctAnswer + "  Correct answer");
            holder.textViewCorrectAnswer.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
            holder.textViewUserAnswer.setVisibility(View.GONE);
        } else {
            // 用户答案错误时的处理
            holder.textViewCorrectAnswer.setText(correctAnswer + "  -  Correct answer");
            holder.textViewCorrectAnswer.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
            holder.textViewUserAnswer.setText(userAnswer + "  -  Your answer");
            holder.textViewUserAnswer.setTextColor(Color.RED);
            holder.textViewUserAnswer.setVisibility(View.VISIBLE);

            if (incorrectAnswers.size() > 1) {
                // 如果有多个错误答案，则显示
                holder.incorrectAnswersLayout.setVisibility(View.VISIBLE);
                holder.incorrectAnswersLayout.removeAllViews();
                for (String answer : incorrectAnswers) {
                    TextView textViewIncorrectAnswer = new TextView(holder.itemView.getContext());
                    textViewIncorrectAnswer.setText(answer);
                    textViewIncorrectAnswer.setTextColor(Color.WHITE);
                    holder.incorrectAnswersLayout.addView(textViewIncorrectAnswer);
                }
            } else {
                holder.incorrectAnswersLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    // 定义 ViewHolder 类，包含历史记录项的视图组件
    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        ViewGroup incorrectAnswersLayout;
        TextView textViewUserAnswer;
        TextView textQuestion;
        TextView textViewCorrectAnswer;

        // 构造函数，初始化视图组件
        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            incorrectAnswersLayout = itemView.findViewById(R.id.incorrectAnswersLayout);
            textViewUserAnswer = itemView.findViewById(R.id.textViewUserAnswer);
            textQuestion = itemView.findViewById(R.id.textQuestion);
            textViewCorrectAnswer = itemView.findViewById(R.id.textViewCorrectAnswer);
        }
    }
}
