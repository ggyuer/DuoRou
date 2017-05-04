package com.wzq.duorou.chat.view;


import com.wzq.duorou.beans.ChatFile;

import java.util.List;

/**
 * Created by weizhenqing on 2017/4/12.
 */

public interface IChatFileView {

    void getChatFile(List<ChatFile> files);

    void deleteChatFile(boolean flag);

}
