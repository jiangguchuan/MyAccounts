package com.shumin.study.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Guchuan on 2016/5/13.
 */
@DatabaseTable(tableName = ExamHistory.TABLE_NAME)
public class ExamHistory {

    public static final String TABLE_NAME = "exam_history";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_QUESTIONS_ID = "questions_id";
    public static final String COLUMN_QUESTIONS_NAME = "questions_name";
    public static final String COLUMN_COMPLETED_COUNT = "completed_count";
    public static final String COLUMN_CORRECT_COUNT = "correct_count";
    public static final String COLUMN_TIME = "time";

    @DatabaseField(generatedId = true, columnName = "id", unique = true)
    private long mId;
    @DatabaseField(columnName = COLUMN_USER)
    private String mUser;
    @DatabaseField(columnName = COLUMN_QUESTIONS_ID)
    private long mQuestionsId;
    @DatabaseField(columnName = COLUMN_QUESTIONS_NAME)
    private String mQuestionsName;
    @DatabaseField(columnName = COLUMN_COMPLETED_COUNT)
    private int mCompletedCount;
    @DatabaseField(columnName = COLUMN_CORRECT_COUNT)
    private int mCorrectCount;
    @DatabaseField(columnName = COLUMN_TIME)
    private String mTime;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public long getQuestionsId() {
        return mQuestionsId;
    }

    public void setQuestionsId(long questionsId) {
        mQuestionsId = questionsId;
    }

    public String getQuestionsName() {
        return mQuestionsName;
    }

    public void setQuestionsName(String questionsName) {
        mQuestionsName = questionsName;
    }

    public int getCompletedCount() {
        return mCompletedCount;
    }

    public void setCompletedCount(int completedCount) {
        mCompletedCount = completedCount;
    }

    public int getCorrectCount() {
        return mCorrectCount;
    }

    public void setCorrectCount(int correctCount) {
        mCorrectCount = correctCount;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        this.mTime = time;
    }
}
