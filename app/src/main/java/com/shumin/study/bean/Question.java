package com.shumin.study.bean;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
public class Question {

    public static final int TYPE_JUDGMENT = 1;
    public static final int TYPE_CHOICE = 2;

    private String mSubject;
    private int mType;
    private String mOption1;
    private String mOption2;
    private String mOption3;
    private String mOption4;
    private boolean mIsCorrect;
    private int mRightAnswer;

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getOption1() {
        return mOption1;
    }

    public void setOption1(String option1) {
        mOption1 = option1;
    }

    public String getOption2() {
        return mOption2;
    }

    public void setOption2(String option2) {
        mOption2 = option2;
    }

    public String getOption3() {
        return mOption3;
    }

    public void setOption3(String option3) {
        mOption3 = option3;
    }

    public String getOption4() {
        return mOption4;
    }

    public void setOption4(String option4) {
        mOption4 = option4;
    }

    public boolean isCorrect() {
        return mIsCorrect;
    }

    public void setCorrect(boolean correct) {
        mIsCorrect = correct;
    }

    public int getRightAnswer() {
        return mRightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        mRightAnswer = rightAnswer;
    }
}
