package com.huami.watch.train.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.huami.watch.train.R;

/**
 * Created by jinliang on 16/11/16.
 */

public class DrawableUtils {

     private static final String TAG = DrawableUtils.class.getSimpleName();
    public static Drawable getTrainPlanDrawableByType(Context mContext , int  type){

        LogUtils.print(TAG, "getTrainPlanDrawableByType：" + type);
        Drawable drawable= null ;
        switch (type){
            case Constant.TRAIN_PLAN_CATEGORY_XINSHOU:
                drawable  = mContext.getResources().getDrawable(R.mipmap.training_list_icon_novice_road);
                break;
            case Constant.TRAIN_PLAN_CATEGORY_5KM:
                drawable  = mContext.getResources().getDrawable(R.mipmap.training_list_icon_5km);
                break;
            case Constant.TRAIN_PLAN_CATEGORY_10KM:
                drawable  = mContext.getResources().getDrawable(R.mipmap.training_list_icon_10km);
                break;
            case Constant.TRAIN_PLAN_CATEGORY_BANMA: // 半马
                drawable  = mContext.getResources().getDrawable(R.mipmap.training_list_icon_half_marathon);
                break;
            case Constant.TRAIN_PLAN_CATEGORY_QUANMA: // 全马
                drawable  = mContext.getResources().getDrawable(R.mipmap.training_list_icon_all_marathon);
                break;
        }

        return drawable ;
    }


    public static int getTrainPlanResIdByType( int  type){

        int resId = 0;
        switch (type){
            case Constant.TRAIN_PLAN_CATEGORY_XINSHOU:
                resId=R.mipmap.training_list_icon_novice_road;
                break;
            case Constant.TRAIN_PLAN_CATEGORY_5KM:
                resId =R.mipmap.training_list_icon_5km;
                break;
            case Constant.TRAIN_PLAN_CATEGORY_10KM:
                resId  = R.mipmap.training_list_icon_10km;
                break;
            case Constant.TRAIN_PLAN_CATEGORY_BANMA: // 半马
                resId  = R.mipmap.training_list_icon_half_marathon;
                break;
            case Constant.TRAIN_PLAN_CATEGORY_QUANMA: // 全马
                resId  = R.mipmap.training_list_icon_all_marathon;
                break;
        }
        return resId ;
    }


    /**
     *  获取运动的运动类型
     * @param mContext
     * @param runRemindType
     * @return
     */
    public static Drawable getDrawablByRunRemindType(Context mContext ,int runRemindType) {

        Drawable drawable = null;
        int sportType = getSportType(runRemindType);
        switch (sportType){
            case Constant.SPORT_TYPE_RUNNING:
                drawable =mContext.getResources().getDrawable(R.mipmap.training_icon_run);
                break;
            case Constant.SPORT_TYPE_REST:
                drawable =mContext.getResources().getDrawable(R.mipmap.training_icon_rest);
                break;
            case Constant.SPORT_TYPE_SWIMING:
                drawable =mContext.getResources().getDrawable(R.mipmap.training_icon_swimming);
                break;
            case Constant.SPORT_TYPE_RIDE:
                drawable =mContext.getResources().getDrawable(R.mipmap.training_icon_riding);
                break;
        }
        return drawable;
    }


    public static  int getSportType(int runRemindType){
        int  sportType  =0 ;
        switch (runRemindType) {
            case Constant.SPORT_CATEGORY_RUN_SLOW: // 慢跑
                sportType = Constant.SPORT_TYPE_RUNNING;
                break;
            case Constant.SPORT_CATEGORY_RUN_RESTORE: //恢复跑
                sportType = Constant.SPORT_TYPE_RUNNING;
                break;
            case Constant.SPORT_CATEGORY_RUN_BASIC:// 基础跑
                sportType = Constant.SPORT_TYPE_RUNNING;
                break;
            case Constant.SPORT_CATEGORY_RUN_ADVANCED://进阶跑
                sportType = Constant.SPORT_TYPE_RUNNING;
                break;
            case Constant.SPORT_CATEGORY_RUN_FATELEK:// 法特莱克跑
                sportType = Constant.SPORT_TYPE_RUNNING;
                break;
            case Constant.SPORT_CATEGORY_RUN_HILLSIDE:// 山坡跑
                sportType = Constant.SPORT_TYPE_RUNNING;
                break;
            case Constant.SPORT_CATEGORY_RUN_RHYTHM:// 节奏跑
                sportType = Constant.SPORT_TYPE_RUNNING;
                break;
            case Constant.SPORT_CATEGORY_RUN_INTERMITTENT_SHORT:// 跑步 短周期
                sportType = Constant.SPORT_TYPE_RUNNING;
                break;
            case Constant.SPORT_CATEGORY_RUN_INTERMITTENT_LONG://跑步 长周期
                sportType = Constant.SPORT_TYPE_RUNNING;
                break;
            case Constant.SPORT_CATEGORY_REST:// 休息
                sportType = Constant.SPORT_TYPE_REST;
                break;
            case Constant.SPORT_CATEGORY_SWIMMING:// 游泳
                sportType = Constant.SPORT_TYPE_SWIMING;
                break;
            case Constant.SPORT_CATEGORY_RIDING:// 骑行
                sportType = Constant.SPORT_TYPE_RIDE;
                break;
        }
        return sportType;
    }
}
