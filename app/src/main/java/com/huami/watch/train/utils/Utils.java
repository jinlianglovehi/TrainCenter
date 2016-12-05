package com.huami.watch.train.utils;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;

import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.data.greendao.template.DayTrainPlan;
import com.huami.watch.train.data.greendao.template.TrainPlan;
import com.huami.watch.train.data.manager.DayTrainRecordManager;
import com.huami.watch.train.data.manager.TrainRecordManager;
import com.huami.watch.train.data.greendao.db.DayTrainRecord;
import com.huami.watch.train.data.greendao.db.DayTrainRecordDao;
import com.huami.watch.train.data.greendao.db.TrainRecord;
import com.huami.watch.train.model.UserInfo;
import com.huami.watch.train.ui.activity.TrainWidgetRemindActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jinliang on 16/11/9.
 */

public class Utils {


    /**
     *  跑步
     */
    private static final String RUNNING_PACKAGE = "com.huami.watch.sport" ;
    private static final String RUNNING_CLASS_NAME =RUNNING_PACKAGE +".ui.SportActivity" ;

    /**
     * 游泳
     */

    private static final String SWIMING_PACKAGE ="" ;

    private static final String SWIMING_CLASS_NAME ="" ;

    /**
     * 骑行
     */
    private static final String RIDE_PACKAGE ="" ;
    private static final String RIDE_CLASS_NAME ="" ;



    private static final String TAG = Utils.class.getSimpleName();
    // 心率的数据基数
    private static final int RATE_BASE_NUMBER = 220;

    /**
     * 初次将选择计划转换为训练记录
     *
     * @param trainPlan
     * @return
     */

    public static TrainRecord trainPlanToTrainRecord(TrainPlan trainPlan) {

        TrainRecord trainRecord = new TrainRecord();

        trainRecord.setTrainTitle(trainPlan.getTitle());

        trainRecord.setTrainPlanId(trainPlan.getId()); // 关联的是训练计划的id
        trainRecord.setStartData(new Date());// 设置开始时间
        // 预期中结束的天数
        trainRecord.setEndData(DataUtils.getOffsetDateFromStartDate(new Date(),trainPlan.getTotalDays()-1));

        trainRecord.setTrainStatus(Constant.TRAIN_RECORD_UN_DONE);// 设置训练状态
        trainRecord.setTrainTotalLength(0d);
        trainRecord.setLastTrainOffsetDays(0);  // 考虑需不需要？？？？

//        trainRecord.setCopywriter(trainPlan.getCopyWrite());// 设置的是训练类型的文案
        trainRecord.setTrainType(trainPlan.getType()); // 训练类型 以后的数据的获取
        trainRecord.setTrainDays(0);// 训练天数
        trainRecord.setTotalDays(trainPlan.getTotalDays());// 需要训练天数
        trainRecord.setTotalLength(trainPlan.getTotalLength());// 需要训练总长度
        return trainRecord;

    }

    /**
     * 通过trainRecorid  以及每天的训练模板 转换为  每天的训练记录
     *
     * @param trainRecordId
     * @param dayTrainPlanList
     * @return
     */
    public static List<DayTrainRecord> dayTrainPlansToRecords(Long trainRecordId, int trainType, List<DayTrainPlan> dayTrainPlanList) {
        List<DayTrainRecord> dayTrainRecordList = new ArrayList<>();
        DayTrainRecord dayTrainRecord = null;
        for (DayTrainPlan item : dayTrainPlanList) {
            dayTrainRecord = new DayTrainRecord();
            dayTrainRecord.setDayTrainStatus(Constant.TRAIN_RECORD_UN_DONE);// 设置
            dayTrainRecord.setTrainRecordId(trainRecordId);// 设置的训练记录id

            dayTrainRecord.setOffsetDays(item.getOffsetTotal());//偏移的天数

            dayTrainRecord.setRunremindId(item.getRunremindId());

            dayTrainRecord.setRateStart(item.getRateStart());
            dayTrainRecord.setRateEnd(item.getRateEnd());

            dayTrainRecord.setTrainType(trainType);

            dayTrainRecord.setDistance(item.getDistance());//当天的训练长度
            // 计算后最小的心率
//            dayTrainRecord.setRateStart((RATE_BASE_NUMBER-getUserAga())*item.getRateStart()/100);

            // 计算后的最大的心率
//            dayTrainRecord.setRateEnd((RATE_BASE_NUMBER-getUserAga())*item.getRateEnd()/100);

            dayTrainRecordList.add(dayTrainRecord);
        }
        return dayTrainRecordList;
    }


