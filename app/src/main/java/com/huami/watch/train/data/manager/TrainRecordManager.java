package com.huami.watch.train.data.manager;


import com.huami.watch.train.data.greendao.AbstractDatabaseManager;
import com.huami.watch.train.model.TrainRecord;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.dao.AbstractDao;

/**
 * Created by jinliang on 16/11/9.
 *
 */

public class TrainRecordManager  extends AbstractDatabaseManager<TrainRecord,Long> {


    public TrainRecordManager() {

    }


    public AbstractDao<TrainRecord, Long> getAbstractDao() {
        return daoSession.getTrainRecordDao();
    }
}
