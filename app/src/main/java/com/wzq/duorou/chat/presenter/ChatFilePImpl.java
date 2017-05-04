package com.wzq.duorou.chat.presenter;

import android.content.Context;


import com.wzq.duorou.beans.ChatFile;
import com.wzq.duorou.chat.impl.ChatFileDaoImpl;
import com.wzq.duorou.chat.model.ChatFileDao;
import com.wzq.duorou.chat.view.IChatFileView;
import com.wzq.duorou.utils.DeleteCacheTool;

import java.util.List;

/**
 * Created by weizhenqing on 2017/4/12.
 */

public class ChatFilePImpl implements ChatFilePresenter {

    private IChatFileView iFileView;
    private ChatFileDao chatFileDao;
    private Context context;

    public ChatFilePImpl(IChatFileView iFileView, Context context) {
        this.iFileView = iFileView;
        this.context = context;
        chatFileDao = new ChatFileDaoImpl(context);
    }

    @Override
    public void getChatFile(String userId) {
        List<ChatFile> chatFiles = chatFileDao.getChatFileById(userId);
        if (chatFiles.size() > 0){
            iFileView.getChatFile(chatFiles);
        }
    }

    @Override
    public void deleteChatFile(List<ChatFile> checkedList) {
        if (DeleteCacheTool.delFiles(checkedList)) {
           iFileView.deleteChatFile(chatFileDao.deleteListByPath(checkedList));
        }else {
            iFileView.deleteChatFile(false);
        }
    }
}
