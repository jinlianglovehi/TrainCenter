package com.huami.watch.train.businessmodolex.traininit;

import com.huami.watch.train.data.DataSourceModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jinliang on 16/11/9.
 */
@Module
public class TrainCenterInitPresenterModule {

    private final TrainCenterInitContract.View mView ;


    public TrainCenterInitPresenterModule(TrainCenterInitContract.View mView) {
        this.mView = mView;
    }

    @Provides
    TrainCenterInitContract.View  provideTrainInitContractView(){

        return mView;
    }
}
