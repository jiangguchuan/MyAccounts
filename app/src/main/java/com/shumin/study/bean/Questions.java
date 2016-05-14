package com.shumin.study.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by Administrator on 2016/5/2 0002.
 */

@DatabaseTable(tableName = Questions.TABLE_NAME)
public class Questions {

    public static final String TABLE_NAME = "questions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_EXAM_COUNT = "exam_count";
    public static final String CLOLUMN_LEVEL = "level";

    @DatabaseField(generatedId = true, columnName = "id", unique = true)
    private long mId;
    @DatabaseField(columnName = COLUMN_NAME)
    private String mName;
    @DatabaseField(columnName = COLUMN_DESC)
    private String mDescription;
    @DatabaseField(columnName = COLUMN_TIME)
    private String mCreateTime;
    private int mQuestionCount;
    @DatabaseField(columnName = COLUMN_EXAM_COUNT)
    private int mExamCount;
    @DatabaseField(columnName = CLOLUMN_LEVEL)
    private int mLevel;
    private List<Question> mQuestionList;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(String createTime) {
        mCreateTime = createTime;
    }

    public int getQuestionCount() {
        return mQuestionCount;
    }

    public void setQuestionCount(int questionCount) {
        mQuestionCount = questionCount;
    }

    public int getExamCount() {
        return mExamCount;
    }

    public void setExamCount(int examCount) {
        mExamCount = examCount;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public List<Question> getQuestionList() {
        return mQuestionList;
    }

    public void setQuestionList(List<Question> questionList) {
        mQuestionList = questionList;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}
