package com.wzq.duorou.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wzq.duorou.App;
import com.wzq.duorou.beans.TopicUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggyuer on 2017/5/5.
 */

public class TopicUserDao {


    public static String NAME = "topicUser";
    public static String NICK = "nick";
    public static String AVATAR = "avatar";
    public static String CONTENT = "content";

    private DBHelper helper;

    public TopicUserDao() {
        helper = DBHelper.getInstance(App.getInstance().getApplicationContext());
    }

    public void saveTopicUsers(List<TopicUser> users) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            for (TopicUser u : users) {
                values.put(NICK, u.getNick());
                values.put(AVATAR, u.getAvatar());
                values.put(CONTENT, u.getContent());
                db.replace(NAME, null, values);
            }
        }
    }


    public void saveTopicUser(TopicUser u) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();

            values.put(NICK, u.getNick());
            values.put(AVATAR, u.getAvatar());
            values.put(CONTENT, u.getContent());
            db.replace(NAME, null, values);
        }
    }


    public List<TopicUser> getAll() {

        List<TopicUser> users = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + NAME, null);
            while (cursor.moveToNext()) {
                String nick = cursor.getString(cursor.getColumnIndex(NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(AVATAR));
                String content = cursor.getString(cursor.getColumnIndex(CONTENT));
                TopicUser u = new TopicUser(avatar, nick, content);
                users.add(u);
            }
        }
        return users;
    }
}
