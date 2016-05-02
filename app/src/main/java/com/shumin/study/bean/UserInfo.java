package com.shumin.study.bean;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
public class UserInfo {
    private String mUserName;
    private String mPassword;
    private boolean mIsManager;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isManager() {
        return mIsManager;
    }

    public void setManager(boolean manager) {
        mIsManager = manager;
    }
}
