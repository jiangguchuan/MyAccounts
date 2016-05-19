package com.shumin.study.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.shumin.study.bean.Documents;
import com.shumin.study.bean.ExamHistory;
import com.shumin.study.bean.ExamRecord;
import com.shumin.study.bean.Question;
import com.shumin.study.bean.Questions;
import com.shumin.study.bean.UserInfo;

import java.sql.SQLException;

/**
 * Created by Guchuan on 2016/5/8.
 */
public class OrmDBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "study";

    private Context mContext;

    public OrmDBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
        mContext = context;
    }

    public OrmDBHelper(Context context) {
        this(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserInfo.class);
            TableUtils.createTable(connectionSource, Questions.class);
            TableUtils.createTable(connectionSource, Question.class);
            TableUtils.createTable(connectionSource, ExamHistory.class);
            TableUtils.createTable(connectionSource, ExamRecord.class);
            TableUtils.createTable(connectionSource, Documents.class);
        } catch (SQLException e) {
            android.util.Log.e("nsm", "catch exception in onCreate", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
