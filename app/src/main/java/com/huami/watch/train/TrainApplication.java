package com.huami.watch.train;

import android.app.Application;

import com.huami.watch.train.data.DaggerDataSourceComponent;
import com.huami.watch.train.data.DataSourceComponent;
import com.huami.watch.train.data.DataSourceModule;
import com.huami.watch.train.data.greendao.AbstractDatabaseManager;
import com.huami.watch.train.data.manager.TrainRecordManager;

/**
 * Created by jinliang on 16/11/9.
 */

public class TrainApplication  extends Application{

    private DataSourceComponent dataSourceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
        dataSourceComponent = DaggerDataSourceComponent.builder().trainApplicationModule(new TrainApplicationModule(getApplicationContext()))
                .dataSourceModule(new DataSourceModule())
               .build();
    }

    private void initDB(){

        AbstractDatabaseManager.initOpenHelper(getApplicationContext());

    }

    public DataSourceComponent getDataSourceComponent(){
        return dataSourceComponent;
    }
}
