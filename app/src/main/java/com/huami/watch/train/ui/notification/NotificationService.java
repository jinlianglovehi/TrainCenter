package com.huami.watch.train.ui.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.LogUtils;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jinliang on 16/11/28.
 */

public class NotificationService extends Service {


    private static final String TAG = NotificationService.class.getSimpleName();

    // 设置提醒的时间
    private final  int   dayTrainRemind_hour = 17 ;// 每日训练提醒 小时
    private final  int  dayTrainRemind_minute = 0 ;// 每日训练提醒  分钟
    private final int   REQUEST_DAY_TRAIN_REMIND  =1000 ;


    private final int  finishTrainRecord_hour  = 0 ; // 修改训练记录状态 小时
    private final int  finishTrainRecord_minute = 0 ;// 修改训练记录状态 分钟
    private final int  REQUEST_FINISH_TRAIN_RECORD = 2000 ;

    private AlarmManager manager =null ;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.print(TAG, "trainNotification onCreate");
        createOneAlarm(this,dayTrainRemind_hour,dayTrainRemind_minute,Constant.BROCASTER_FROM_DAY_TRAIN_REMIND,REQUEST_DAY_TRAIN_REMIND);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.print(TAG, " trainNotification onStartCommand");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        stopOneAlarm(this,Constant.BROCASTER_FROM_DAY_TRAIN_REMIND,REQUEST_DAY_TRAIN_REMIND);

        manager=null;

    }


    /**
     *  创建一个定时任务时钟
     */
    private void createOneAlarm(Context mContext,int hour, int minutes,String action ,int requestCode){
        if(manager!=null){
            Intent intent = new Intent(mContext, NotificationReceiver.class);
            intent.setAction(action);
            PendingIntent pi = PendingIntent.getBroadcast(mContext, requestCode, intent, 0);
            long now = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeInMillis(now);
            // 每天定时任务
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minutes);

            if(now > calendar.getTimeInMillis()){
                LogUtils.print(TAG, " now  >  tomorrow : add days   ");
                calendar.setTimeInMillis(calendar.getTimeInMillis() + 1000 * 60 * 60 * 24);
            }
            manager  = (AlarmManager)getSystemService(ALARM_SERVICE);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
            LogUtils.print(TAG, " trainNotification add alarm success ");
        }
    }

    /**
     * 停止一个时钟
     * @param mContext
     * @param action
     * @param requestCode
     */
    private void stopOneAlarm(Context mContext ,String  action ,int requestCode){

        if(manager!=null){
            Intent i = new Intent(mContext, NotificationReceiver.class);
            i.setAction(action);
            PendingIntent pi = PendingIntent.getBroadcast(this, requestCode, i, 0);
            manager.cancel(pi);
        }

    }


}
