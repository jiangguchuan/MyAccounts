package com.shumin.study.database;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.shumin.study.bean.ExamHistory;
import com.shumin.study.bean.ExamRecord;
import com.shumin.study.bean.Question;
import com.shumin.study.bean.Questions;
import com.shumin.study.bean.UserInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Guchuan on 2016/5/8.
 */
public class OrmDBUtils {

    public static List<ExamHistory> queryAllExamHistory(OrmLiteSqliteOpenHelper helper) throws SQLException {
        QueryBuilder<ExamHistory, Long> builder = getQueryBuilder(helper, ExamHistory.class);
        return builder.query();
    }

    public static void createOrUpdateExamHistory(OrmLiteSqliteOpenHelper helper, ExamHistory history) {
        getDao(helper, ExamHistory.class).createOrUpdate(history);
    }

    public static void createOrUpdateExamRecord(OrmLiteSqliteOpenHelper helper, ExamRecord record) {
        getDao(helper, ExamRecord.class).createOrUpdate(record);
    }

    public static void deleteQuestion(OrmLiteSqliteOpenHelper helper, Question question) {
        getDao(helper, Question.class).delete(question);
    }

    public static void createOrUpdateQuestion(OrmLiteSqliteOpenHelper helper, Question question) {
        getDao(helper, Question.class).createOrUpdate(question);
    }

    public static List<Question> queryQuestionsByQuestionsId(OrmLiteSqliteOpenHelper helper, long questionsId) throws SQLException {
        QueryBuilder<Question, Long> builder = getQueryBuilder(helper, Question.class);
        return builder
                .where()
                .eq(Question.COLUMN_QUESTIONS_ID, questionsId)
                .query();
    }

    public static void createOrUpdateQuestions(OrmLiteSqliteOpenHelper helper, Questions questions) {
        getDao(helper, Questions.class).createOrUpdate(questions);
    }

    public static List<Questions> queryAllQuestions(OrmLiteSqliteOpenHelper helper) throws SQLException {
        QueryBuilder<Questions, Long> builder = getQueryBuilder(helper, Questions.class);
        return builder.query();
    }

    public static void deleteUserInfo(OrmLiteSqliteOpenHelper helper, UserInfo userInfo) {
        getDao(helper, UserInfo.class).delete(userInfo);
    }

    public static Questions queryQuestionsByName(OrmLiteSqliteOpenHelper helper, String name) throws SQLException {
        QueryBuilder<Questions, Long> builder = getQueryBuilder(helper, Questions.class);
        Questions questions = builder
                .where()
                .eq(Questions.COLUMN_NAME, name)
                .queryForFirst();
        return questions;
    }

    public static void createOrUpdateUserInfo(OrmLiteSqliteOpenHelper helper, UserInfo userInfo) {
        getDao(helper, UserInfo.class).createOrUpdate(userInfo);
    }

    public static UserInfo queryUserInfoByUsername(OrmLiteSqliteOpenHelper helper, String username) throws SQLException {
        QueryBuilder<UserInfo, Long> builder = getQueryBuilder(helper, UserInfo.class);
        UserInfo userInfo = builder
                .where()
                .eq(UserInfo.COLUMN_USERNAME, username)
                .queryForFirst();
        return userInfo;
    }

    public static List<UserInfo> queryAllUserInfo(OrmLiteSqliteOpenHelper helper) throws SQLException {
        QueryBuilder<UserInfo, Long> builder = getQueryBuilder(helper, UserInfo.class);
        return builder.query();
    }

    public static List<UserInfo> queryUserInfoByManager(OrmLiteSqliteOpenHelper helper, boolean isManager) throws SQLException {
        QueryBuilder<UserInfo, Long> builder = getQueryBuilder(helper, UserInfo.class);
        List<UserInfo> userList = builder
                .where()
                .eq(UserInfo.COLUMN_IS_MANAGER, isManager)
                .query();
        return userList;
    }

    private static <T> RuntimeExceptionDao<T, Long> getDao(OrmLiteSqliteOpenHelper helper, Class<T> clazz) {
        return helper.getRuntimeExceptionDao(clazz);
    }

    private static <T> QueryBuilder<T, Long> getQueryBuilder(OrmLiteSqliteOpenHelper helper, Class<T> clazz) {
        return getDao(helper, clazz).queryBuilder();
    }

    private static <T> DeleteBuilder<T, Long> getDeleteBuilder(OrmLiteSqliteOpenHelper helper, Class<T> clazz) {
        return getDao(helper, clazz).deleteBuilder();
    }

}
