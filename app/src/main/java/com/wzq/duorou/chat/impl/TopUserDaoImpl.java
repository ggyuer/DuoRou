package com.wzq.duorou.chat.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.wzq.duorou.beans.TopUser;
import com.wzq.duorou.chat.model.TopUserDao;
import com.wzq.duorou.db.DBHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weizhenqing on 2017/4/11.
 */

public class TopUserDaoImpl implements TopUserDao {

    private DBHelper dbHelper;

    public TopUserDaoImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public void saveTopUserList(List<TopUser> contactList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(TABLE_NAME, null, null);
            for (TopUser user : contactList) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME_ID, user.getUserName());
                values.put(COLUMN_NAME_TIME, user.getTime());
                values.put(COLUMN_NAME_IS_GOUP, user.getType());
                db.replace(TABLE_NAME, null, values);
            }
        }
    }

    @Override
    public Map<String, TopUser> getTopUserList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, TopUser> users = new HashMap<String, TopUser>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME
                    + " order by time asc ", null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor
                        .getColumnIndex(COLUMN_NAME_ID));
                long time = cursor.getLong(cursor
                        .getColumnIndex(COLUMN_NAME_TIME));
                int is_group = cursor.getInt(cursor
                        .getColumnIndex(COLUMN_NAME_IS_GOUP));
                TopUser user = new TopUser();
                user.setTime(time);
                user.setType(is_group);
                user.setUserName(username);
                users.put(username, user);
            }
            cursor.close();
        }
        return users;
    }

    @Override
    public long saveTopUser(TopUser user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, user.getUserName());
        values.put(COLUMN_NAME_TIME, user.getTime());
        values.put(COLUMN_NAME_IS_GOUP, user.getType());
        long i = 0;
        if (db.isOpen()) {
            i = db.replace(TABLE_NAME, null, values);
        }
        return i;
    }

    @Override
    public int deleteTopUser(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int i = 0;
        if (db.isOpen()) {
            i = db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?",
                    new String[]{username});
        }
        return i;
    }
}
