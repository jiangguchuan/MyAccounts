package com.shumin.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shumin.study.R;
import com.shumin.study.adapter.UserListAdapter;
import com.shumin.study.bean.UserInfo;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMangeActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private ListView mListView;
    private List<UserInfo> mUserInfoList = new ArrayList<>();
    private UserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mange);

        mListView = (ListView) findViewById(R.id.user_list);
        mListView.setOnItemClickListener(this);
    }

    private void initUserInfoList() {
        try {
            mUserInfoList = OrmDBUtils.queryAllUserInfo(mOrmDBHelper);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mAdapter = new UserListAdapter(this, mUserInfoList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.user_manage);
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
    protected void onResume() {
        super.onResume();
        initUserInfoList();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        UserInfo info = mUserInfoList.get(i);
        Intent intent = new Intent(this, UserEditActivity.class);
        intent.putExtra(UserEditActivity.EXT_USERNAME, info.getUserName());
        intent.putExtra(UserEditActivity.EXT_PASSWORD, info.getPassword());
        intent.putExtra(UserEditActivity.EXT_IS_MANAGER, info.isManager());
        startActivity(intent);
    }
}
