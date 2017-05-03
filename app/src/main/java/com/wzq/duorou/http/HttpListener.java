package com.wzq.duorou.http;


/**
 * Created by wzq on 2017/3/14.
 */
public interface HttpListener {
    void onSuccess(Object result);

    void onFailure(int errorType, String message);
}
