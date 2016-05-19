package com.shumin.study.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.shumin.study.JsonUtils;
import com.shumin.study.R;
import com.shumin.study.Utility;
import com.shumin.study.adapter.DocumentListAdapter;
import com.shumin.study.bean.Documents;
import com.shumin.study.database.OrmDBUtils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentsActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private static final int FILE_SELECT_CODE = 0;
    public static final String EXT_INT_TYPE = "type";
    public static final int TYPE_ADMIN = 0;
    public static final int TYPE_USER = 1;

    private ListView mListView;
    private DocumentListAdapter mAdapter;
    private List<Documents> mData = new ArrayList<>();
    private int mType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        mType = getIntent().getIntExtra(EXT_INT_TYPE, 1);
        switch (mType) {
            case TYPE_ADMIN:
                setTitleRightBtnText(getString(R.string.new_documents));
                break;
            case TYPE_USER:
                setTitleRightBtnEnable(false);
                break;
        }
        mListView = (ListView) findViewById(R.id.image_list);
        mAdapter = new DocumentListAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

    }

    private void initDataList() {
        try {
            mData = OrmDBUtils.queryAllDocuments(mOrmDBHelper);
            for(Documents info : mData) {
                String jsonStr = info.getDataJson();
                List<String> images = JsonUtils.parseToArrayList(jsonStr, String.class);
                info.setImages(images);
            }
            mAdapter.updateData(mData);
        } catch (SQLException e) {
            android.util.Log.e("jgc", "", e);
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDataList();
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.documents_list);
    }

    @Override
    protected void titleLeftBtnOnClick() {
        finish();
    }

    @Override
    protected void titleRightBtnOnClick() {
        startActivity(new Intent(this, FileChooseActivity.class));
    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long documentsId = mData.get(position).getId();
        Intent intent = new Intent(this, FileChooseActivity.class);
        intent.putExtra(DocumentsActivity.EXT_INT_TYPE, DocumentsActivity.TYPE_USER);
        intent.putExtra(FileChooseActivity.EXT_LONG_DOCUMENTS_ID, documentsId);
        startActivity(intent);
    }
}
