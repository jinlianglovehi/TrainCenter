//package com.huami.watch.train.data;
//
//import com.huami.watch.train.data.manager.DayTrainRecordManager;
//import com.huami.watch.train.data.manager.TrainRecordManager;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//
///**
// * Created by jinliang on 16/11/9.
// *
// *  操作本地 sqlite 数据库
// */
//
//@Module
//public class DataSourceModule {
//
//    public DataSourceModule() {
//
//    }
//
//    @Provides
//    TrainRecordManager providerTrainRecordManager(){
//        return new TrainRecordManager();
//    }
//
//
//    @Provides
//    DayTrainRecordManager providerDayTrainRecordManager(){
//
//        return new DayTrainRecordManager();
//
//    }
//}
