package com.shumin.study.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
public class Questions {

    private int mId;
    private String mName;
    private String mDescription;
    private String mCreateTime;
    private int mQuestionCount;
    private int mExamCount;
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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
