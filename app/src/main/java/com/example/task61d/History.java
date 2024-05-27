package com.example.task61d;

import java.util.List;

public class History {
    // 问题文本
    private String questionText;
    // 正确答案
    private String correctAnswer;
    // 用户的答案
    private String userAnswer;
    // 错误的答案列表
    private List<String> incorrectAnswers;

    // 构造方法，用于初始化历史记录对象
    public History(String questionText, String correctAnswer, List<String> incorrectAnswers, String userAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    // 获取问题文本
    public String getQuestionText() {
        return questionText;
    }

    // 获取正确答案
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // 获取用户的答案
    public String getUserAnswer() {
        return userAnswer;
    }

    // 获取错误的答案列表
    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
