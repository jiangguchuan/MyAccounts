package com.shumin.study.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/5/2 0002.
 */

@DatabaseTable(tableName = UserInfo.TABLE_NAME)
public class UserInfo {

    public static final String TABLE_NAME = "user_info";
    public static final String COLUMN_USERNAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IS_MANAGER = "is_manager";

    @DatabaseField(generatedId = true, columnName = "id", unique = true)
    private long mId;
    @DatabaseField(columnName = COLUMN_USERNAME)
    private String mUserName;
    @DatabaseField(columnName = COLUMN_PASSWORD)
    private String mPassword;
    @DatabaseField(columnName = COLUMN_IS_MANAGER)
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

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}
