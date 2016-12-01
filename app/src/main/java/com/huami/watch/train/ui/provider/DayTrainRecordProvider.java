package com.huami.watch.train.ui.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huami.watch.template.model.DayTrainPlan;
import com.huami.watch.train.data.manager.DayTrainRecordManager;
import com.huami.watch.train.data.manager.TrainRecordManager;
import com.huami.watch.train.model.DayTrainRecord;
import com.huami.watch.train.model.DayTrainRecordDao;
import com.huami.watch.train.model.TrainRecord;
import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.DataUtils;
import com.huami.watch.train.utils.DrawableUtils;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.SAXUtils;
import com.huami.watch.train.utils.SPUtils;

/**
 * Created by jinliang on 16/11/24.
 *  向外提供数据接口
 */

public class DayTrainRecordProvider extends ContentProvider {

    private static final String TAG = DayTrainRecordProvider.class.getSimpleName();

    public static final String AUTHORITY ="com.huami.watch.train.ui.provider.dayTrainRecordProvider";
    public static  UriMatcher uriMatcher = null;

    private static  String  sql_select_current_task = null;// 查询当前任务

    public static final int GET_TODAY_TRAIN_TASK = 1 ;// 查询今天的训练任务
    public static final int FINISH_TODAY_TRAIN_STATUS = 2 ;// 修改今天训练的状态
    public static final int GET_TODAY_TRAIN_STATUS = 3 ;// 获取今天的任务状态


    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "getTodayTrainTask", GET_TODAY_TRAIN_TASK);
        uriMatcher.addURI(AUTHORITY,"finishTodayTrainTask/#",FINISH_TODAY_TRAIN_STATUS);
//        uriMatcher.addURI(AUTHORITY,"getTodayTrainStatus",GET_TODAY_TRAIN_STATUS);

        sql_select_current_task = " select _id , DISTANCE ,RATE_START,RATE_END,DAY_TRAIN_STATUS " +
                "from DayTrainRecord where TRAIN_RECORD_ID=?  and DAY_TRAIN_STATUS = 0 and OFFSET_DAYS=? ";

    }




    @Override
    public boolean onCreate() {
        LogUtils.print(TAG, "onCreate");
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

       int result = uriMatcher.match(uri);
        LogUtils.print(TAG, "query result:"+result+",url:"+uri.toString()+",projection:"+projection
                +",selection:"+selection+",selectionArgs:"+selectionArgs+",sortOrder:"+sortOrder);
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case GET_TODAY_TRAIN_TASK:
                LogUtils.print(TAG, "query get_today_train_task");
                int trainStaus = SPUtils.getTrainStatus(getContext());
                LogUtils.print(TAG, "query trainStaus:"+trainStaus);
                switch (trainStaus){
                    case SPUtils.TRAIN_STATUS_TASKING:
                        Long currentTrainRecordId = SPUtils.getCurrentTrainRecordId(getContext());
                        LogUtils.print(TAG, "query  currnetTrainRec");
                        TrainRecord trainRecord =TrainRecordManager.getInstance().selectByPrimaryKey(currentTrainRecordId);
                        int offsetDays =DataUtils.getOffsetDaysToToday(trainRecord.getStartData().getTime()/1000);
                        DayTrainPlan dayTrainPlan  = SAXUtils.getCurrentDayTrainPlan(getContext(),trainRecord.getTrainType(),offsetDays);
                        cursor =DayTrainRecordManager.getInstance().getAbstractDao().getDatabase().rawQuery(sql_select_current_task,
                              new String[]{String.valueOf(currentTrainRecordId),String.valueOf(offsetDays)});
                        if(dayTrainPlan!=null){
                            Bundle build = new Bundle();
                            build.putString("tainContent",dayTrainPlan.getDesc());
                            build.putInt("sportType", DrawableUtils.getSportType(dayTrainPlan.getRunremindId()));
                            ((AbstractCursor) cursor).setExtras(build);
                        }
                        break;
                }

                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int result = uriMatcher.match(uri);
        LogUtils.print(TAG, "getType  url:"+uri.toString() +",matchResult:"+result);
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        LogUtils.print(TAG, "insert" +"url:"+uri.toString() +",values:"+values.toString());
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        LogUtils.print(TAG, "delete--"+",url:"+uri.toString() +",section:"+selection +",selectionArgs:"+selectionArgs);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        LogUtils.print(TAG, "update"+",url:"+uri.toString());
        int result = -1 ;
        LogUtils.print(TAG, " matchResult:"+ uriMatcher.match(uri));
        switch (uriMatcher.match(uri)){
            case FINISH_TODAY_TRAIN_STATUS:
               result = updateDayTrainRecordStatus(uri);
                break;
        }
        return result;
    }

    private int updateDayTrainRecordStatus(Uri uri){
        Long dayTrainRecordId =  ContentUris.parseId(uri);
        LogUtils.print(TAG, " provider dayTrainRecordId:"+ dayTrainRecordId);

        /**
         * 获取的当期那任务
         */

         boolean isTodayDayTrainRecordId = isTodayTaskDayTrainRecordId(dayTrainRecordId);

        int providerResult = 404 ;
        if(isTodayDayTrainRecordId){
            ContentValues values=new ContentValues();
            values.put(String.valueOf(DayTrainRecordDao.Properties.DayTrainStatus.columnName),Constant.TRAIN_RECORD_DONE);

             providerResult =  DayTrainRecordManager.getInstance().getAbstractDao().getDatabase()
                    .update(DayTrainRecordDao.TABLENAME
                            ,values
                            ,new StringBuffer().append(DayTrainRecordDao.Properties.Id.columnName).append(" =? ").toString()
                            ,new String[]{String.valueOf(dayTrainRecordId)});

            LogUtils.print(TAG, "providerResult:"+ providerResult);
        }
        // 如果传递的不是今天的 dayTrainRecordId 训练的id
        return providerResult;
    }


    /**
     * 判断是否是今天训练记录的id
     * @param dayTrainRecordId
     * @return
     */
    private boolean isTodayTaskDayTrainRecordId(Long dayTrainRecordId){
        boolean result = false ;
        Long currentTrainRecordId = SPUtils.getCurrentTrainRecordId(getContext());
        LogUtils.print(TAG, "query  currnetTrainRec");
        TrainRecord trainRecord =TrainRecordManager.getInstance().selectByPrimaryKey(currentTrainRecordId);
        if(trainRecord!=null){
            int offsetDays =DataUtils.getOffsetDaysToToday(trainRecord.getStartData().getTime()/1000);
            DayTrainRecord dayTrainRecord =DayTrainRecordManager.getInstance().selectByPrimaryKey(dayTrainRecordId);
            if(dayTrainRecord!=null && dayTrainRecord.getOffsetDays()==offsetDays){
                result = true;
            }
        }
        return result;
    }
}