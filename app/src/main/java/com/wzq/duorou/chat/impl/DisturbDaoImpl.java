package com.wzq.duorou.chat.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.wzq.duorou.beans.Disturb;
import com.wzq.duorou.chat.model.DisturbDao;
import com.wzq.duorou.db.DBHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weizhenqing on 2017/4/11.
 */

public class DisturbDaoImpl implements DisturbDao {

    private DBHelper dbHelper;

    public DisturbDaoImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    public Map<String,Disturb> getDisturbList(){
        Map<String,Disturb> map = new HashMap<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()){
            Cursor cursor = db.rawQuery("select * from disturb",null);
            if (cursor.moveToNext()){
                String userId = cursor.getString(cursor.getColumnIndex(USER_ID));
                Disturb disturb = new Disturb();
                disturb.setUserId(userId);
                map.put(userId,disturb);
            }
            return map;
        }
        return null;
    }

    public boolean saveDisturbList(List<Disturb> disturbs){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            for (Disturb disturb:disturbs){
                values.put(USER_ID,disturb.getUserId());
                db.replace(TABLE_NAME,null,values);
            }
            return true;
        }
        return false;
    }

    public long saveDisturb(Disturb disturb){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(USER_ID,disturb.getUserId());
            return db.replace(TABLE_NAME,null,values);
        }
        return 0;
    }

    public long deleteDisturb(String userId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()){
            return db.delete(TABLE_NAME,USER_ID + " = ?",new String[]{userId});
        }
        return 0;
    }
}
