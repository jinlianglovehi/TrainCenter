package com.huami.watch.train.data.manager;

import com.huami.watch.train.data.greendao.AbstractDatabaseManager;
import com.huami.watch.train.data.greendao.db.DayTrainRecord;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by jinliang on 16/11/9.
 *  每天的训练记录管理
 */

public class DayTrainRecordManager  extends  AbstractDatabaseManager<DayTrainRecord,Long> {


    public DayTrainRecordManager() {
    }

    public static DayTrainRecordManager instance ;

    /**
     * 保持单例
     * @return
     */
    public static DayTrainRecordManager getInstance(){

        if(instance==null){

            synchronized (DayTrainRecordManager.class){
                if(instance==null){
                    instance = new DayTrainRecordManager();
                }
            }
        }
        return instance;
    }
    @Override
    public AbstractDao<DayTrainRecord, Long> getAbstractDao() {

        return daoSession.getDayTrainRecordDao();
    }
}
