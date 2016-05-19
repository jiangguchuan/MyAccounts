package com.shumin.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.shumin.study.bean.UserInfo;
import com.shumin.study.database.OrmDBHelper;
import com.shumin.study.database.OrmDBUtils;
import com.shumin.study.ui.AdminActivity;
import com.shumin.study.ui.BaseActivity;
import com.shumin.study.ui.RegisterActivity;
import com.shumin.study.ui.UserActivity;

import java.sql.SQLException;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText mPassword;
    private EditText mUsername;
    private CheckBox mShowPwd;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPassword = (EditText) findViewById(R.id.login_password);
        mUsername = (EditText) findViewById(R.id.login_username);
        mShowPwd = (CheckBox) findViewById(R.id.login_is_show_pwd);
        mLogin = (Button) findViewById(R.id.login_bt);

        mLogin.setOnClickListener(this);
        mShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                showOrHiddenPwd(b);
            }
        });

        setTitleRightBtnText(getString(R.string.register));
        Utility.insertDefaultAccount(mOrmDBHelper);
        if (!Utility.getBooleanPref(this, Utility.INSERT_DEFAULT_QUESTIONS, false)) {
            Utility.insertDefaultQuestions(this, mOrmDBHelper);
        }
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.app_name);
    }

    @Override
    protected void titleLeftBtnOnClick() {
        onBackPressed();
    }

    @Override
    protected void titleRightBtnOnClick() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                login();
                break;
        }
    }

    public void showOrHiddenPwd(boolean isChecked) {
        if (isChecked) {
            mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        String pwd = mPassword.getText().toString();
        if (!TextUtils.isEmpty(pwd)) {
            mPassword.setSelection(pwd.length());
        }
    }

    private void login() {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.cant_input_empty_userinfo, Toast.LENGTH_SHORT).show();
            return;
        }
        UserInfo userInfo = null;
        try {
            userInfo = OrmDBUtils.queryUserInfoByUsername(mOrmDBHelper, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (userInfo == null) {
            Toast.makeText(this, R.string.empty_username, Toast.LENGTH_LONG).show();
        } else if (!password.equals(userInfo.getPassword())) {
            Toast.makeText(this, R.string.invalid_user, Toast.LENGTH_LONG).show();
        } else {
            Utility.setStringPref(this, Utility.LOGGED_USER, userInfo.getUserName());
            Intent intent = new Intent(this, userInfo.isManager() ? AdminActivity.class : UserActivity.class);
            startActivity(intent);
            clearLoginInfo();
        }
//        Intent intent = new Intent(this, UserActivity.class);
//        startActivity(intent);
//        clearLoginInfo();
    }

    private void clearLoginInfo() {
        mUsername.setText("");
        mPassword.setText("");
    }
}
