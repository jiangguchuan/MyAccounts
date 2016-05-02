package com.shumin.study.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.shumin.study.R;

public class UserEditActivity extends BaseActivity {

    public static final String EXT_USERNAME = "username";
    public static final String EXT_PASSWORD = "password";
    public static final String EXT_IS_MANAGER = "is_manager";

    private EditText mUsername;
    private EditText mPassword;
    private CheckBox mCheckbox;

    private boolean mIsManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        mIsManger = getIntent().getBooleanExtra(EXT_IS_MANAGER, false);

        mUsername = (EditText) findViewById(R.id.edit_username);
        mPassword = (EditText) findViewById(R.id.edit_password);
        mCheckbox = (CheckBox) findViewById(R.id.edit_is_manager);

        mUsername.setText(getIntent().getStringExtra(EXT_USERNAME));
        mPassword.setText(getIntent().getStringExtra(EXT_PASSWORD));
        mCheckbox.setChecked(mIsManger);

        mUsername.setEnabled(mIsManger);
        mPassword.setEnabled(mIsManger);
        mCheckbox.setVisibility(mIsManger ? View.VISIBLE : View.GONE);
        setTitleRightBtnText(getString(mIsManger ? R.string.submit : R.string.delete));
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.edit_user_info);
    }

    @Override
    protected void titleLeftBtnOnClick() {
        onBackPressed();
    }

    @Override
    protected void titleRightBtnOnClick() {

    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }
}
