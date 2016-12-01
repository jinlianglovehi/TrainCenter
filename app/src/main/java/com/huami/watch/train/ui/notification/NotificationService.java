package com.huami.watch.train.ui.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
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
    private final int HOUR_OF_DAY = 17 ;
    private final  int MINUTE = 0 ;

    private AlarmManager manager =null ;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.print(TAG, "trainNotification onCreate");
        initAlarmManager();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.print(TAG, " trainNotification onStartCommand");
        initAlarmManager();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    /**
     * 正式任务
     */
    private void initAlarmManager(){

        LogUtils.print(TAG, "trainNotification  2 ");
        if(manager==null){

            Intent intent = new Intent(this, NotificationReceiver.class);
            intent.setAction(Constant.BROCASTER_FROM_NOTIFICATION_SERVICE);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
            long now = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeInMillis(now);
            // 每天定时任务
            calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
            calendar.set(Calendar.MINUTE, MINUTE);

            if(now > calendar.getTimeInMillis()){
                LogUtils.print(TAG, " now  >  tomorrow : add days   ");
                calendar.setTimeInMillis(calendar.getTimeInMillis() + 1000 * 60 * 60 * 24);
            }
            manager  = (AlarmManager)getSystemService(ALARM_SERVICE);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
            LogUtils.print(TAG, " trainNotification add alarm success ");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(manager!=null){
            LogUtils.print(TAG, "trainNotification onDestroy");
            //在Service结束后关闭AlarmManager
            Intent i = new Intent(this, NotificationReceiver.class);
            i.setAction(Constant.BROCASTER_FROM_NOTIFICATION_SERVICE);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
            manager.cancel(pi);
            manager =null;
        }

    }

}
