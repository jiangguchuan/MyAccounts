package com.shumin.study.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.shumin.study.FileUtils;
import com.shumin.study.JsonUtils;
import com.shumin.study.R;
import com.shumin.study.bean.Documents;
import com.shumin.study.database.OrmDBUtils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileChooseActivity extends BaseActivity implements View.OnClickListener
        , AdapterView.OnItemClickListener {

    private static final int FILE_SELECT_CODE = 0;
    public static final String EXT_LONG_DOCUMENTS_ID = "documents_id";

    private GridView mGridView;
    private ImageGridAdapter mAdapter;
    private EditText mName;
    private Button mSubmit;
    private List<String> mImageUrls = new ArrayList<>();
    private int mType;
    private long mDocumentsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choose);


        mGridView = (GridView) findViewById(R.id.image_grid);
        mName = (EditText) findViewById(R.id.documents_name);
        mSubmit = (Button) findViewById(R.id.documents_submit_btn);
        mSubmit.setOnClickListener(this);
        mAdapter = new ImageGridAdapter(this, mImageUrls);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

        mDocumentsId = getIntent().getLongExtra(EXT_LONG_DOCUMENTS_ID, 0);
        mType = getIntent().getIntExtra(DocumentsActivity.EXT_INT_TYPE, DocumentsActivity.TYPE_ADMIN);
        switch (mType) {
            case DocumentsActivity.TYPE_ADMIN:
                mName.setEnabled(true);
                mSubmit.setVisibility(View.VISIBLE);
                setTitleRightBtnEnable(true);
                setTitleRightBtnText(getString(R.string.add_img));
                setTitleName(getString(R.string.file_choose));
                break;
            case DocumentsActivity.TYPE_USER:
                mName.setEnabled(false);
                mSubmit.setVisibility(View.GONE);
                setTitleRightBtnEnable(false);
                initImageUrls();
                break;
        }

    }

    private void initImageUrls() {
        try {
            Documents info = OrmDBUtils.queryDocumentsById(mOrmDBHelper, mDocumentsId);
            mName.setVisibility(View.GONE);
            setTitleName(info.getName());
            mImageUrls = JsonUtils.parseToArrayList(info.getDataJson(), String.class);
            mAdapter.updateData(mImageUrls);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getCurrentTitleName() {
        return getString(R.string.file_choose);
    }

    @Override
    protected void titleLeftBtnOnClick() {
        finish();
    }

    @Override
    protected void titleRightBtnOnClick() {
        if (mImageUrls.size() < 4) {
            showFileChooser();
        } else {
            Toast.makeText(this, R.string.max_image_remind, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected ActionBarActivity getContext() {
        return this;
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String filePath = FileUtils.getPath(this, uri);
                    File file = new File(filePath);
                    android.util.Log.e("jgc", "" + file.length());
                    if (file.exists()) {
                        mImageUrls.add(filePath);
                        mAdapter.updateData(mImageUrls);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.documents_submit_btn:
                saveDocuments();
                break;
        }
    }

    private void saveDocuments() {
        if (mImageUrls.size() < 1) {
            Toast.makeText(this, R.string.min_image_remind, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mName.getText())) {
            Toast.makeText(this, R.string.empty_name, Toast.LENGTH_SHORT).show();
            return;
        }

        String jsonStr = null;
        try {
            jsonStr = JsonUtils.toString(mImageUrls);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(jsonStr)) {
            return;
        }

        Documents info = new Documents();
        info.setName(mName.getText().toString());
        info.setDataJson(jsonStr);

        OrmDBUtils.createOrUpdateDocuments(mOrmDBHelper, info);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String imagePath = mImageUrls.get(i);
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        intent.putExtra(ImageDisplayActivity.EXT_STR_IMG_PATH, imagePath);
        startActivity(intent);
    }

    class ImageGridAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> mData = new ArrayList<>();
        private BitmapFactory.Options mOptions = new BitmapFactory.Options();

        public ImageGridAdapter(Context context, List<String> data) {
            mContext = context;
            mData = data;
            mOptions.inJustDecodeBounds = true;
        }

        public void updateData(List<String> data) {
            mData = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.image_grid_item, null);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.item_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String filePath = mData.get(position);
            try {
                Bitmap bm = revitionImageSize(filePath, 500);
                holder.image.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }
    }

    private Bitmap revitionImageSize(String path, int size) throws IOException {
        // 取得图片
        FileInputStream temp = new FileInputStream(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
        BitmapFactory.decodeStream(temp, null, options);
        // 关闭流
        temp.close();

        // 生成压缩的图片
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            // 这一步是根据要设置的大小，使宽和高都能满足
            if ((options.outWidth >> i <= size)
                    && (options.outHeight >> i <= size)) {
                // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
                temp = new FileInputStream(path);
                // 这个参数表示 新生成的图片为原始图片的几分之一。
                options.inSampleSize = (int) Math.pow(2.0D, i);
                // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                options.inJustDecodeBounds = false;

                bitmap = BitmapFactory.decodeStream(temp, null, options);
                break;
            }
            i += 1;
        }
        temp.close();
        return bitmap;
    }
}