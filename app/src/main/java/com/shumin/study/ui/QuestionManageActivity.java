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
        for(int i = 0; i < 10; i++) {
            Questions questions = new Questions();
            questions.setName("题库" + i);
            questions.setDescription("这是第" + i + "套题");
            questions.setCreateTime("创建于:2016-05-" + (i + 1));
            questions.setLevel((i % 5) + 1);
            questions.setQuestionCount(10 + i);

            List<Question> questionList = new ArrayList<>();
            for(int j = 0; j < 10; j++) {
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
                questionList.add(question);
            }
            questions.setQuestionList(questionList);
            questions.setExamCount(questions.getQuestionList().size());
            mData.add(questions);
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
