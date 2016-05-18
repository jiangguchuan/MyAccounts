package com.shumin.study.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shumin.study.R;
import com.shumin.study.adapter.QuestionsGridAdapter;
import com.shumin.study.bean.Question;
import com.shumin.study.bean.Questions;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionManageActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    private GridView mGridView;
    private List<Questions> mData = new ArrayList<>();
    private QuestionsGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_manage);

        mGridView = (GridView) findViewById(R.id.questions_grid);

        mAdapter = new QuestionsGridAdapter(this, mData);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        mGridView.setOnItemLongClickListener(this);
    }

    private void initQuestionsList() {
        try {
            mData = OrmDBUtils.queryAllQuestions(mOrmDBHelper);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Questions questions : mData) {
            try {
                List<Question> questionList = OrmDBUtils.queryQuestionsByQuestionsId(mOrmDBHelper, questions.getId());
                questions.setQuestionCount(questionList.size());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.questions);
    }

    @Override
    protected void titleLeftBtnOnClick() {
        onBackPressed();
    }

    @Override
    protected void titleRightBtnOnClick() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initQuestionsList();
        mAdapter.updateData(mData);
    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Questions info = mData.get(i);
        if (info.getQuestionCount() == 0) {
            Intent intent = new Intent(this, QuestionEditActivity.class);
            intent.putExtra(QuestionEditActivity.EXT_QUESTIONS_ID, info.getId());
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ExamActivity.class);
            intent.putExtra(ExamActivity.EXT_QUESTIONS_ID, info.getId());
            intent.putExtra(ExamActivity.EXT_QUESTIONS_NAME, info.getName());
            intent.putExtra(ExamActivity.EXT_TYPE, getIntent().getIntExtra(ExamActivity.EXT_TYPE, ExamActivity.TYPE_EDIT));
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        android.util.Log.e("jgc", "position -> " + i);
        final Questions questions = mData.get(i);
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.questions_delete_confirm, questions.getName()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        android.util.Log.e("jgc", "id -> " + questions.getId());
                        try {
                            OrmDBUtils.deleteQuestionsById(mOrmDBHelper, questions.getId());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        mData.remove(questions);
                        mAdapter.updateData(mData
                        );
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
        return true;
    }
}
