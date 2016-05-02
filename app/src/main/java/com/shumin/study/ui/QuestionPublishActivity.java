package com.shumin.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.shumin.study.R;

public class QuestionPublishActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_publish);

        setTitleRightBtnEnable(false);

        findViewById(R.id.submit_btn).setOnClickListener(this);

    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.questions_edit);
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
            case R.id.submit_btn:
                startActivity(new Intent(this, QuestionEditActivity.class));
                break;
        }
    }
}
