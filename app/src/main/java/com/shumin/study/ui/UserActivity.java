package com.shumin.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.shumin.study.R;

public class UserActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        setTitleRightBtnEnable(false);
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

    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.exam_btn:
                intent = new Intent(this, ExamActivity.class);
                intent.putExtra(ExamActivity.EXT_TYPE, ExamActivity.TYPE_EXAM);
                startActivity(intent);
                break;
            case R.id.scan_question_btn:
                intent = new Intent(this, QuestionManageActivity.class);
                intent.putExtra(ExamActivity.EXT_TYPE, ExamActivity.TYPE_SCAN);
                startActivity(intent);
                break;
            case R.id.show_history_btn:
                break;
            case R.id.exit_btn:
                onBackPressed();
                break;
        }
    }
}
