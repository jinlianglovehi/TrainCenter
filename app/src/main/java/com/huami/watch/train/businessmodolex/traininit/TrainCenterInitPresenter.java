package com.huami.watch.train.businessmodolex.traininit;

import android.content.Context;

import com.huami.watch.template.model.TrainPlan;
import com.huami.watch.train.data.DataSourceModule;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.data.manager.TrainRecordManager;
import com.huami.watch.train.model.TrainRecord;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;
import com.huami.watch.train.utils.SPUtils;
import com.huami.watch.train.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by jinliang on 16/11/9.
 */

public class TrainCenterInitPresenter implements TrainCenterInitContract.Presenter {

    private static final String TAG = TrainCenterInitPresenter.class.getSimpleName();
    private TrainCenterInitContract.View mView ;

    private TrainRecordManager trainRecordManager ;

    private Subscription subscription ;
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
    public void onDestory() {
        RxUtils.unsubscribe(subscription);
    }

    @Override
    public void startToSelectTrainPlan(Context mContext) {
        SPUtils.setTrainStatus(mContext,SPUtils.TRAIN_STATUS_HAS_NO_TASK);

        mView.jumpToSelectTrainPlanPage();

    }


}
