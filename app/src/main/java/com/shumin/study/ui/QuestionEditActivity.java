package com.shumin.study.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.shumin.study.R;
import com.shumin.study.bean.Question;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionEditActivity extends BaseActivity implements CheckBox.OnCheckedChangeListener,
        View.OnClickListener {

    public static final String EXT_QUESTIONS_ID = "questions_id";
    public static final String EXT_INDEX = "index";

    private long mQuestionsId;
    private List<CheckBox> mCheckboxList = new ArrayList<>();
    private EditText mSubject;
    private List<EditText> mOptions = new ArrayList<>();
    private int mCurrentSubjectIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_edit);

        setTitleRightBtnText(getString(R.string.submit));

        mQuestionsId = getIntent().getLongExtra(EXT_QUESTIONS_ID, 0);

        mCheckboxList.add((CheckBox) findViewById(R.id.option_1));
        mCheckboxList.add((CheckBox) findViewById(R.id.option_2));
        mCheckboxList.add((CheckBox) findViewById(R.id.option_3));
        mCheckboxList.add((CheckBox) findViewById(R.id.option_4));
        for (CheckBox checkBox : mCheckboxList) {
            checkBox.setOnCheckedChangeListener(this);
        }

        mOptions.add((EditText) findViewById(R.id.option_1_input));
        mOptions.add((EditText) findViewById(R.id.option_2_input));
        mOptions.add((EditText) findViewById(R.id.option_3_input));
        mOptions.add((EditText) findViewById(R.id.option_4_input));

        findViewById(R.id.submit_btn).setOnClickListener(this);

        mSubject = (EditText) findViewById(R.id.subject_label);

        mCurrentSubjectIndex = getIntent().getIntExtra(EXT_INDEX, 0);

        nextSubject();
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
        saveQuestion();
        finish();
    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            int i = 0;
            for (CheckBox checkBox : mCheckboxList) {
                if (buttonView != checkBox) {
                    checkBox.setChecked(false);
                }
                i ++;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                saveQuestion();
                break;
        }
    }

    private void saveQuestion() {
        int answerCount = 0;
        String subject = mSubject.getText().toString();
        int selectedIndex = -1;
        if (TextUtils.isEmpty(subject)) {
            Toast.makeText(this, R.string.empty_subject, Toast.LENGTH_SHORT).show();
            return;
        }
        for(EditText editText : mOptions) {
            if (!TextUtils.isEmpty(editText.getText())) {
                answerCount ++;
            }
        }
        for(int i = 0; i < mCheckboxList.size(); i++) {
            CheckBox box = mCheckboxList.get(i);
            if (box.isChecked()) {
                selectedIndex = i;
                break;
            }
        }
        if (answerCount < 1) {
            Toast.makeText(this, R.string.at_least_two_ansers, Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedIndex == -1) {
            Toast.makeText(this, R.string.choose_answer, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mOptions.get(selectedIndex).getText())) {
            Toast.makeText(this, R.string.empty_answer, Toast.LENGTH_SHORT).show();
            return;
        }
        Question question = new Question();
        question.setSubject(subject);;
        question.setType(Question.TYPE_CHOICE);
        question.setOption1(mOptions.get(0).getText().toString());
        question.setOption2(mOptions.get(1).getText().toString());
        question.setOption3(mOptions.get(2).getText().toString());
        question.setOption4(mOptions.get(3).getText().toString());
        question.setRightAnswer(selectedIndex);
        question.setQuestionsId(mQuestionsId);
        OrmDBUtils.createOrUpdateQuestion(mOrmDBHelper, question);
        nextSubject();
    }

    private void nextSubject() {
        setTitleName(getString(R.string.question_index, mCurrentSubjectIndex + 1));
        mSubject.setText("");
        for(EditText option : mOptions) {
            option.setText("");
        }
        mCurrentSubjectIndex ++;
    }
}
