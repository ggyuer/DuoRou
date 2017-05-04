package com.wzq.duorou.chat.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import com.wzq.duorou.beans.GroupNick;
import com.wzq.duorou.db.DBHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weizhenqing on 2017/4/13.
 */

public class GroupNickDaoImpl implements GroupNickDao {

    private DBHelper dbHelper;

    public GroupNickDaoImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public long saveShowNick(GroupNick nick) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(SHOW_USER_ID,nick.getUserId());
            values.put(SHOW_GROUP_ID,nick.getGroupId());
            values.put(SHOW_USER_NICK,nick.getUserNick());
            values.put(SHOW_GROUP_NICK,nick.getGroupNick());
            return db.replace(TABLE_NAME,null,values);
        }
        return 0;
    }

    @Override
    public String queryShowNick(String userId, String groupId) {
        return null;
    }

    @Override
    public Map<String, String> queryAllShowNick() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String,String> map = new HashMap<>();
        if (db.isOpen()){
            Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);
            while (cursor.moveToNext()){
                String userId = cursor.getString(cursor.getColumnIndex(SHOW_USER_ID));
                String groupId = cursor.getString(cursor.getColumnIndex(SHOW_GROUP_ID));
                String userNick = cursor.getString(cursor.getColumnIndex(SHOW_USER_NICK));
                String groupNick = cursor.getString(cursor.getColumnIndex(SHOW_GROUP_NICK));
                if (!TextUtils.isEmpty(groupNick)){
                    map.put(userId+groupId,groupNick);
                }else {
                    map.put(userId+groupId,userNick);
                }
            }
            return map;
        }

        return null;
    }
}
