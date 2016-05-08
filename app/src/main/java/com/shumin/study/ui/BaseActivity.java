package com.shumin.study.ui;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.shumin.study.R;
import com.shumin.study.database.OrmDBHelper;

public abstract class BaseActivity extends ActionBarActivity {

    private TextView mTitleLeftBtn;
    private TextView mTitleName;
    private Button mTitleRightBtn;
    private RelativeLayout mTitleBackground;
    private Dialog mInteractiveDlg;
    private LinearLayout mCustomTitleView;
    protected OrmDBHelper mOrmDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initDBHelper();
        intiActionBar(getContext());
    }

    private void initDBHelper() {
        if (mOrmDBHelper == null) {
            mOrmDBHelper = OpenHelperManager.getHelper(this, OrmDBHelper.class);
        }
    }

    protected void intiActionBar(ActionBarActivity context) {
        ActionBar actionBar = context.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        View actionbarLayout = LayoutInflater.from(context).inflate(
                R.layout.common_title, null);
        initView(actionbarLayout);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionbarLayout, params);
    }

    private void addListener() {
        mTitleLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleLeftBtnOnClick();
            }
        });
        mTitleRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleRightBtnOnClick();
            }
        });
    }


    private void initView(View actionbarLayout) {
        mTitleLeftBtn = (TextView) actionbarLayout.findViewById(R.id.title_left_btn);
        mTitleName = (TextView) actionbarLayout.findViewById(R.id.title_name);
        mTitleRightBtn = (Button) actionbarLayout.findViewById(R.id.title_right_btn);
        mTitleBackground = (RelativeLayout) actionbarLayout.findViewById(R.id.title_background);
        mCustomTitleView = (LinearLayout) actionbarLayout.findViewById(R.id.title_view);
        setTitleName();
        addListener();
    }

    protected void setTitleName() {
        mTitleName.setText(getCurrentTitleName());
    }

    protected void setTitleName(String title) {
        mTitleName.setText(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOrmDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mOrmDBHelper = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDBHelper();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void setTitleLeftBtnSrc(int resId) {
        mTitleLeftBtn.setBackgroundResource(resId);
    }

    protected void setLeftBtnText(String text) {
        setLeftBtnVisibility(View.VISIBLE);
        mTitleLeftBtn.setText(text);
    }

    protected void setTitleBackgroundColor(int color) {
        mTitleBackground.setBackgroundColor(color);
    }

    protected void setTitleRightBtnText(String text) {
        mTitleRightBtn.setText(text);
    }

    protected void setTitleRightBtnColor(int color) {
        mTitleRightBtn.setTextColor(color);
    }

    protected void setTitleRightBtnEnable(boolean isEnable) {
        mTitleRightBtn.setEnabled(isEnable);
    }

    protected void setTitleRightBtnBackground(int resId) {
        mTitleRightBtn.setBackgroundResource(resId);
    }

    protected void setRightBtnVisibility(int visibility) {
        mTitleRightBtn.setVisibility(visibility);

    }

    protected void setLeftBtnVisibility(int visibility) {
        mTitleLeftBtn.setVisibility(visibility);
    }

    protected View getTitleRightButton() {
        return mTitleRightBtn;
    }

    protected void setCustomTitleView(View view) {
        mTitleName.setVisibility(View.GONE);
        mCustomTitleView.addView(view);
    }

    protected void setLeftBtnEnable(boolean enable) {
        mTitleLeftBtn.setEnabled(enable);
    }

    protected abstract String getCurrentTitleName();

    protected abstract void titleLeftBtnOnClick();

    protected abstract void titleRightBtnOnClick();

    protected abstract ActionBarActivity getContext();
}
