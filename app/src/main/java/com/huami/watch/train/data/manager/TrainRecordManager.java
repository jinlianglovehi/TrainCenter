package com.huami.watch.train.data.manager;


import com.huami.watch.train.data.greendao.AbstractDatabaseManager;
import com.huami.watch.train.data.greendao.db.TrainRecord;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by jinliang on 16/11/9.
 * 总的训练 summary
 */

public class TrainRecordManager  extends AbstractDatabaseManager<TrainRecord,Long> {

    public static TrainRecordManager instance ;

    /**
     * 保持单例
     * @return
     */
    public static TrainRecordManager getInstance(){

        if(instance==null){

            synchronized (TrainRecordManager.class){
                if(instance==null){
                    instance = new TrainRecordManager();
                }
            }
        }
        return instance;
    }

    public TrainRecordManager() {

    }

    public AbstractDao<TrainRecord, Long> getAbstractDao() {
        return daoSession.getTrainRecordDao();
    }
}
