package com.huami.watch.train.businessmodolex.traininit;

import android.content.Context;

import com.huami.watch.train.data.DataSourceModule;
import com.huami.watch.train.data.manager.TrainRecordManager;
import com.huami.watch.train.model.TrainRecord;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.SPUtils;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by jinliang on 16/11/9.
 */

public class TrainCenterInitPresenter implements TrainCenterInitContract.Presenter {

     private static final String TAG = TrainCenterInitPresenter.class.getSimpleName();
    private TrainCenterInitContract.View mView ;

    private TrainRecordManager trainRecordManager ;

    @Inject
    public TrainCenterInitPresenter(TrainRecordManager trainRecordManager,TrainCenterInitContract.View mView) {
        this.trainRecordManager = trainRecordManager;

        this.mView = mView;
    }

    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }



    @Override
    public void start() {

    }

    @Override
    public void startToSelectTrainPlan(Context mContext) {
        SPUtils.setTrainStatus(mContext,SPUtils.TRAIN_STATUS_HAS_NO_TASK);

        TrainRecord trainRecord =new TrainRecord();
        trainRecord.setId(1L);
        trainRecord.setCopywriter("测试");
        trainRecord.setStartData(new Date());

        trainRecordManager.insert(trainRecord);
        TrainRecord trainRecord1 = trainRecordManager.selectByPrimaryKey(1L);
        LogUtils.print(TAG, "startToSelectTrainPlan" + trainRecord1.getCopywriter());
        mView.jumpToSelectTrainPlanPage();
    }
}
