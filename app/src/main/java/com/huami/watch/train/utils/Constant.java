package com.huami.watch.train.utils;

/**
 * Created by jinliang on 16/11/10.
 *
 *  常量的设置
 */

public class Constant {


    public static final String SEPEAR = "/" ;

    /**
     * entity 属性字段
     */

    public static final String ENTITY_TITLE  ="title" ; // 标题

    public static final String ENTITY_WEEK_NUMBER = "week_number";// 训练周数

    public static final String ENTITY_CURRENT_TRAIN_RECORD_ID = "trainRecordId";//当前的训练id

    public static final String ENTITY_START_DATE ="start_date";// 开始记录的时间

    public static final String ENTITY_TRAIN_RECORD_STATUS = "train_status";

    // ############### 常量的分类  trainPlan #####################
    //新手
    public static final int  TRAIN_PLAN_CATEGORY_XINSHOU = 1 ;

    // 5km
    public static final int  TRAIN_PLAN_CATEGORY_5KM  =2 ;

    // 10km
    public static final int TRAIN_PLAN_CATEGORY_10KM = 3 ;

    // 半马
    public static final  int TRAIN_PLAN_CATEGORY_BANMA =4 ;

    // 全马
    public static final  int TRAIN_PLAN_CATEGORY_QUANMA = 5 ;

    // 获取的是训练计划总的分类
    public static final int TRAIN_PLAN_CATEGORY_PLAN_SUMMMARY = 100 ;

    // 设置的是跑步提醒的文案
    public static final int TRAIN_PLAN_RUNING_WRITE_COPY = 200 ;

//  ############## 常量分类结束 #####################


//  ################ 训练状态 ######################

    public static final int TRAIN_RECORD_UN_DONE = 0 ; // 训练记录没有完成

    // complete
    public static final int TRAIN_RECORD_DONE  = 1 ; // 训练记录完成


//    ############# widget 对应的数据 #################

    public static final int SPORT_CATEGORY_RUN_SLOW = 0 ; //跑步 - 慢跑

    public static final int SPORT_CATEGORY_RUN_RESTORE = 1 ;// 跑步 -- 恢复跑

    public static final int SPORT_CATEGORY_RUN_BASIC = 2 ;// 跑步 -- 基础跑

    public static final int SPORT_CATEGORY_RUN_ADVANCED = 3;// 跑步-- 进阶跑

    //Fatelek
    public static final int SPORT_CATEGORY_RUN_FATELEK  = 4 ; // 跑步 -- 法特莱克跑

    // hillside
    public static final int SPORT_CATEGORY_RUN_HILLSIDE  = 5 ;// 跑步 -- 山坡跑
// Rhythm
    public static final int SPORT_CATEGORY_RUN_RHYTHM = 6 ;// 跑步  -- 节奏跑

// Intermittent
    public static final int SPORT_CATEGORY_RUN_INTERMITTENT_SHORT = 7 ; // 跑步-- 短周期


    public static final int SPORT_CATEGORY_RUN_INTERMITTENT_LONG = 8 ;// 跑步 -- 长周期

    public static final int SPORT_CATEGORY_REST =9 ;// 休息

    public static final int SPORT_CATEGORY_SWIMMING = 10 ;// 游泳

    // Riding
    public static final int SPORT_CATEGORY_RIDING  = 11 ;//骑行


    /**
     * 跳转的应用的类型
     */

    public static final int SPORT_TYPE_RUNNING = 100 ;// 跑步

    public static final int SPORT_TYPE_SWIMING = 200 ;// 游泳

    public static final int SPORT_TYPE_RIDE  = 300 ;// 骑行

    public static final int SPORT_TYPE_REST = 400 ;// 休息

   // 跑步的常量
    public static final String EXTRA_KEY_SPORT_TYPE = "sport_type";
    public static final String EXTRA_KEY_TAG = "tag";
    public static final String EXTRA_KEY_DISTANCE = "distance";
    public static final String EXTRA_KEY_SPORT_STATUS = "sport_status";
    public static final String EXTRA_KEY_SPORT_SOURCE = "sport_source";
    public static final String EXTRA_GPS_STATUS = "sport_gps_status";

    public static final String EXTRA_KEY_ENTRANCE_TYPE = "entrance_type";
    public static final int EXTRA_VALUE_TAG_NONE = -1;
    public static final int EXTRA_VALUE_TAG_COUNT_DOWN = 1;
    public static final int EXTRA_VALUE_TAG_GPS_UNAVAILABLE = 2;
    public static final int extra_value_tag_sport = 3;
    public static final int EXTRA_VALUE_TAG_NOTIFICATION_STOP = 4;
    public static final int EXTRA_VALUE_TAG_NOTIFICATION_CONTINUE = 5;

    public static final int EXTRA_KEY_ENTRANCE_TYPE_TRAIN_CENTER = 2;


    /**
     * 运动类型
     */
    public static final int RUNNING = 1;
    public static final int WALKING = 2;
    public static final int CROSSING = 3;
    public static final int INDOOR_RUN = 4;
    public static final int OUTDOOR_RIDING = 5;
    public static final int INDOOR_RIDING = 6;

    //其中机子需要重启提醒的常驻进程
    public static final String BROCASTER_BOOT_COMPLETED ="android.intent.action.BOOT_COMPLETED" ;

    // 监听来自服务
    public static final String BROCASTER_FROM_NOTIFICATION_SERVICE = "com.huami.watch.train.ui.notification.notificationservice";


    public static final String BROCASTER_MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED" ;

    public static final String BROCASTER_MEDIA_UN_MOUNTED="android.intent.action.MEDIA_UNMOUNTED" ;

}

