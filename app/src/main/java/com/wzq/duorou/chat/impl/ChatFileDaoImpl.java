package com.wzq.duorou.chat.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.wzq.duorou.beans.ChatFile;
import com.wzq.duorou.chat.model.ChatFileDao;
import com.wzq.duorou.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weizhenqing on 2017/4/12.
 */

public class ChatFileDaoImpl implements ChatFileDao {

    private DBHelper dbHelper;

    public ChatFileDaoImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    /**
     * 获取全部聊天文件map
     *
     * @return
     */
    public Map<String, ChatFile> getChatFileMap() {
        Map<String, ChatFile> map = new HashMap<>();
        List<ChatFile> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from chatFiles", null);
            while (cursor.moveToNext()) {
                ChatFile chatFile = new ChatFile();
                String time = cursor.getString(cursor.getColumnIndex(TIME));
                String messageId = cursor.getString(cursor.getColumnIndex(MESSAGE_ID));
                String userId = cursor.getString(cursor.getColumnIndex(USER_ID));
                String chatType = cursor.getString(cursor.getColumnIndex(CHAT_TYPE));
                String fileType = cursor.getString(cursor.getColumnIndex(FILE_TYPE));
                String path = cursor.getString(cursor.getColumnIndex(PATH));
                String thumbPath = cursor.getString(cursor.getColumnIndex(THUMB_PATH));
                String remotePath = cursor.getString(cursor.getColumnIndex(REMOTE_PATH));
                String duration = cursor.getString(cursor.getColumnIndex(DURATION));
                String secret = cursor.getString(cursor.getColumnIndex(SECRET));
                int row_id = cursor.getInt(cursor.getColumnIndex(CHAT_ID));
                chatFile.setRow_id(row_id);
                chatFile.setUserId(userId);
                chatFile.setTime(time);
                chatFile.setSecret(secret);
                chatFile.setMessageId(messageId);
                chatFile.setDuration(duration);
                chatFile.setChatType(chatType);
                chatFile.setFileType(fileType);
                chatFile.setPath(path);
                chatFile.setThumbPath(thumbPath);
                chatFile.setRemotePath(remotePath);
                map.put(thumbPath, chatFile);
                list.add(chatFile);
            }
            cursor.close();
            return map;
        }
        return null;
    }

    public List<ChatFile> getChatFileMList() {
        List<ChatFile> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from chatFiles", null);
            while (cursor.moveToNext()) {
                ChatFile chatFile = new ChatFile();
                String duration = cursor.getString(cursor.getColumnIndex(DURATION));
                String messageId = cursor.getString(cursor.getColumnIndex(MESSAGE_ID));
                String time = cursor.getString(cursor.getColumnIndex(TIME));
                String userId = cursor.getString(cursor.getColumnIndex(USER_ID));
                String chatType = cursor.getString(cursor.getColumnIndex(CHAT_TYPE));
                String fileType = cursor.getString(cursor.getColumnIndex(FILE_TYPE));
                String path = cursor.getString(cursor.getColumnIndex(PATH));
                String thumbPath = cursor.getString(cursor.getColumnIndex(THUMB_PATH));
                String remotePath = cursor.getString(cursor.getColumnIndex(REMOTE_PATH));
                String secret = cursor.getString(cursor.getColumnIndex(SECRET));
                int row_id = cursor.getInt(cursor.getColumnIndex(CHAT_ID));
                chatFile.setRow_id(row_id);
                chatFile.setSecret(secret);
                chatFile.setTime(time);
                chatFile.setDuration(duration);
                chatFile.setMessageId(messageId);
                chatFile.setUserId(userId);
                chatFile.setChatType(chatType);
                chatFile.setFileType(fileType);
                chatFile.setPath(path);
                chatFile.setThumbPath(thumbPath);
                chatFile.setRemotePath(remotePath);
                list.add(chatFile);
            }
            cursor.close();
            return list;
        }
        return null;
    }

    /**
     * 保存聊天文件
     *
     * @param chatFile
     * @return
     */
    public boolean saveChatFile(ChatFile chatFile) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(TIME, chatFile.getTime());
            values.put(MESSAGE_ID, chatFile.getMessageId());
            values.put(SECRET, chatFile.getSecret());
            values.put(USER_ID, chatFile.getUserId());
            values.put(CHAT_TYPE, chatFile.getChatType());
            values.put(FILE_TYPE, chatFile.getFileType());
            values.put(PATH, chatFile.getPath());
            values.put(THUMB_PATH, chatFile.getThumbPath());
            values.put(REMOTE_PATH, chatFile.getRemotePath());
            values.put(DURATION, chatFile.getDuration());
            db.insert(TABLE_NAME, null, values);
            return true;
        }
        return false;
    }

    /**
     * 删除聊天文件
     *
     * @param userIds
     * @return
     */
    public boolean deleteList(List<String> userIds) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            if (userIds != null && userIds.size() > 0) {
                for (String userId : userIds) {
                    db.delete(TABLE_NAME, USER_ID + " = ?", new String[]{userId});
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 通过路径删除（路径唯一）
     *
     * @param chatFiles
     * @return
     */
    public boolean deleteListByPath(List<ChatFile> chatFiles) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            if (chatFiles != null && chatFiles.size() > 0) {
                for (ChatFile chatFile : chatFiles) {
                    if (chatFile.isChecked()) {
                        String path = chatFile.getPath();
                        String tPath = chatFile.getThumbPath();
                        if (!TextUtils.isEmpty(path)) {
                            db.delete(TABLE_NAME, PATH + " = ?", new String[]{path});
                        } else if (!TextUtils.isEmpty(tPath)) {
                            db.delete(TABLE_NAME, THUMB_PATH + " = ?", new String[]{tPath});
                        } else {
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }

    /**
     * 通过id查询聊天文件
     *
     * @param id
     * @return
     */
    public List<ChatFile> getChatFileById(String id) {
        List<ChatFile> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from chatFiles where userId = ?", new String[]{id});
            while (cursor.moveToNext()) {
                ChatFile chatFile = new ChatFile();
                String duration = cursor.getString(cursor.getColumnIndex(DURATION));
                String messageId = cursor.getString(cursor.getColumnIndex(MESSAGE_ID));
                String time = cursor.getString(cursor.getColumnIndex(TIME));
                String userId = cursor.getString(cursor.getColumnIndex(USER_ID));
                String chatType = cursor.getString(cursor.getColumnIndex(CHAT_TYPE));
                String fileType = cursor.getString(cursor.getColumnIndex(FILE_TYPE));
                String path = cursor.getString(cursor.getColumnIndex(PATH));
                String thumbPath = cursor.getString(cursor.getColumnIndex(THUMB_PATH));
                String remotePath = cursor.getString(cursor.getColumnIndex(REMOTE_PATH));
                String secret = cursor.getString(cursor.getColumnIndex(SECRET));
                int row_id = cursor.getInt(cursor.getColumnIndex(CHAT_ID));
                chatFile.setRow_id(row_id);
                chatFile.setSecret(secret);
                chatFile.setTime(time);
                chatFile.setDuration(duration);
                chatFile.setMessageId(messageId);
                chatFile.setUserId(userId);
                chatFile.setChatType(chatType);
                chatFile.setFileType(fileType);
                chatFile.setPath(path);
                chatFile.setThumbPath(thumbPath);
                chatFile.setRemotePath(remotePath);
                list.add(chatFile);
            }
            cursor.close();
            return list;
        }
        return null;
    }

}
