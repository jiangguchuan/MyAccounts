package com.shumin.study.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.shumin.study.R;

public class RegisterActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitleRightBtnText(getString(R.string.submit));
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

    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }
}
