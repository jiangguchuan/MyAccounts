package com.shumin.study.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.shumin.study.R;
import com.shumin.study.bean.Question;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends BaseActivity implements CheckBox.OnCheckedChangeListener,
        View.OnClickListener {

    public static final int TYPE_EXAM = 0;
    public static final int TYPE_EDIT = 1;
    public static final int TYPE_SCAN = 2;
    public static final String EXT_QUESTIONS_ID = "questions_id";
    public static final String EXT_TYPE = "type";

    private List<CheckBox> mCheckboxList = new ArrayList<>();
    private List<Question> mData = new ArrayList<>();
    private EditText mSubject;
    private Button mSubmitBtn;
    private TextView mQuestTypeView;
    private int mType;
    private Question mCurrentQuestion;
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        setTitleRightBtnText(getString(R.string.delete));

        mSubject = (EditText) findViewById(R.id.subject_label);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);
        mQuestTypeView = (TextView) findViewById(R.id.question_type);
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
        for (int i = 0; i < 10; i++) {
            Question question = new Question();
            question.setSubject("第" + i + "题");
            if (i % 2 == 0) {
                question.setType(Question.TYPE_CHOICE);
                question.setOption1("第一个选项");
                question.setOption2("第二个选项");
                question.setOption3("第三个选项");
                question.setOption4("第四个选项");
                question.setRightAnswer(1);
            } else {
                question.setType(Question.TYPE_JUDGMENT);
                question.setCorrect(false);
            }
            mData.add(question);
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
            case TYPE_SCAN:
                mSubject.setEnabled(false);
                mSubject.setBackgroundResource(0x0);
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
            case R.id.submit_btn:
                if (mCurrentIndex < mData.size() - 1) {
                    mCurrentIndex ++;
                    mCurrentQuestion = mData.get(mCurrentIndex);
                    for(CheckBox checkBox : mCheckboxList) {
                        checkBox.setChecked(false);
                    }
                    inflateQuestionToView(mCurrentQuestion);
                    if (mCurrentIndex == mData.size() - 1) {
                        mSubmitBtn.setText(R.string.submit);
                    }
                }
                break;
        }
    }
}
