package com.huami.watch.train.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huami.watch.train.R;
import com.huami.watch.train.TrainApplication;
import com.huami.watch.train.base.BaseActivity;
import com.huami.watch.train.businessmodolex.traininit.DaggerTrainCenterInitComponent;
import com.huami.watch.train.businessmodolex.traininit.TrainCenterInitFragment;
import com.huami.watch.train.businessmodolex.traininit.TrainCenterInitPresenter;
import com.huami.watch.train.businessmodolex.traininit.TrainCenterInitPresenterModule;
import com.huami.watch.train.utils.ActivityUtils;
import com.huami.watch.train.utils.LogUtils;

import javax.inject.Inject;

/**
 * Created by jinliang on 16/11/9.
 */
public class TrainCenterInitActivity extends BaseActivity {

 private static final String TAG = TrainCenterInitActivity.class.getSimpleName();
    @Inject
    TrainCenterInitPresenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        initFragmentAndPresenter();
    }

    private void initFragmentAndPresenter(){



        TrainCenterInitFragment trainCenterInitFragment =
                (TrainCenterInitFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (trainCenterInitFragment == null) {
            LogUtils.print(TAG, "initFragmentAndPresenter");
            trainCenterInitFragment =TrainCenterInitFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    trainCenterInitFragment, R.id.fragment_container);

            injectPresenter(trainCenterInitFragment);
        }

    }

    private void injectPresenter(TrainCenterInitFragment view ){

        DaggerTrainCenterInitComponent.builder().
                dataSourceComponent(((TrainApplication)getApplication()).getDataSourceComponent())
                .trainCenterInitPresenterModule(new TrainCenterInitPresenterModule(view)).build().inject(this);

    }
}