    public static void finishDayTrainRecord(Context mContext, Long trainRecordId, Long dayTrainRecordId) {

        DayTrainRecord dayTrainRecord = DayTrainRecordManager.getInstance().selectByPrimaryKey(dayTrainRecordId);

        TrainRecord trainRecord = TrainRecordManager.getInstance().selectByPrimaryKey(trainRecordId);

        dayTrainRecord.setDayTrainStatus(Constant.TRAIN_RECORD_DONE);

        trainRecord.setTrainDays(trainRecord.getTrainDays() + 1);//训练天数 +1

        DayTrainPlan dayTrainPlan = SAXUtils.getCurrentDayTrainPlan(mContext, trainRecord.getTrainType(), dayTrainRecord.getOffsetDays());
        double trainLength = dayTrainPlan.getDistance();
        trainRecord.setTrainTotalLength(trainRecord.getTrainTotalLength() + trainLength);
        if (trainRecord.getTotalDays() == (dayTrainRecord.getOffsetDays() + 1)) {// 最后一天将整个训练计划结束
            trainRecord = finishTrainRecord(mContext, trainRecord);
        }

        DayTrainRecordManager.getInstance().update(dayTrainRecord);
        TrainRecordManager.getInstance().update(trainRecord);


    }

    public static TrainRecord finishTrainRecord(Context mContext, TrainRecord trainRecord) {
        trainRecord.setTrainStatus(Constant.TRAIN_RECORD_DONE);
        trainRecord.setEndData(new Date());
        SPUtils.setCurrentTrainRecordId(mContext, -1l);
        SPUtils.setTrainStatus(mContext, SPUtils.TRAIN_STATUS_HAS_NO_TASK);

        return trainRecord;

    }


    /**
     *  用户主动标记完成一个训练计划后，将剩余没有完成的每天的训练记录标记为完成。
     * @param trainRecord   训练记录
     * @param currentOffsetDays  当前的天的偏移天数
     */
    public static void autoMarkedDayTrainRecords(TrainRecord trainRecord ,int currentOffsetDays){

        List<DayTrainRecord> dayTrainRecordList = DayTrainRecordManager.getInstance().getQueryBuilder()
                .where(DayTrainRecordDao.Properties.Id.eq(trainRecord.getId()),
                        DayTrainRecordDao.Properties.OffsetDays.ge(currentOffsetDays))
                .list();

        LogUtils.print(TAG, "autoMarkedDayTrainRecords:size:"+ dayTrainRecordList.size());
        if(dayTrainRecordList!=null && dayTrainRecordList.size()>0){
            for (DayTrainRecord dayTrainRecord: dayTrainRecordList) {
                dayTrainRecord.setDayTrainStatus(Constant.TRAIN_RECORD_UN_DONE);
            }
            DayTrainRecordManager.getInstance().updateList(dayTrainRecordList);
        }

    }


    /**
     * 自动标记以前没有标记的 休息的记录
     * @param trainRecordId
     * @param currentOffsetDays
     */
    public static boolean autoHistoryRestDayTrainRecords(Long trainRecordId,int currentOffsetDays){

        boolean result  =false ;
        List<DayTrainRecord> dayTrainRecordList =DayTrainRecordManager.getInstance().getQueryBuilder()
                .where(DayTrainRecordDao.Properties.TrainRecordId.eq(trainRecordId)
                        ,DayTrainRecordDao.Properties.OffsetDays.lt(currentOffsetDays)
                        ,DayTrainRecordDao.Properties.RunremindId.eq(Constant.SPORT_CATEGORY_REST)
                        ,DayTrainRecordDao.Properties.DayTrainStatus.eq(Constant.TRAIN_RECORD_UN_DONE))
                .list();

        LogUtils.print(TAG, "autoHistoryRestDayTrainRecords has no record ");
        if(dayTrainRecordList!=null) {
            LogUtils.print(TAG, "autoHistoryRestDayTrainRecords has record  ");
            for (DayTrainRecord dayTrainRecord : dayTrainRecordList) {
                    // 如果是休息自动标记为完成
                    dayTrainRecord.setDayTrainStatus(Constant.TRAIN_RECORD_DONE);
            }
            result  =DayTrainRecordManager.getInstance().updateList(dayTrainRecordList);
        }

        return result;


    }


    public static int getSummaryTrainDays(List<TrainRecord> trainRecordList) {

        int summaryDays = 0;
        for (TrainRecord trainRecord : trainRecordList) {
            summaryDays += trainRecord.getTrainDays();
        }
        return summaryDays;
    }

    public static int getSummaryTrainMils(List<TrainRecord> trainRecordList) {

        int summaryTrainMils = 0;
        for (TrainRecord trainRecord : trainRecordList) {
            summaryTrainMils += trainRecord.getTrainTotalLength();
        }
        return summaryTrainMils;

    }

    /**
     * 获取的是当前用户的年龄
     *
     * @return
     */
    public static int getUserAge(Context mContext) {
        SimpleDateFormat format_y = new
                SimpleDateFormat("yyyy");
        SimpleDateFormat format_M = new
                SimpleDateFormat("MM");

        int thisYear = Integer.parseInt(format_y.format(new Date()));
        int thisMonth = Integer.parseInt(format_M.format(new Date()));
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(mContext);
//        计算当前的时间
        int birthYear = userInfo.getYear();
        int birthMonth = userInfo.getMonth();
        int age = thisYear - birthYear;

        // 如果未到出生月份，则age - 1
        if (thisMonth < birthMonth) {
            age -= 1;
        }

        LogUtils.print(TAG, "getUserAge:" + age);
        return age;

    }

