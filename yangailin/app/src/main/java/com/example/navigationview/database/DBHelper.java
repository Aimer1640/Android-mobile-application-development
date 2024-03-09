package com.example.navigationview.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.navigationview.R;
import com.example.navigationview.User;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "MyThings.db";
    public static final String TABLE_NAME = "userinfo";
    public static final String COLUMN_USERID = "uid";
    public static final String COLUMN_USERPWD = "upwd";
//    public static final String TABLE_NAME2 = "bill";

    //创建数据库语句
    private static final String CREATE_TABLE = "create table if not exists "
            + TABLE_NAME + "(" + COLUMN_USERID + " text not null primary key,"
            + COLUMN_USERPWD + " text not null)";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    //创建数据库方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
//            db.execSQL(CREATE_TABLE2);
            //      创建表示类型的表
            String sql = "create table typetb(id integer primary key autoincrement,typename varchar(10),imageId integer,sImageId integer,kind integer)";
            db.execSQL(sql);
//            insertType(db);
            //创建记账表
            sql = "create table accounttb(id integer primary key autoincrement,typename varchar(10),sImageId integer,beizhu varchar(80),money float," +
                    "time varchar(60),year integer,month integer,day integer,kind integer)";
            db.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //重置数据库方法(先删表，再建表)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table  if exists " + TABLE_NAME);
        db.execSQL(CREATE_TABLE);
//        db.execSQL("drop table  if exists " + TABLE_NAME2);
//        db.execSQL(CREATE_TABLE2);
    }


    //登录方法
    @SuppressLint("Range")
    public User userlogin(String userId, String userPwd) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_USERID, COLUMN_USERPWD},
                COLUMN_USERID + "=? and " + COLUMN_USERPWD + "=?",
                new String[]{userId, userPwd},
                null,
                null,
                null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = new User();
            user.setUserId(cursor.getString(cursor.getColumnIndex(COLUMN_USERID)));
            user.setUserPwd(cursor.getString(cursor.getColumnIndex(COLUMN_USERPWD)));

        }
        return user;
    }

    //注册方法
    public long registerUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERID, user.getUserId());
        contentValues.put(COLUMN_USERPWD, user.getUserPwd());
        return db.insert(TABLE_NAME, null, contentValues);

    }
}
