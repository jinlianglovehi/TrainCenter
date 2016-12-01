package com.huami.watch.train;

import android.app.Application;
import android.content.Intent;

import com.huami.watch.train.data.greendao.AbstractDatabaseManager;
import com.huami.watch.train.ui.notification.NotificationService;
import com.huami.watch.train.utils.LogUtils;

/**
 * Created by jinliang on 16/11/9.
 */

public class TrainApplication  extends Application{

     private static final String TAG = TrainApplication.class.getSimpleName();
//    private DataSourceComponent dataSourceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
//        dataSourceComponent = DaggerDataSourceComponent.builder().trainApplicationModule(new TrainApplicationModule(getApplicationContext()))
//                .dataSourceModule(new DataSourceModule())
//               .build();
        initNotificationService();
    }

    private void initNotificationService(){

        LogUtils.print(TAG, "initNotificationService");
        Intent serviceIntent = new Intent(this,NotificationService.class);
        startService(serviceIntent);//启动服务
    }


    private void initDB(){

        AbstractDatabaseManager.initOpenHelper(getApplicationContext());

    }

//    public DataSourceComponent getDataSourceComponent(){
//        return dataSourceComponent;
//    }
}
