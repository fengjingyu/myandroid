package com.jingyu.android.common.exception;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jingyu.android.common.log.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author fengjingyu@foxmail.com
 */
public class ExceptionDb extends SQLiteOpenHelper {
    public static final String TAG = ExceptionDb.class.getSimpleName();

    public static String mDefaultDbName = "exception.db";
    public static int mVersion = 1;
    public static String mOperatorTableName = "exceptionTable";

    /**
     * 排序常量
     */
    public static final String SORT_DESC = " DESC";// 有个空格符号，勿删
    public static final String SORT_ASC = " ASC";// 有个空格符号，勿删

    /**
     * 以下是表字段
     */
    public static final String _ID = "_id";
    /**
     * 异常的详细信息
     */
    public static final String INFO = "info";
    /**
     * 异常发生的时间
     */
    public static final String EXCEPTION_TIME = "exceptionTime";
    /**
     * 是否上传成功 0 UPLOAD_NO 为没上传或上传失败，1 UPLOAD_YES 为上传成功
     */
    public static final String UPLOAD_SUCCESS = "uploadSuccess";
    /**
     * 哪一个用户，这个字段需要项目中去设置（可选）
     */
    public static final String USER_ID = "userId";
    /**
     * 唯一标识 = exceptionTime + "_" + uuid
     */
    public static final String UNIQUE_ID = "uniqueId";

    /**
     * 装db集合的
     */
    public static Map<String, ExceptionDb> map = new LinkedHashMap<String, ExceptionDb>();

    public static ExceptionDb getInstance(Context context) {

        return getInstance(context, mDefaultDbName);
    }

    public static ExceptionDb getInstance(Context context, String dbName) {

        ExceptionDb db = map.get(dbName);

        if (db != null) {
            return db;
        }

        synchronized (ExceptionDb.class) {
            if (map.get(dbName) == null) {
                map.put(dbName, new ExceptionDb(context, dbName));
            }
            return map.get(dbName);
        }

    }

    private ExceptionDb(Context context) {
        super(context, mDefaultDbName, null, mVersion);
    }

