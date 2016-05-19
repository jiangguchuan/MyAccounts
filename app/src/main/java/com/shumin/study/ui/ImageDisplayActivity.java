package com.shumin.study.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.shumin.study.R;

public class ImageDisplayActivity extends BaseActivity {

    public static final String EXT_STR_IMG_PATH = "img_path";

    private WebView mWebView;
    private String mImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        mWebView = (WebView) findViewById(R.id.image_display);

        mImagePath = getIntent().getStringExtra(EXT_STR_IMG_PATH);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.loadUrl("file://" + mImagePath);
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.display_img);
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
