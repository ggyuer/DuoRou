package com.wzq.duorou.utils;

import android.os.Environment;
import android.text.TextUtils;


import com.wzq.duorou.beans.ChatFile;

import java.io.File;
import java.util.List;

/**
 * 删除应用的缓存文件
 * Created by weizhenqing on 2017/3/18.
 */

public class DeleteCacheTool {

    /**
     * formats 文件夹名
     */
    public static String SD_PATH = Environment.getExternalStorageDirectory()
            + "/formats/";//获取文件夹

    /**
     * 通过文件路径删除缓存文件
     *
     * @param filePath
     * @return
     */
    public static boolean delFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除全部缓存
     * @param chatFiles
     * @return
     */
    public static boolean delFiles(List<ChatFile> chatFiles) {

        for (ChatFile chatFile : chatFiles) {
            String path = chatFile.getPath();
            String tPath = chatFile.getThumbPath();
            if (!TextUtils.isEmpty(path)) {
               return delFile(path);
            }else if (!TextUtils.isEmpty(tPath)){
                return delFile(tPath);
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     * 删除文件夹和文件夹里面的文件
     */
    public static void deleteDir() {
        File dir = new File(SD_PATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 判断文件是否是存在
     *
     * @param path
     * @return
     */
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

}
