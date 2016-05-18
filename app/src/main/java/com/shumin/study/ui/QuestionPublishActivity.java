package com.shumin.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shumin.study.R;
import com.shumin.study.Utility;
import com.shumin.study.bean.Questions;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionPublishActivity extends BaseActivity implements View.OnClickListener {

    private EditText mName;
    private EditText mDesc;
    private EditText mLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_publish);

        setTitleRightBtnEnable(false);

        findViewById(R.id.submit_btn).setOnClickListener(this);

        mName = (EditText) findViewById(R.id.questions_name);
        mDesc = (EditText) findViewById(R.id.questions_description);
        mLevel = (EditText) findViewById(R.id.questions_level);

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
                confirm();
                break;
        }
    }

    private void confirm() {
        String name = mName.getText().toString();
        String desc = mDesc.getText().toString();
        int level = 0;
        if (!TextUtils.isEmpty(mLevel.getText())) {
            level = Integer.parseInt(mLevel.getText().toString());
        }
        if (TextUtils.isEmpty(name)
                || TextUtils.isEmpty(desc)
                || TextUtils.isEmpty(mLevel.getText())) {
            Toast.makeText(this, R.string.complete_the_blank, Toast.LENGTH_SHORT).show();
            return;
        }

        if (level <= 0 || level > 5) {
            Toast.makeText(this, R.string.questions_level_hint, Toast.LENGTH_SHORT).show();
            return;
        }

        Questions questions = null;
        try {
            questions = OrmDBUtils.queryQuestionsByName(mOrmDBHelper, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (questions != null) {
            Toast.makeText(this, R.string.exist_questions_name, Toast.LENGTH_SHORT).show();
            return;
        }

        questions = new Questions();
        questions.setName(name);
        questions.setDescription(desc);
        questions.setCreateTime(Utility.getDate());
        questions.setLevel(level);
        OrmDBUtils.createOrUpdateQuestions(mOrmDBHelper, questions);

        try {
            questions = OrmDBUtils.queryQuestionsByName(mOrmDBHelper, name);
            Intent intent = new Intent(this, QuestionEditActivity.class);
            intent.putExtra(QuestionEditActivity.EXT_QUESTIONS_ID, questions.getId());
            startActivity(intent);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
