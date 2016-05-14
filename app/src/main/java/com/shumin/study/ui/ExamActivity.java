package com.shumin.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shumin.study.R;
import com.shumin.study.Utility;
import com.shumin.study.bean.ExamHistory;
import com.shumin.study.bean.ExamRecord;
import com.shumin.study.bean.Question;
import com.shumin.study.bean.Questions;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends BaseActivity implements CheckBox.OnCheckedChangeListener,
        View.OnClickListener {

    public static final int TYPE_EXAM = 0;
    public static final int TYPE_EDIT = 1;
    public static final int TYPE_SCAN = 2;
    public static final String EXT_QUESTIONS_ID = "questions_id";
    public static final String EXT_QUESTIONS_NAME = "questions_name";
    public static final String EXT_TYPE = "type";

    private List<CheckBox> mCheckboxList = new ArrayList<>();
    private List<Question> mData = new ArrayList<>();
    private List<ExamRecord> mResultList = new ArrayList<>();
    private EditText mSubject;
    private Button mNextBtn;
    private Button mSubmitBtn;
    private TextView mQuestTypeView;
    private int mType;
    private Question mCurrentQuestion;
    private int mCurrentIndex = 0;
    private long mQuestionsId;
    private String mQuestionsName;
    private String mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        setTitleRightBtnText(getString(R.string.delete));

        mUser = Utility.getStringPref(this, Utility.LOGGED_USER);
        mQuestionsId = getIntent().getLongExtra(EXT_QUESTIONS_ID, 0);
        mQuestionsName = getIntent().getStringExtra(EXT_QUESTIONS_NAME);

        mSubject = (EditText) findViewById(R.id.subject_label);
        mNextBtn = (Button) findViewById(R.id.next_btn);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);
        mQuestTypeView = (TextView) findViewById(R.id.question_type);
        mNextBtn.setOnClickListener(this);
        mSubmitBtn.setOnClickListener(this);

        mCheckboxList.add((CheckBox) findViewById(R.id.option_1));
        mCheckboxList.add((CheckBox) findViewById(R.id.option_2));
        mCheckboxList.add((CheckBox) findViewById(R.id.option_3));
        mCheckboxList.add((CheckBox) findViewById(R.id.option_4));

        for (CheckBox checkBox : mCheckboxList) {
            checkBox.setOnCheckedChangeListener(this);
        }
        initQuestionList();
        initView();

    }

    private void initQuestionList() {
        try {
            mData = OrmDBUtils.queryQuestionsByQuestionsId(mOrmDBHelper, mQuestionsId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mCurrentQuestion = mData.get(mCurrentIndex);
        mType = getIntent().getIntExtra(EXT_TYPE, TYPE_EXAM);
        switch (mType) {
            case TYPE_EDIT:
                mSubject.setEnabled(true);
                mSubject.setBackgroundResource(R.drawable.question_edit_bg);
                break;
            case TYPE_EXAM:
                setTitleRightBtnText(getString(R.string.submit));
                mSubmitBtn.setVisibility(View.GONE);
                mSubject.setEnabled(false);
                mSubject.setBackgroundResource(0x0);
                break;
            case TYPE_SCAN:
                for(CheckBox box : mCheckboxList) {
                    box.setEnabled(false);
                }
                mSubject.setEnabled(false);
                mSubject.setBackgroundResource(0x0);
                setTitleRightBtnText(getString(R.string.complete));
                mSubmitBtn.setVisibility(View.GONE);
                break;
        }
        inflateQuestionToView(mCurrentQuestion);
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.question_index);
    }

    @Override
    protected void titleLeftBtnOnClick() {
        onBackPressed();
    }

    @Override
    protected void titleRightBtnOnClick() {
        switch (mType) {
            case TYPE_EDIT:
                OrmDBUtils.deleteQuestion(mOrmDBHelper, mCurrentQuestion);
                Toast.makeText(this, R.string.delete_success, Toast.LENGTH_SHORT).show();
                showNextQuestion();
                break;
            case TYPE_EXAM:
                saveExamRecord();
                finish();
                break;
            case TYPE_SCAN:
                finish();
                break;
        }
    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            for (CheckBox checkBox : mCheckboxList) {
                if (compoundButton != checkBox) {
                    checkBox.setChecked(false);
                }
            }
        }
    }

    private void inflateQuestionToView(Question info) {
        mSubject.setText(info.getSubject());
        switch (info.getType()) {
            case Question.TYPE_JUDGMENT:
                mCheckboxList.get(0).setText(R.string.incorrect);
                mCheckboxList.get(1).setText(R.string.correct);
                mCheckboxList.get(2).setVisibility(View.GONE);
                mCheckboxList.get(3).setVisibility(View.GONE);
                mQuestTypeView.setText(R.string.judgment_question);
                if (mType == TYPE_EDIT || mType == TYPE_SCAN) {
                    if (info.isCorrect()) {
                        mCheckboxList.get(0).setChecked(true);
                    } else {
                        mCheckboxList.get(1).setChecked(true);
                    }
                }
                break;
            case Question.TYPE_CHOICE:
                mQuestTypeView.setText(R.string.choice_question);
                if (!TextUtils.isEmpty(info.getOption1())) {
                    mCheckboxList.get(0).setText(info.getOption1());
                    mCheckboxList.get(0).setVisibility(View.VISIBLE);
                } else {
                    mCheckboxList.get(0).setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(info.getOption1())) {
                    mCheckboxList.get(1).setText(info.getOption2());
                    mCheckboxList.get(1).setVisibility(View.VISIBLE);
                } else {
                    mCheckboxList.get(1).setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(info.getOption1())) {
                    mCheckboxList.get(2).setText(info.getOption3());
                    mCheckboxList.get(2).setVisibility(View.VISIBLE);
                } else {
                    mCheckboxList.get(2).setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(info.getOption1())) {
                    mCheckboxList.get(3).setText(info.getOption4());
                    mCheckboxList.get(3).setVisibility(View.VISIBLE);
                } else {
                    mCheckboxList.get(3).setVisibility(View.GONE);
                }
                if (mType == TYPE_EDIT || mType == TYPE_SCAN) {
                    mCheckboxList.get(info.getRightAnswer()).setChecked(true);
                }
                break;
        }
        setTitleName(getString(R.string.question_index, mCurrentIndex + 1));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                switch (mType) {
                    case TYPE_EDIT:
                        if (mCurrentQuestion != null) {
                            saveCurrentQuestion();
                            showNextQuestion();
                        }
                        break;
                    case TYPE_SCAN:
                        showNextQuestion();
                        break;
                    case TYPE_EXAM:
                        if (addResultToList() != -1) {
                            showNextQuestion();
                        }
                        break;
                }
                break;
            case R.id.submit_btn:
                switch (mType) {
                    case TYPE_EDIT:
                        saveCurrentQuestion();
                        finish();
                        Toast.makeText(this, R.string.changes_saved, Toast.LENGTH_SHORT).show();
                        break;
                    case TYPE_SCAN:
                        break;
                }
                break;
        }
    }

    private void saveCurrentQuestion() {
        if (TextUtils.isEmpty(mSubject.getText())) {
            Toast.makeText(ExamActivity.this, R.string.empty_subject, Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedIndex = -1;
        for (int i = 0; i < mCheckboxList.size(); i++) {
            CheckBox box = mCheckboxList.get(i);
            if (box.isChecked()) {
                selectedIndex = i;
                break;
            }
        }
        if (selectedIndex == -1) {
            Toast.makeText(ExamActivity.this, R.string.empty_answer, Toast.LENGTH_SHORT).show();
            return;
        }

        if (mCurrentQuestion.getType() == Question.TYPE_CHOICE) {
            mCurrentQuestion.setSubject(mSubject.getText().toString());
            mCurrentQuestion.setRightAnswer(selectedIndex);
            OrmDBUtils.createOrUpdateQuestion(mOrmDBHelper, mCurrentQuestion);
        }
    }

    private void showNextQuestion() {
        mCurrentIndex ++;
        if (mCurrentIndex < mData.size()) {
            mCurrentQuestion = mData.get(mCurrentIndex);
        }
        switch (mType) {
            case TYPE_EDIT:
                if (mCurrentIndex < mData.size()) {
                    for(CheckBox checkBox : mCheckboxList) {
                        checkBox.setChecked(false);
                    }
                    inflateQuestionToView(mCurrentQuestion);
                } else {
                    Intent intent = new Intent(ExamActivity.this, QuestionEditActivity.class);
                    intent.putExtra(QuestionEditActivity.EXT_QUESTIONS_ID, mQuestionsId);
                    intent.putExtra(QuestionEditActivity.EXT_INDEX, mCurrentIndex);
                    startActivity(intent);
                    finish();
                }
                break;
            case TYPE_SCAN:
                for(CheckBox checkBox : mCheckboxList) {
                    checkBox.setChecked(false);
                }
                if (mCurrentIndex < mData.size() - 1) {
                    inflateQuestionToView(mCurrentQuestion);
                } else if (mCurrentIndex == mData.size() - 1) {
                    inflateQuestionToView(mCurrentQuestion);
                    mNextBtn.setText(R.string.complete);
                } else {
                    finish();
                }
                break;
            case TYPE_EXAM:
                for(CheckBox checkBox : mCheckboxList) {
                    checkBox.setChecked(false);
                }
                if (mCurrentIndex < mData.size() - 1) {
                    inflateQuestionToView(mCurrentQuestion);
                } else if (mCurrentIndex == mData.size() - 1) {
                    inflateQuestionToView(mCurrentQuestion);
                    mNextBtn.setText(R.string.submit);
                } else {
                    saveExamRecord();
                    finish();
                }
                break;
        }
    }

    private int addResultToList() {
        int selectedIndex = -1;
        for (int i = 0; i < mCheckboxList.size(); i++) {
            CheckBox box = mCheckboxList.get(i);
            if (box.isChecked()) {
                selectedIndex = i;
                break;
            }
        }
        if (selectedIndex == -1) {
            Toast.makeText(ExamActivity.this, R.string.empty_answer, Toast.LENGTH_SHORT).show();
            return selectedIndex;
        }

        ExamRecord record = new ExamRecord();
        record.setQuestion(mCurrentQuestion);
        record.setUser(mUser);
        record.setAnswer(selectedIndex);

        if (record.getAnswer() == record.getRightAnswer()) {
            Toast.makeText(this, R.string.bingo, Toast.LENGTH_SHORT).show();
            record.setBingo(true);
        } else {
            String rightAnswer = null;
            switch(record.getRightAnswer()) {
                case 0:
                    rightAnswer = record.getOption1();
                    break;
                case 1:
                    rightAnswer = record.getOption2();
                    break;
                case 2:
                    rightAnswer = record.getOption3();
                    break;
                case 3:
                    rightAnswer = record.getOption4();
                    break;
            }
            Toast.makeText(this, getString(R.string.unbingo, rightAnswer), Toast.LENGTH_SHORT).show();
            record.setBingo(false);
        }
        mResultList.add(record);

        return selectedIndex;
    }

    private void saveExamRecord() {
        ExamHistory history = new ExamHistory();
        history.setUser(mUser);
        history.setQuestionsId(mQuestionsId);
        history.setQuestionsName(mQuestionsName);
        int correctCount = 0;
        for(ExamRecord record : mResultList) {
            if (record.isBingo()) {
                correctCount ++;
            }
        }
        history.setCorrectCount(correctCount);
        history.setCompletedCount(mResultList.size());
        OrmDBUtils.createOrUpdateExamHistory(mOrmDBHelper, history);

        try {
            ExamHistory h = OrmDBUtils.queryExamHistoryByQuestionsId(mOrmDBHelper, mQuestionsId);
            for(ExamRecord record : mResultList) {
                record.setHistoryId(h.getId());
                OrmDBUtils.createOrUpdateExamRecord(mOrmDBHelper, record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Questions questions = OrmDBUtils.queryQuestionsById(mOrmDBHelper, mQuestionsId);
            int examCount = questions.getExamCount() + 1;
            questions.setExamCount(examCount);
            OrmDBUtils.createOrUpdateQuestions(mOrmDBHelper, questions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
