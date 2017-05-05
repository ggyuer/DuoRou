package com.wzq.duorou;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import com.wzq.duorou.beans.TopUser;
import com.wzq.duorou.beans.TopicUser;

import java.util.List;
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

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");

    }

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        context = this;
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
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
