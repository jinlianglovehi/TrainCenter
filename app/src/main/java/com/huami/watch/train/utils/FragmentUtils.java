package com.huami.watch.train.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.huami.watch.train.R;

/**
 * Created by jinliang on 16/11/15.
 */

public class FragmentUtils {

    /**
     * 处理
     * @param activity
     * @param contrainId
     * @param fragment
     */
    public static  void replaceFragment(Activity activity, int contrainId, Fragment fragment){

        FragmentTransaction transaction = activity.getFragmentManager()
                .beginTransaction();
        transaction.replace(contrainId,fragment);
//        transaction.addToBackStack(null); 加入到回退站会有问题
        transaction.commit();

    }

}
