package com.shumin.study.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.shumin.study.R;
import com.shumin.study.adapter.ExamHistoryAdapter;
import com.shumin.study.bean.ExamHistory;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamHistoryActivity extends BaseActivity {

    private List<ExamHistory> mHistoryList = new ArrayList<>();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_history);
        initHistoryList();

        mListView = (ListView) findViewById(R.id.history_list);
        mListView.setAdapter(new ExamHistoryAdapter(this, mHistoryList));
    }

    @Override
    protected String getCurrentTitleName() {
        return null;
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

    private void initHistoryList() {
        try {
            mHistoryList = OrmDBUtils.queryAllExamHistory(mOrmDBHelper);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
