package com.shumin.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.shumin.study.ui.AdminActivity;
import com.shumin.study.ui.BaseActivity;
import com.shumin.study.ui.RegisterActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText mUserPasswordEditText;
    private CheckBox mShowPwd;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserPasswordEditText = (EditText) findViewById(R.id.login_password);
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
                startActivity(new Intent(this, AdminActivity.class));
                break;
        }
    }

    public void showOrHiddenPwd(boolean isChecked) {
        if (isChecked) {
            mUserPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mUserPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        String pwd = mUserPasswordEditText.getText().toString();
        if (!TextUtils.isEmpty(pwd)) {
            mUserPasswordEditText.setSelection(pwd.length());
        }
    }

}
