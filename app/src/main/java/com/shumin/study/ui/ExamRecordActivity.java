package com.shumin.study.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shumin.study.R;
import com.shumin.study.bean.ExamRecord;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamRecordActivity extends BaseActivity {

    public static final String EXT_STR_HISTORY_ID = "history_id";

    private EditText mSubject;
    private List<CheckBox> mCheckboxList = new ArrayList<>();
    private TextView mRealAnswer;
    private long mHistoryId;
    private List<ExamRecord> mData = new ArrayList<>();
    private int mCurrentIndex = 0;
    private ExamRecord mCurrentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_record);

        setTitleRightBtnText(getString(R.string.next_question));

        mSubject = (EditText) findViewById(R.id.subject_label);
        mSubject.setEnabled(false);

        mCheckboxList.add((CheckBox) findViewById(R.id.option_1));
        mCheckboxList.add((CheckBox) findViewById(R.id.option_2));
        mCheckboxList.add((CheckBox) findViewById(R.id.option_3));
        mCheckboxList.add((CheckBox) findViewById(R.id.option_4));
        for(CheckBox box : mCheckboxList) {
            box.setEnabled(false);
        }

        mRealAnswer = (TextView) findViewById(R.id.selected_answer);

        mHistoryId = getIntent().getLongExtra(EXT_STR_HISTORY_ID, 0);

        initData();
        if (mData.size() >= 0) {
            nextPage();
        }
    }

    private void initData() {
        try {
            mData = OrmDBUtils.queryExamRecordByHistoryId(mOrmDBHelper, mHistoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.exam_history);
    }

    @Override
    protected void titleLeftBtnOnClick() {
        onBackPressed();
    }

    @Override
    protected void titleRightBtnOnClick() {
        nextPage();
    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    private void nextPage() {
        if (mCurrentIndex >= mData.size()) {
            Toast.makeText(this, R.string.no_more_question, Toast.LENGTH_SHORT).show();
            return;
        }
        for(CheckBox box : mCheckboxList) {
            box.setChecked(false);
        }
        mCurrentItem = mData.get(mCurrentIndex);
        setTitleName(getString(R.string.question_index, (mCurrentIndex + 1)));

        mSubject.setText(mCurrentItem.getSubject());

        if (mCurrentItem.getRightAnswer() == mCurrentItem.getAnswer()) {
            mRealAnswer.setTextColor(Color.GREEN);
            mRealAnswer.setText(R.string.bingo);
        } else {
            mRealAnswer.setTextColor(Color.RED);
            String answer = null;
            switch (mCurrentItem.getAnswer()) {
                case 0:
                    answer = mCurrentItem.getOption1();
                    break;
                case 1:
                    answer = mCurrentItem.getOption2();
                    break;
                case 2:
                    answer = mCurrentItem.getOption3();
                    break;
                case 3:
                    answer = mCurrentItem.getOption4();
                    break;
            }
            mRealAnswer.setText(getString(R.string.your_answer, answer));
        }

        mCheckboxList.get(0).setText(mCurrentItem.getOption1());
        mCheckboxList.get(1).setText(mCurrentItem.getOption2());
        mCheckboxList.get(2).setText(mCurrentItem.getOption3());
        mCheckboxList.get(3).setText(mCurrentItem.getOption4());

        mCheckboxList.get(mCurrentItem.getRightAnswer()).setChecked(true);

        mCurrentIndex ++;
    }

}
