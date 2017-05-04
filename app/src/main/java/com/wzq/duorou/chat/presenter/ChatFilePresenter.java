package com.wzq.duorou.chat.presenter;


import com.wzq.duorou.beans.ChatFile;

import java.util.List;

/**
 * Created by weizhenqing on 2017/4/12.
 */

public interface ChatFilePresenter {

    /**
     * 通过userId获取聊天记录
     * @param userId
     * @return
     */
    void getChatFile(String userId);

    /**
     * 删除聊天文件
     */
    void deleteChatFile(List<ChatFile> checkedFiles);
}
