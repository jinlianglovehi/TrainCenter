package com.huami.watch.train.ui.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.huami.watch.train.R;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.model.UserInfo;
import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;
import com.huami.watch.train.utils.SPUtils;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jinliang on 16/11/28.
 */

public class NotificationReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager ;
    private NotificationCompat.Builder builder ;

     private static final String TAG = NotificationReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.print(TAG, "onReceive  action:"+action);
        if(Constant.BROCASTER_BOOT_COMPLETED.equalsIgnoreCase(action)
//                || Constant.BROCASTER_MEDIA_MOUNTED.equalsIgnoreCase(action)
//                || Constant.BROCASTER_MEDIA_UN_MOUNTED.equalsIgnoreCase(action)
                ){// 重启机子需要重启服务
            int trainTaskStatus =SPUtils.getTrainStatus(context);
            LogUtils.print(TAG, "trainNotificationReceiver onReceive  bootComplete  trainStatus:"+ trainTaskStatus);
            if(SPUtils.TRAIN_STATUS_TASKING==trainTaskStatus){// 只有当期有任务时候才开启常驻进程
                LogUtils.print(TAG, "  start notification service process ");
                context.startService(new Intent(context,NotificationService.class));
            }
        }else if(Constant.BROCASTER_FROM_DAY_TRAIN_REMIND.equalsIgnoreCase(action)){//每日训练状态提醒
            LogUtils.print(TAG, " dayTrainRemind onReceive");
            // 处理发送今日提醒
            selectAndSendTodayNeedRemind(context);
        }else if(Constant.BROCASTER_FROM_FINISH_TRAIN_RECORD.equalsIgnoreCase(action)){
            LogUtils.print(TAG, "finish TrainRecord onReceive");
            //修改训练记录完成状态
            autoFinishExpireData(context);
        }
    }



    private void autoFinishExpireData(Context mContext){
        ContentResolver resolver =  mContext.getContentResolver();
        Uri uri = Uri.parse("content://com.huami.watch.train.ui.provider.dayTrainRecordProvider/autoDealExpiredData");
        int result =  resolver.update(uri,null,null,null);
        LogUtils.print(TAG, " autoFinishExpireData  result:"+ result);
    }

    /**
     * 发送今日提醒通知
     * @param mContext
     */
    private void sendDayTrainRecoedNotification(Context mContext){
        LogUtils.print(TAG, " trainNotification sendNotification");
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(mContext);
        builder.setContentTitle(mContext.getString(R.string.notification_title));
        builder.setContentText(mContext.getString(R.string.notificaiton_train_content));
        builder.setSmallIcon(R.mipmap.training_notify_icon);

        Bitmap background = ((BitmapDrawable) mContext.getDrawable(R.mipmap.lau_notify_icon_training_bg)).getBitmap();
        Bundle extras = new Bundle();
        extras.putBoolean("hm_vibrator", true);
        builder.setExtras(extras);
        builder.extend(new NotificationCompat.WearableExtender().setBackground(background));

        //构建 发出通知
        Notification notification = builder.build();
        notificationManager.notify(0, notification);

        LogUtils.print(TAG, " trainNotification test notification game over ");
    }

    /**
     * 查询今天是否需要提醒
     * @param mContext
     */
    private void selectAndSendTodayNeedRemind(final Context mContext){

        RxUtils.operate(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean result = isTodayNeedRemind(mContext);
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }, new IResultCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                LogUtils.print(TAG, "onSuccess  selectTodayNeedRemind:"+ aBoolean);
                if(aBoolean){
                    sendDayTrainRecoedNotification(mContext);
                }
            }

            @Override
            public void onFail(Boolean aBoolean, String msg) {
                LogUtils.print(TAG, "onFail selectTodayNeedRemind aBoolean:"+aBoolean +",msg:"+msg);

            }
        });
    }
    /**
     * 查询今天是否需要弹出通知内容
     * @param context
     * @return
     */
    private boolean isTodayNeedRemind(Context context){

        boolean  needRemind = false ;
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://com.huami.watch.train.ui.provider.dayTrainRecordProvider/getTodayTrainTask");
        // 添加运动类型
        Cursor cursor = resolver.query(uri,null,null,null,null);
        Long id  ;
        double  distance  ;
        int rateStart,rateEnd,item ;
        int dayTrainStatus =Constant.TRAIN_RECORD_DONE;
        /**
         * _id , DISTANCE ,RATE_START,RATE_END,DAY_TRAIN_STATUS
         */
        if(cursor!=null){
            Bundle bundle = cursor.getExtras();
            int sportType = bundle.getInt("sportType"); // sportType: 100 running  ，200  swiming ，300 ride  400： rest；
            String trainContent = bundle.getString("tainContent");
            LogUtils.print(TAG,"trainSportType: " + sportType+",trainContent:"+ trainContent);//
            if(sportType== Constant.SPORT_TYPE_REST){
                closeCursor(cursor);
                return true ;
            }
            if(cursor != null && cursor.moveToFirst()) {
                item = 0 ;
                id  = cursor.getLong(item++);
                distance = cursor.getDouble(item++);
                rateStart = cursor.getInt(item++);
                rateEnd =cursor.getInt(item++);
                dayTrainStatus = cursor.getInt(item++);
                LogUtils.print(TAG," id:"+id+",distance:"+distance+",rateStart:"+rateStart+",rateEnd:"+rateEnd+",dayTrainStatus:"+dayTrainStatus);
            }

            closeCursor(cursor);
            if(dayTrainStatus==Constant.TRAIN_RECORD_UN_DONE){
                return true;
            }
        }

        return needRemind;
    }

    /**
     * 关闭 cursor
     * @param cursor
     */
    private void closeCursor(Cursor cursor){
        if(cursor!=null){
            cursor.close();
        }

    }

}
