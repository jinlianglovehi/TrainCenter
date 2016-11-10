package com.huami.watch.train.utils;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by jinliang on 16/11/9.
 */

public class StringUtils {

    public static boolean isEmpty(String data){

        if(data!=null){
            return  TextUtils.isEmpty(data);
        }
        return false;

    }
}
