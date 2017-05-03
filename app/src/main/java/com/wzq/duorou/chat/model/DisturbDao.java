package com.wzq.duorou.chat.model;


import com.wzq.duorou.beans.Disturb;

import java.util.List;
import java.util.Map;

/**
 * Created by weizhenqing on 2017/3/13.
 */

public interface DisturbDao {

    String TABLE_NAME = "disturb";
    String USER_ID = "userId";

    /**
     * 获取所有的被设置免打扰的人
     * @return
     */
    Map<String,Disturb> getDisturbList();

    /**
     * 保存
     * @param disturbs
     * @return
     */
    boolean saveDisturbList(List<Disturb> disturbs);
    /**
     * 保存一个
     * @param disturb
     * @return
     */
    long saveDisturb(Disturb disturb);

    /**
     * 删除
     * @param userId
     * @return
     */
    long deleteDisturb(String userId);
}
