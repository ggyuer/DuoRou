package com.wzq.duorou.mvp.modle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wzq.duorou.App;
import com.wzq.duorou.beans.Breed;
import com.wzq.duorou.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenqing on 2017/5/10.
 */

public class BreedDaoImpl implements BreedDao {

    private DBHelper dbHelper;

    public BreedDaoImpl() {
        dbHelper = DBHelper.getInstance(App.getContext());
    }

    @Override
    public long saveBreedList(List<Breed> breeds) {
        long i = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            for (Breed breed : breeds) {
                ContentValues values = new ContentValues();
                values.put(BREED_ID, breed.getId());
                values.put(BREED_TITLE, breed.getTitle());
                values.put(BREED_IMAGE, breed.getImg());
                values.put(BREED_TIME, breed.getTime());
                values.put(BREED_LIKE_COUNT, breed.getLikeCount());
                values.put(BREED_SHARE_FROM, breed.getShareFrom());
                values.put(BREED_LOCATION, breed.getLocation());
                values.put(BREED_PATH, breed.getHttpPath());
                values.put(BREED_REID, breed.getReId());
                i = db.replace(TABLE_NAME, null, values);
            }
            return i;
        }
        return 0;
    }

    @Override
    public List<Breed> getBreedList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Breed> breeds = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(BREED_ID));
                String title = cursor.getString(cursor.getColumnIndex(BREED_TITLE));
                String img = cursor.getString(cursor.getColumnIndex(BREED_IMAGE));
                String time = cursor.getString(cursor.getColumnIndex(BREED_TIME));
                int count = cursor.getInt(cursor.getColumnIndex(BREED_LIKE_COUNT));
                String share = cursor.getString(cursor.getColumnIndex(BREED_SHARE_FROM));
                String location = cursor.getString(cursor.getColumnIndex(BREED_LOCATION));
                String path = cursor.getString(cursor.getColumnIndex(BREED_PATH));
                int rid = cursor.getInt(cursor.getColumnIndex(BREED_REID));

                Breed breed = new Breed(id, title, img, time, count, share, location, path,rid);
                breeds.add(breed);
            }
        }
        return null;
    }

    @Override
    public long delBreed(int breedId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            return db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(breedId)});
        }
        return 0;
    }
}
