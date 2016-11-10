package com.huami.watch.train.businessmodolex.traininit;

import android.content.Context;

import com.huami.watch.train.base.BasePresenter;
import com.huami.watch.train.base.BaseView;

/**
 * Created by jinliang on 16/11/9.
 */

public interface TrainCenterInitContract {


    interface Presenter extends BasePresenter {

        void startToSelectTrainPlan(Context mContext);

    }

    interface  View extends BaseView<Presenter> {

        void jumpToSelectTrainPlanPage();

    }
}
