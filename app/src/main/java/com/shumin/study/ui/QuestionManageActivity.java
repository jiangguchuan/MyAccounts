package com.shumin.study.ui;

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

public class QuestionManageActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private GridView mGridView;
    private List<Questions> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_manage);

        initQuestionsList();

        mGridView = (GridView) findViewById(R.id.questions_grid);

        mGridView.setAdapter(new QuestionsGridAdapter(this, mData));
        mGridView.setOnItemClickListener(this);
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
    protected ActionBarActivity getContext() {
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Questions info = mData.get(i);
        Intent intent = new Intent(this, ExamActivity.class);
        intent.putExtra(ExamActivity.EXT_QUESTIONS_ID, info.getId());
        intent.putExtra(ExamActivity.EXT_TYPE, getIntent().getIntExtra(ExamActivity.EXT_TYPE, ExamActivity.TYPE_EDIT));
        startActivity(intent);

    }
}
