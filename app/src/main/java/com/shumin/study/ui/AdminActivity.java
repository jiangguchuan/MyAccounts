package com.shumin.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.shumin.study.R;

import java.util.ArrayList;
import java.util.HashMap;

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
                android.util.Log.e("jgc", "1");
                startActivity(new Intent(this, UserMangeActivity.class));
                break;
            case R.id.question_publish_btn:
                android.util.Log.e("jgc", "2");
                break;
            case R.id.question_manage_btn:
                android.util.Log.e("jgc", "3");
                break;
            case R.id.exit_btn:
                android.util.Log.e("jgc", "4");
                break;
        }
    }
}
