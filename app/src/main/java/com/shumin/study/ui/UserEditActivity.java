package com.shumin.study.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.shumin.study.R;
import com.shumin.study.bean.UserInfo;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.util.List;

public class UserEditActivity extends BaseActivity implements View.OnClickListener{

    public static final String EXT_USERNAME = "username";
    public static final String EXT_PASSWORD = "password";
    public static final String EXT_IS_MANAGER = "is_manager";

    private EditText mUsername;
    private EditText mPassword;
    private CheckBox mCheckbox;
    private Button mSubmit;

    private boolean mIsManger;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        mIsManger = getIntent().getBooleanExtra(EXT_IS_MANAGER, false);

        mUsername = (EditText) findViewById(R.id.edit_username);
        mPassword = (EditText) findViewById(R.id.edit_password);
        mCheckbox = (CheckBox) findViewById(R.id.edit_is_manager);
        mSubmit = (Button) findViewById(R.id.user_edit_submit);
        mSubmit.setOnClickListener(this);

        mUsername.setText(getIntent().getStringExtra(EXT_USERNAME));
        mPassword.setText(getIntent().getStringExtra(EXT_PASSWORD));
        mCheckbox.setChecked(mIsManger);

        setTitleRightBtnText(getString(R.string.delete));

        try {
            mUserInfo = OrmDBUtils.queryUserInfoByUsername(mOrmDBHelper, getIntent().getStringExtra(EXT_USERNAME));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        if (!isLastManager(mUserInfo.getUserName())) {
            OrmDBUtils.deleteUserInfo(mOrmDBHelper, mUserInfo);
            Toast.makeText(this, R.string.delete_success, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.at_least_one_manager, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_edit_submit:
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, R.string.empty_username, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!mCheckbox.isChecked() && isLastManager(username)) {
                    Toast.makeText(this, R.string.at_least_one_manager, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mUserInfo == null) {
                    mUserInfo = new UserInfo();
                }
                mUserInfo.setUserName(username);
                mUserInfo.setPassword(password);
                mUserInfo.setManager(mCheckbox.isChecked());
                OrmDBUtils.createOrUpdateUserInfo(mOrmDBHelper, mUserInfo);
                Toast.makeText(this, R.string.modify_success, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    private boolean isLastManager(String username) {
        List<UserInfo> userList = null;
        try {
            userList = OrmDBUtils.queryUserInfoByManager(mOrmDBHelper, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (userList.size() == 1 && userList.get(0).getUserName().equals(username)) {
            return true;
        }
        return false;
    }
}
