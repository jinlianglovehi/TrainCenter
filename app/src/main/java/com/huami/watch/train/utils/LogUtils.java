package com.huami.watch.train.utils;

import android.util.Log;

/**
 * Created by jinliang on 16/11/9.
 *  log 工具管理类
 */

public class LogUtils {

    private static final String TAG = LogUtils.class.getSimpleName();
    private static final boolean IS_DEBUG = true ;
    public static void print(String tag ,String method ,String data){
        if(IS_DEBUG){
            Log.i(TAG, new StringBuilder(method).append("---------").append(data).toString());
        }
    }

    public static void print(String tag, String method ){
        if(IS_DEBUG){
            Log.i(TAG, new StringBuilder(method).append("---------").toString());
        }

    }

}