    public static int getRateNumber(Context mContext, int perncent) {

        return (RATE_BASE_NUMBER - getUserAge(mContext)) * perncent / 100;

    }


    public static void selectTrainPlanJumpToPage(final Context mContext, final int selectPosition, final List<TrainPlan> trainPlanList) {

        RxUtils.operate(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {

                if (trainPlanList != null) {
                    TrainPlan selectTrainPlan = trainPlanList.get(selectPosition);
//                    TrainRecord trainRecord = Utils.trainPlanToTrainRecord(selectTrainPlan);
//                    Long id = TrainRecordManager.getInstance().insert(trainRecord);
                    subscriber.onNext(selectTrainPlan.getId());
                    subscriber.onCompleted();
                }

            }
        }, new IResultCallBack<Integer>() {
            @Override
            public void onSuccess(Integer id) {
                // 通过uuid
                LogUtils.print(TAG, " insertTrainRecord onSuccess");
                ActivityUtils.startActivity(mContext, TrainWidgetRemindActivity.class, id);

            }

            @Override
            public void onFail(Integer aLong, String msg) {
                LogUtils.print(TAG, " insertTrainRecord onFail");

            }
        });

    }



    /**
     * 跳到外部的各个应用的类型
     * @param mContext
     * @param sportType
     */
    public static void jumpToOutSportApp(Context mContext,int sportType){

        Intent intent = new Intent(Intent.ACTION_VIEW);

        String packageName=null ,className=null;
        switch (sportType){
            case Constant.SPORT_TYPE_RUNNING: // 跳入跑步应用
                packageName = RUNNING_PACKAGE;
                className =RUNNING_CLASS_NAME;
                LogUtils.print(TAG, "jumpToOutSportApp -->>>>  SportActivity  " );
                intent.setClassName(packageName, className);
                intent.putExtra(Constant.EXTRA_KEY_SPORT_TYPE, Constant.RUNNING);
                intent.putExtra(Constant.EXTRA_KEY_TAG, Constant.EXTRA_VALUE_TAG_COUNT_DOWN);
                intent.putExtra(Constant.EXTRA_KEY_ENTRANCE_TYPE,Constant.EXTRA_KEY_ENTRANCE_TYPE_TRAIN_CENTER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                break;
            case Constant.SPORT_TYPE_SWIMING:// 跳入游泳应用
                packageName = SWIMING_PACKAGE;
                className = SWIMING_CLASS_NAME;
                LogUtils.print(TAG, "jumpToOutSportApp -->>>>  SwimingActivity , this method is not accessable  " );
                // TODO: 16/11/24  
                break;
            case Constant.SPORT_TYPE_RIDE:// 跳入骑行应用
                LogUtils.print(TAG, "jumpToOutSportApp -->>>>   ride    " );
                packageName = RUNNING_PACKAGE;
                className =RUNNING_CLASS_NAME;
                intent.setClassName(packageName, className);
                intent.putExtra(Constant.EXTRA_KEY_SPORT_TYPE, Constant.OUTDOOR_RIDING);// 室外骑行
                intent.putExtra(Constant.EXTRA_KEY_TAG, Constant.EXTRA_VALUE_TAG_COUNT_DOWN);
                intent.putExtra(Constant.EXTRA_KEY_ENTRANCE_TYPE,Constant.EXTRA_KEY_ENTRANCE_TYPE_TRAIN_CENTER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                break;
            case Constant.SPORT_TYPE_REST:
                // TODO: 16/11/24
                LogUtils.print(TAG, "jumpToOutSportApp -->>>>  Rest  , not to jump   " );
                break;
        }


    }


    /**
     * 过期自动结束训练记录
     * @param mContext
     */
    public  static boolean expireAutoFinishTrainRecord(Context mContext){
        boolean result = true ;
        Long currentTrainRecordId =  SPUtils.getCurrentTrainRecordId(mContext);
        TrainRecord trainRecord = TrainRecordManager.getInstance().selectByPrimaryKey(currentTrainRecordId);
        int offset = DataUtils.getOffsetDaysFromStartData(trainRecord.getStartData(),new Date());
        boolean trainRecordExpire  = offset>(trainRecord.getTotalDays()-1) ;// 是否过期
        if(trainRecordExpire){// 如果过期
            trainRecord.setTrainStatus(Constant.TRAIN_RECORD_DONE);
            result = TrainRecordManager.getInstance().update(trainRecord);
            SPUtils.setCurrentTrainRecordId(mContext, -1l);
            SPUtils.setTrainStatus(mContext, SPUtils.TRAIN_STATUS_HAS_NO_TASK);
        }
        return result;
    }


}
