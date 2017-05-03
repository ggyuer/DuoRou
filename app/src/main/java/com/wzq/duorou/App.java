package com.wzq.duorou;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.wzq.duorou.beans.TopUser;

import java.util.Map;

/**
 * Created by wzq on 2017/3/10.
 */
public class App extends Application {

    public static Context context;
    private static App instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        context = this;

        Logger.init("hhh")
                .methodOffset(2)
                .methodCount(2)
                .hideThreadInfo()
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);

        instance = this;
        MyHelper.getInstance().init(context);
        //PreferenceManager.init(applicationContext);
    }

    public static Context getContext() {
        return context;
    }

    public static App getInstance() {
        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    /**
     * 获取内存中置顶好友user list
     *
     * @return
     */
    public Map<String, TopUser> getTopUserList() {
        return MyHelper.getInstance().getTopUserList();
    }

    /**
     * 设置置顶好友到内存中
     *
     * @param contactList
     */
    public void setTopUserList(Map<String, TopUser> contactList) {
        MyHelper.getInstance().setTopUserList(contactList);
    }

    /**
     * 删除会话置顶的好友
     *
     * @param userName
     */
    public int deleteTopUser(String userName) {
        return MyHelper.getInstance().deleteTopUser(userName);
    }

    /**
     * 保存单个会话置顶的好友
     *
     * @param user
     */
    public long setTopUser(TopUser user) {
        return MyHelper.getInstance().setTopUser(user);
    }
}