    private ExceptionDb(Context context, String dbName) {
        super(context, dbName, null, mVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + mOperatorTableName
                + "(" + _ID + " integer primary key autoincrement,"
                + INFO + " text, "
                + EXCEPTION_TIME + " text, "
                + UPLOAD_SUCCESS + " text, "
                + USER_ID + " text, "
                + UNIQUE_ID + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public ContentValues createContentValue(ExceptionInfo model) {
        ContentValues values = new ContentValues();
        values.put(INFO, model.getInfo());
        values.put(EXCEPTION_TIME, model.getExceptionTime());
        values.put(UPLOAD_SUCCESS, model.getUploadSuccess());
        values.put(USER_ID, model.getUserId());
        values.put(UNIQUE_ID, model.getUniqueId());
        return values;
    }

    public ExceptionInfo createModel(Cursor c) {
        ExceptionInfo model = new ExceptionInfo();
        model.setInfo(c.getString(c.getColumnIndex(INFO)));
        model.setExceptionTime(c.getString(c.getColumnIndex(EXCEPTION_TIME)));
        model.setUploadSuccess(c.getString(c.getColumnIndex(UPLOAD_SUCCESS)));
        model.setUserId(c.getString(c.getColumnIndex(USER_ID)));
        model.setUniqueId(c.getString(c.getColumnIndex(UNIQUE_ID)));
        return model;
    }

    /**
     * 插入一条记录
     */
    public synchronized long insert(ExceptionInfo model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValue(model);
        long id = db.insert(mOperatorTableName, _ID, values);
        Logger.d(TAG, "insert()插入的记录的id是: " + id);
        db.close();
        return id;
    }

    public synchronized long inserts(List<ExceptionInfo> list) {
        int count = 0;
        SQLiteDatabase db = getWritableDatabase();
        for (ExceptionInfo model : list) {
            ContentValues values = createContentValue(model);
            long id = db.insert(mOperatorTableName, _ID, values);
            Logger.d(TAG, "insert()插入的记录的id是: " + id);
            count++;
        }
        db.close();
        return count;
    }

    /**
     * 删除所有记录
     */
    public synchronized int deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        int raw = db.delete(mOperatorTableName, null, null);
        db.close();
        return raw;
    }

    public synchronized int deleteByUniqueId(String value) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, UNIQUE_ID + "=?", new String[]{value + ""});
        Logger.d(TAG, "delete-->" + rows + "行");
        db.close();
        return rows;
    }

    /**
     * 删除与该用户有关的所有异常
     */
    public synchronized int deleteByUserId(String userId) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, USER_ID + "=?", new String[]{userId + ""});
        // Logger.d(TAG_DB, "delete_userid-->" + rows + "行");
        db.close();
        return rows;
    }

    /**
     * 删除所有上传成功了的异常信息，“1”为上传成功，“0”为未上传或失败
     */
    public synchronized int deleteUploadSuccess() {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, UPLOAD_SUCCESS + "=?", new String[]{ExceptionInfo.UPLOAD_YES});
        Logger.d(TAG, "delete_uploadSuccess-->" + rows + "行");
        db.close();
        return rows;
    }

    /**
     * 删除上传失败的记录
     */
    public synchronized int deleteUploadFail() {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, UPLOAD_SUCCESS + "=?", new String[]{ExceptionInfo.UPLOAD_NO});
        Logger.d(TAG, "delete_uploadFail-->" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized List<ExceptionInfo> queryByUniqueId(String value) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, UNIQUE_ID + "=?", new String[]{value}, null, null, null);
        List<ExceptionInfo> beans = new ArrayList<ExceptionInfo>();
        while (c.moveToNext()) {
            ExceptionInfo bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    /**
     * 查询上传成功的记录
     */
    public synchronized List<ExceptionInfo> queryUploadSuccess(String sort) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, UPLOAD_SUCCESS + "=?",
                new String[]{ExceptionInfo.UPLOAD_YES}, null, null, _ID + sort); // 条件为null可以查询所有,见api;ORDER
        List<ExceptionInfo> beans = new ArrayList<ExceptionInfo>();
        while (c.moveToNext()) {
            ExceptionInfo bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    /**
     * 查询上传失败的记录
     */
    public synchronized List<ExceptionInfo> queryUploadFail(String sort) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, UPLOAD_SUCCESS + "=?",
                new String[]{ExceptionInfo.UPLOAD_NO}, null, null, _ID + sort); // 条件为null可以查询所有,见api;ORDER
        List<ExceptionInfo> beans = new ArrayList<ExceptionInfo>();
        while (c.moveToNext()) {
            ExceptionInfo bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    /**
     * 查询共有多少条记录
     */
    public synchronized int queryCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, new String[]{"COUNT(*)"}, null, null, null, null, null, null);
        c.moveToNext();
        int count = c.getInt(0);
        c.close();
        db.close();
        return count;
    }

    /**
     * 查询所有
     */
    public synchronized List<ExceptionInfo> queryAllByIdDesc() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_DESC); // 条件为null可以查询所有
        List<ExceptionInfo> beans = new ArrayList<ExceptionInfo>();
        while (c.moveToNext()) {
            ExceptionInfo bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    /**
     * 查询所有
     */
    public synchronized List<ExceptionInfo> queryAllByIdAsc() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_ASC);
        List<ExceptionInfo> beans = new ArrayList<ExceptionInfo>();
        while (c.moveToNext()) {
            ExceptionInfo bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    /**
     * 分页查找
     */
    public synchronized List<ExceptionInfo> queryPageByIdAsc(int pageNum, int capacity) {
        String offset = (pageNum - 1) * capacity + ""; // 偏移量
        String len = capacity + ""; // 个数
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_ASC, offset + "," + len);
        List<ExceptionInfo> beans = new ArrayList<ExceptionInfo>();
        while (c.moveToNext()) {
            ExceptionInfo bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    /**
     * 分页查找
     */
    public synchronized List<ExceptionInfo> queryPageByIdDesc(int pageNum, int capacity) {
        String offset = (pageNum - 1) * capacity + ""; // 偏移量
        String len = capacity + ""; // 个数
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_DESC, offset + "," + len);
        List<ExceptionInfo> beans = new ArrayList<ExceptionInfo>();
        while (c.moveToNext()) {
            ExceptionInfo bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized int updateByUniqueId(ExceptionInfo model, String value) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValue(model);
        int rows = db.update(mOperatorTableName, values, UNIQUE_ID + "=?", new String[]{value + ""});
        Logger.d(TAG, "更新了" + rows + "行");
        db.close();
        return rows;
    }
}