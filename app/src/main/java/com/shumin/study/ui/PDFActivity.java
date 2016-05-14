package com.shumin.study.ui;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;

import com.shumin.study.R;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class PDFActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.adobe.reader/files/Downloads/快速入门.pdf";
        File file = new File(filePath);
        try {
            PDDocument pd = PDDocument.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.title_activity_pdf);
    }

    @Override
    protected void titleLeftBtnOnClick() {

    }

    @Override
    protected void titleRightBtnOnClick() {

    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

}
