package com.shumin.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shumin.study.R;
import com.shumin.study.adapter.ExamHistoryAdapter;
import com.shumin.study.bean.ExamHistory;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamHistoryActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private List<ExamHistory> mData = new ArrayList<>();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_history);
        initHistoryList();

        mListView = (ListView) findViewById(R.id.history_list);
        mListView.setAdapter(new ExamHistoryAdapter(this, mData));
        mListView.setOnItemClickListener(this);
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

    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    private void initHistoryList() {
        try {
            mData = OrmDBUtils.queryAllExamHistory(mOrmDBHelper);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ExamHistory info = mData.get(i);
        Intent intent = new Intent(this, ExamRecordActivity.class);
        intent.putExtra(ExamRecordActivity.EXT_STR_HISTORY_ID, info.getId());
        startActivity(intent);
    }
}
