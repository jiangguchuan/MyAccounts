package com.shumin.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.shumin.study.R;

public class AdminActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        setTitleRightBtnEnable(false);

    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.title_activity_admin);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_manage_btn:
                startActivity(new Intent(this, UserMangeActivity.class));
                break;
            case R.id.question_publish_btn:
                startActivity(new Intent(this, QuestionPublishActivity.class));
                break;
            case R.id.question_manage_btn:
                startActivity(new Intent(this, QuestionManageActivity.class));
                break;
            case R.id.exit_btn:
                onBackPressed();
                break;
        }
    }
}
