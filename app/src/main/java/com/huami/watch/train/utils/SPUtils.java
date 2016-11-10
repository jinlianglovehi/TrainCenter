package com.huami.watch.train.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jinliang on 16/11/9.
 */

public class SPUtils {

    public static final String SP_TRAIN_CENTER = "sp_train_center";


    // 训练状态  key
    public static final String TRAIN_PLAN_STATUS = "train_plan_status" ;


    /**
     * 设置训练状态
     * @param mContext
     * @param trainStatus
     */
    public static void  setTrainStatus(Context mContext , int trainStatus){
        SharedPreferences mPreferenceBlue = mContext.getSharedPreferences(SP_TRAIN_CENTER,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mPreferenceBlue.edit();
        edit.putInt(TRAIN_PLAN_STATUS,trainStatus);
        edit.commit();
    }

    /**
     * 获取训练状态
     * @param mContext
     * @return
     */
    public static int getTrainStatus(Context mContext){
        SharedPreferences mPreferenceBlue = mContext.getSharedPreferences(SP_TRAIN_CENTER,
                Context.MODE_PRIVATE);
        int train_status = mPreferenceBlue.getInt(TRAIN_PLAN_STATUS,0);
        return train_status;
    }




//    ########   训练状态 ##############


    /**
     * 训练 init
     */
    public static final int TRAIN_STATUS_INIT = 0 ;

    /**
     * 已进行训练 但是当前没有正在训练中
     */
    public static final int TRAIN_STATUS_HAS_NO_TASK= 1 ;

    /**
     * 正在训练中
     */
    public static final int TRAIN_STATUS_TASKING = 2 ;



}
