package com.shumin.study.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.shumin.study.R;
import com.shumin.study.bean.UserInfo;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;

public class RegisterActivity extends BaseActivity {

    private EditText mUsername;
    private EditText mPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitleRightBtnText(getString(R.string.submit));

        mUsername = (EditText) findViewById(R.id.register_username);
        mPassword = (EditText) findViewById(R.id.register_password);
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.register);
    }

    @Override
    protected void titleLeftBtnOnClick() {
        onBackPressed();
    }

    @Override
    protected void titleRightBtnOnClick() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText())) {
            Toast.makeText(this, R.string.cant_input_empty_userinfo, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            UserInfo userInfo = OrmDBUtils.queryUserInfoByUsername(mOrmDBHelper, mUsername.getText().toString());
            if (userInfo != null) {
                Toast.makeText(this, R.string.registed_username, Toast.LENGTH_SHORT).show();
            } else {
                userInfo = new UserInfo();
                userInfo.setUserName(mUsername.getText().toString());
                userInfo.setPassword(mPassword.getText().toString());
                userInfo.setManager(false);
                OrmDBUtils.createOrUpdateUserInfo(mOrmDBHelper, userInfo);
                Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }
}
