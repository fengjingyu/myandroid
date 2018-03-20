package com.jingyu.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.jingyu.android.common.log.Logger;
import com.jingyu.app.model.User;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类是自动生成的
 */
public class UserDb extends SQLiteOpenHelper {
    public static final String TAG = "UserDb";

    public static String mDefaultDbName = "user.db";
    public static int mVersion = 1;
    public static String mOperatorTableName = "userTable";

    /**
     * 排序常量
     */
    public static String SORT_DESC = " DESC";// 有个空格符号，勿删
    public static String SORT_ASC = " ASC";// 有个空格符号，勿删

    /**
     * 以下是表字段
     */
    public static final String _ID = "_id";
    /**
     * 身份证
     */
    public static final String UNIQUE_ID = "uniqueId";
    /**
     * 名字
     */
    public static final String NAME = "name";
    /**
     * 性别 0 男 ，1 女
     */
    public static final String GENDER = "gender";
    /**
     * 年龄
     */
    public static final String AGE = "age";
    /**
     * 得分
     */
    public static final String SCORE = "score";
    /**
     * 爱好
     */
    public static final String HOBBY = "hobby";

    /**
     * 装db集合的
     */
    public static Map<String, UserDb> map = new LinkedHashMap<String, UserDb>();

    public static UserDb getInstance(Context context) {
        return getInstance(context, mDefaultDbName);
    }

    public static UserDb getInstance(Context context, String dbName) {

        UserDb db = map.get(dbName);

        if (db != null) {
            return db;
        }

        synchronized (UserDb.class) {
            if (map.get(dbName) == null) {
                map.put(dbName, new UserDb(context, dbName));
            }
            return map.get(dbName);
        }

    }

    private UserDb(Context context) {
        super(context, mDefaultDbName, null, mVersion);
    }

    private UserDb(Context context, String dbName) {
        super(context, dbName, null, mVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + mOperatorTableName
                + "(" + _ID + " integer primary key autoincrement,"
                + UNIQUE_ID + " text, "
                + NAME + " text, "
                + GENDER + " text, "
                + AGE + " text, "
                + SCORE + " text, "
                + HOBBY + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public ContentValues createContentValue(User user) {
        ContentValues values = new ContentValues();
        values.put(UNIQUE_ID, user.getUniqueId());
        values.put(NAME, user.getName());
        values.put(GENDER, user.getGender());
        values.put(AGE, user.getAge());
        values.put(SCORE, user.getScore());
        values.put(HOBBY, user.getHobby());
        return values;
    }

    public User createModel(Cursor c) {
        User user = new User();
        user.setUniqueId(c.getString(c.getColumnIndex(UNIQUE_ID)));
        user.setName(c.getString(c.getColumnIndex(NAME)));
        user.setGender(c.getString(c.getColumnIndex(GENDER)));
        user.setAge(c.getString(c.getColumnIndex(AGE)));
        user.setScore(c.getString(c.getColumnIndex(SCORE)));
        user.setHobby(c.getString(c.getColumnIndex(HOBBY)));
        return user;
    }

    /**
     * 插入一条记录
     */
    public synchronized long insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValue(user);
        long id = db.insert(mOperatorTableName, _ID, values);
        Logger.d(TAG, "insert()插入的记录的id是: " + id);
        db.close();
        return id;
    }

    /**
     * 批量插入数据
     */
    public synchronized long inserts(List<User> list) {
        int count = 0;
        SQLiteDatabase db = getWritableDatabase();
        for (User user : list) {
            ContentValues values = createContentValue(user);
            long id = db.insert(mOperatorTableName, _ID, values);
            Logger.d(TAG, "insert()插入的记录的id是: " + id);
            count++;
        }
        db.close();
        return count;
    }

    /**
     * 删除所有数据
     */
    public synchronized int deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        int raw = db.delete(mOperatorTableName, null, null);
        db.close();
        return raw;
    }

    /**
     * 根据name删除
     */
    public synchronized int deleteByName(String name) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, NAME + " = ?", new String[]{name});
        Logger.d(TAG, "deleteByName()-->" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized int deleteByGenderAge(String gender, String age) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, GENDER + " = ? and " + AGE + " = ?", new String[]{gender, age});
        Logger.d(TAG, "deleteByGenderAge()-->" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized int updateByUniqueId(User user, String uniqueId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValue(user);
        int rows = db.update(mOperatorTableName, values, UNIQUE_ID + " = ?", new String[]{uniqueId});
        Logger.d(TAG, "updateByUniqueId()更新了" + rows + "行");
        db.close();
        return rows;
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

    public synchronized List<User> queryAllByIdDesc() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_DESC);
        List<User> beans = new ArrayList<User>();
        while (c.moveToNext()) {
            User bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<User> queryPageByIdAsc(int pageNum, int capacity) {
        String offset = (pageNum - 1) * capacity + ""; // 偏移量
        String len = capacity + ""; // 个数
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_ASC, offset + "," + len);
        List<User> beans = new ArrayList<User>();
        while (c.moveToNext()) {
            User bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<User> queryByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, NAME + " = ?", new String[]{name}, null, null, _ID + SORT_ASC, null);
        List<User> beans = new ArrayList<User>();
        while (c.moveToNext()) {
            User bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

}