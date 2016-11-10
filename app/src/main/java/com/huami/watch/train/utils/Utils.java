package com.huami.watch.train.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by jinliang on 16/11/9.
 */

public class Utils {


    /**
     * 进行界面跳转
     * @param mContext
     * @param className
     */
    public static void startActivity(Context mContext , Class className){
        Intent intent = new Intent(mContext,className);
        mContext.startActivity(intent);

    }
}
