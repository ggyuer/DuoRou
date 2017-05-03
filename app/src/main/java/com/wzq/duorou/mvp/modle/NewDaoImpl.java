package com.wzq.duorou.mvp.modle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wzq.duorou.beans.News;
import com.wzq.duorou.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenqing on 2017/4/12.
 */

public class NewDaoImpl implements NewsDao {

    private DBHelper dbHelper;

    public NewDaoImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public long saveNews(News news) {
        return 0;
    }

    //int id, String imgUrl, String title, String ping, String who, String location
    @Override
    public long saveNewsList(List<News> newses) {
        long i = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()){
            for (News news :newses){
                ContentValues values = new ContentValues();
                values.put(NEWS_ID,news.getId());
                values.put(NEWS_IMAGE,news.getImgUrl());
                values.put(NEWS_TITLE,news.getTitle());
                values.put(NEWS_PING,news.getPing());
                values.put(NEWS_TIME,news.getTime());
                values.put(NEWS_WHO,news.getWho());
                values.put(NEWS_LOCATION,news.getLocation());
                i = db.replace(TABLE_NAME,null,values);
            }
            return i;
        }
        return 0;
    }

    @Override
    public List<News> getNewsList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<News> newses = new ArrayList<>();
        if (db.isOpen()){
            Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(NEWS_ID));
                String img = cursor.getString(cursor.getColumnIndex(NEWS_IMAGE));
                String title = cursor.getString(cursor.getColumnIndex(NEWS_TITLE));
                String ping = cursor.getString(cursor.getColumnIndex(NEWS_PING));
                String time = cursor.getString(cursor.getColumnIndex(NEWS_TIME));
                String who = cursor.getString(cursor.getColumnIndex(NEWS_WHO));
                String location = cursor.getString(cursor.getColumnIndex(NEWS_LOCATION));
                News news = new News(id,img,title,ping,time,who,location);
                newses.add(news);
            }
        }
        return null;
    }

    @Override
    public long delNews(int newsId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()){
            return db.delete(TABLE_NAME,"id = ?",new String[]{String.valueOf(newsId)});
        }
        return 0;
    }
}
