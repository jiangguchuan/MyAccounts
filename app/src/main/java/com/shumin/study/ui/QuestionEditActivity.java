package com.shumin.study.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.shumin.study.R;

public class QuestionEditActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_edit);

        setTitleRightBtnText(getString(R.string.submit));
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.question_edit);
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
