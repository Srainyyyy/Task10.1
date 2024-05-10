package com.example.task61d;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Quiz extends AppCompatActivity {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private ProgressBar progressBar;
    private String description;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_content);
        description = (String) getIntent().getExtras().get("description");
        initializeQuiz();
        fetchData();
    }

    // 初始化
    private void initializeQuiz() {
        questionTextView = findViewById(R.id.textquestions);
        optionsRadioGroup = findViewById(R.id.answerGroup);
        progressBar = findViewById(R.id.progressBar);
        optionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton clicked = group.findViewById(checkedId);
                int selectedOptionIndex = group.indexOfChild(clicked);
                checkAnswer(selectedOptionIndex, clicked);
            }
        });
    }

    // 显示问题
    private void displayQuestion(int questionIndex) {
        if (questions != null && !questions.isEmpty() && questionIndex < questions.size()) {
            Question question = questions.get(questionIndex);
            questionTextView.setText(question.getQuestionText());
            optionsRadioGroup.clearCheck();

            for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
                RadioButton optionButton = (RadioButton) optionsRadioGroup.getChildAt(i);
                optionButton.setBackground(getResources().getDrawable(R.drawable.normal_option));
                optionButton.setText(question.getOptions().get(i));
            }
        }
    }

    // 检查答案
    private void checkAnswer(int selectedOptionIndex, RadioButton clicked) {
        if (questions != null && !questions.isEmpty() && currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            currentQuestionIndex++;
            progressBar.setProgress(currentQuestionIndex);
            TextView progressText = findViewById(R.id.progressNumber);
            progressText.setText(String.format("%d/%d", currentQuestionIndex, questions.size()));
            if (selectedOptionIndex == currentQuestion.getCorrectOptionIndex()) {
                score++;
                clicked.setBackground(getResources().getDrawable(R.drawable.change_style_true));
            } else {
                clicked.setBackground(getResources().getDrawable(R.drawable.change_style_false));
            }
            if (currentQuestionIndex < questions.size()) {
                displayQuestion(currentQuestionIndex);
            } else {
                showFinalPage();
            }
        }
    }

    // 获取问题数据
    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().readTimeout(10, java.util.concurrent.TimeUnit.MINUTES).build())
                .build();

        QuizApiService request = retrofit.create(QuizApiService.class);

        request.getQuiz(description).enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuizResponse> call, @NonNull Response<QuizResponse> response) {
                if (response.isSuccessful()) {
                    QuizResponse quizResponse = response.body();
                    if (quizResponse != null) {
                        questions = quizResponse.getQuiz();
                        displayQuestion(currentQuestionIndex);
                    }
                } else {
                    Toast.makeText(Quiz.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuizResponse> call, @NonNull Throwable t) {
                Toast.makeText(Quiz.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 显示最终页面
    private void showFinalPage() {
        setContentView(R.layout.final_page);

        Button restartButton = findViewById(R.id.restart);
        TextView showScoreTextView = findViewById(R.id.textshowscore);
        double initialScore = (double) score / questions.size() * 100.0;
        int roundedScore = (int) initialScore;
        showScoreTextView.setText(String.format("%d", roundedScore));

        TextView[] questionTextViews = new TextView[]{
                findViewById(R.id.question1),
                findViewById(R.id.question2),
                findViewById(R.id.question3)
        };

        TextView[] answerTextViews = new TextView[]{
                findViewById(R.id.answer1),
                findViewById(R.id.answer2),
                findViewById(R.id.answer3)
        };

        for (int i = 0; i < Math.min(3, questions.size()); i++) {
            Question question = questions.get(i);
            questionTextViews[i].setText(question.getQuestionText());
            String correctAnswer = question.getOptions().get(question.getCorrectOptionIndex());
            answerTextViews[i].setText(String.format("Correct Answer: %s", correctAnswer));
        }

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartApp();
            }
        });
    }

    // 重启应用
    public void restartApp() {
        Intent intent = new Intent(this, this.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    // Retrofit接口
    interface QuizApiService {
        @GET("getQuiz")
        Call<QuizResponse> getQuiz(@Query("topic") String topic);
    }
}
