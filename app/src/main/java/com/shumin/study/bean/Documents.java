package com.shumin.study.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by Guchuan on 2016/5/17.
 */
@DatabaseTable(tableName = Documents.TABLE_NAME)
public class Documents {

    public static final String TABLE_NAME = "documents";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATA_JSON = "data_json";
    public static final String COLUMN_ID = "id";

    @DatabaseField(generatedId = true, columnName = COLUMN_ID, unique = true)
    private long mId;
    @DatabaseField(columnName = COLUMN_NAME)
    private String mName;
    @DatabaseField(columnName = COLUMN_DATA_JSON)
    private String mDataJson;
    private List<String> mImages;


    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDataJson() {
        return mDataJson;
    }

    public void setDataJson(String dataJson) {
        mDataJson = dataJson;
    }

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> images) {
        mImages = images;
    }
}
