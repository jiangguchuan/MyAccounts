package com.shumin.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.shumin.study.R;
import com.shumin.study.bean.ExamHistory;
import com.shumin.study.bean.Questions;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        setTitleRightBtnEnable(false);
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.app_name);
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
        Intent intent;
        switch (view.getId()) {
            case R.id.exam_btn:
                intent = new Intent(this, ExamActivity.class);
                List<Questions> list = new ArrayList<>();
                try {
                    list = OrmDBUtils.queryAllQuestions(mOrmDBHelper);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (list.size() == 0) {
                    Toast.makeText(this, R.string.no_questions, Toast.LENGTH_SHORT).show();
                    return;
                }
                Random random = new Random();
                int index = random.nextInt() % list.size();
                Toast.makeText(this, getString(R.string.random_questions, index), Toast.LENGTH_SHORT).show();
                Questions questions = list.get(index);
                intent.putExtra(ExamActivity.EXT_QUESTIONS_ID, questions.getId());
                intent.putExtra(ExamActivity.EXT_QUESTIONS_NAME, questions.getName());
                intent.putExtra(ExamActivity.EXT_TYPE, ExamActivity.TYPE_EXAM);
                startActivity(intent);
                break;
            case R.id.scan_question_btn:
                intent = new Intent(this, QuestionManageActivity.class);
                intent.putExtra(ExamActivity.EXT_TYPE, ExamActivity.TYPE_SCAN);
                startActivity(intent);
                break;
            case R.id.show_history_btn:
                intent = new Intent(this, ExamHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_btn:
//                intent = new Intent(this, PDFActivity.class);
//                startActivity(intent);
                finish();
                break;
        }
    }
}
