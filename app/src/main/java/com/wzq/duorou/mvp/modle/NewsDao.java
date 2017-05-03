package com.wzq.duorou.mvp.modle;

import com.wzq.duorou.beans.News;

import java.util.List;

/**
 * Created by weizhenqing on 2017/4/12.
 */

public interface NewsDao {

    String TABLE_NAME = "news";
    String NEWS_ID = "news_id";
    String NEWS_IMAGE = "news_image";
    String NEWS_TITLE = "news_title";
    String NEWS_PING = "news_ping";
    String NEWS_TIME = "news_time";
    String NEWS_WHO = "news_who";
    String NEWS_LOCATION = "news_location";


     List<News> getNewsList();

    long saveNewsList(List<News> newses);

     long saveNews(News news);

     long delNews(int newsId);
}

