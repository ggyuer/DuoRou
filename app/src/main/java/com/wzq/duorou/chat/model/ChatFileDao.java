package com.wzq.duorou.chat.model;


import com.wzq.duorou.beans.ChatFile;

import java.util.List;
import java.util.Map;

/**
 * Created by weizhenqing on 2017/3/14.
 */

public interface ChatFileDao {

    public final static String TABLE_NAME = "chatFiles";
    public final static String CHAT_ID = "chat_id";
    public final static String TIME = "time";
    public final static String DURATION = "duration";
    public final static String USER_ID = "userId";
    public final static String CHAT_TYPE = "chatType";
    public final static String FILE_TYPE = "fileType";
    public final static String PATH = "path";
    public final static String SECRET = "secret";
    public final static String THUMB_PATH = "thumbPath";
    public final static String REMOTE_PATH = "remotePath";
    public final static String MESSAGE_ID = "messageId";

    public final static String IMG_FILE = "img_file";
    public final static String VIDEO_FILE = "video_file";
    public final static String HTTP_FILE = "http_file";


    /**
     * 获取全部聊天文件map
     *
     * @return
     */
    Map<String, ChatFile> getChatFileMap();

    /**
     * 获取全部文件 list
     * @return
     */
    List<ChatFile> getChatFileMList();

    /**
     * 保存聊天文件
     *
     * @param chatFile
     * @return
     */
     boolean saveChatFile(ChatFile chatFile) ;

    /**
     * 删除聊天文件
     *
     * @param userIds
     * @return
     */
    boolean deleteList(List<String> userIds) ;

    /**
     * 通过路径删除（路径唯一）
     *
     * @param chatFiles
     * @return
     */
    boolean deleteListByPath(List<ChatFile> chatFiles) ;
    /**
     * 通过id查询聊天文件
     *
     * @param id
     * @return
     */
    List<ChatFile> getChatFileById(String id);
}
