package com.example.task61d;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HistoryDBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "history.db";
    private static final int DATABASE_VERSION = 9;

    // 历史记录表的名称和列名
    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    public static final String COLUMN_INCORRECT_ANSWER = "incorrect_answer";
    private static final String COLUMN_USER_ANSWER = "user_answer";

    // 创建历史记录表的 SQL 语句
    private static final String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_QUESTION + " TEXT," +
            COLUMN_CORRECT_ANSWER + " TEXT," +
            COLUMN_INCORRECT_ANSWER + " TEXT," +
            COLUMN_USER_ANSWER + " TEXT)";

    // 构造函数，初始化数据库助手类
    public HistoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 创建数据库表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HISTORY_TABLE);
    }

    // 更新数据库表
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    // 插入新的历史记录
    public long insertHistoryRecord(String question, String correctAnswer, List<String> incorrectAnswers, String userAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1;

        try {
            db.beginTransaction();
            String concatenatedIncorrectAnswers = TextUtils.join(",", incorrectAnswers);
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUESTION, question);
            values.put(COLUMN_CORRECT_ANSWER, correctAnswer);
            values.put(COLUMN_INCORRECT_ANSWER, concatenatedIncorrectAnswers);
            values.put(COLUMN_USER_ANSWER, userAnswer);
            newRowId = db.insert(TABLE_HISTORY, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("HistoryDBHelper", "error: " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
        return newRowId;
    }

    // 获取所有历史记录
    public List<History> getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORY, null);
        List<History> data = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int questionTextIndex = cursor.getColumnIndex(COLUMN_QUESTION);
                int correctAnswerIndex = cursor.getColumnIndex(COLUMN_CORRECT_ANSWER);
                int incorrectAnswerIndex = cursor.getColumnIndex(COLUMN_INCORRECT_ANSWER);
                int userAnswerIndex = cursor.getColumnIndex(COLUMN_USER_ANSWER);
                if (questionTextIndex != -1 && correctAnswerIndex != -1 && incorrectAnswerIndex != -1 && userAnswerIndex != -1) {
                    String questionText = cursor.getString(questionTextIndex);
                    String correctAnswer = cursor.getString(correctAnswerIndex);
                    String incorrectAnswersString = cursor.getString(incorrectAnswerIndex);
                    List<String> incorrectAnswers = Arrays.asList(incorrectAnswersString.split(","));
                    String userAnswer = cursor.getString(userAnswerIndex);
                    History history = new History(questionText, correctAnswer, incorrectAnswers, userAnswer);
                    data.add(history);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return data;
    }


}
