package com.huami.watch.train.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.widget.Toast;

import com.huami.watch.train.R;
import com.huami.watch.train.TrainApplication;
import com.huami.watch.train.base.BaseActivity;
import com.huami.watch.train.businessmodolex.traininit.DaggerTrainCenterInitComponent;
import com.huami.watch.train.businessmodolex.traininit.TrainCenterInitContract;
import com.huami.watch.train.businessmodolex.traininit.TrainCenterInitPresenter;
import com.huami.watch.train.businessmodolex.traininit.TrainCenterInitPresenterModule;
import com.huami.watch.train.utils.LogUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jinliang on 16/11/13.
 *
 * 测试Activity
 */

public class TestActivity extends BaseActivity implements TrainCenterInitContract.View {

     private static final String TAG = TestActivity.class.getSimpleName();
    @Inject
    TrainCenterInitPresenter mPresenter;

    private Unbinder  unBinder ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unBinder = ButterKnife.bind(this);
        initDagger();
    }
    void initDagger(){

        LogUtils.print(TAG, "initDagger");
        DaggerTrainCenterInitComponent.builder()
                .dataSourceComponent(((TrainApplication)getApplication()).getDataSourceComponent())
        .trainCenterInitPresenterModule(new TrainCenterInitPresenterModule(this)).build()
                .inject(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }

    @OnClick(R.id.sendNotification)
    public void clickBtn(){
        LogUtils.print(TAG, "clickBtn");
        mPresenter.startToSelectTrainPlan(this);

    }
    @Override
    public void jumpToSelectTrainPlanPage() {
        Toast.makeText(this,"tanchu",Toast.LENGTH_SHORT).show();
        LogUtils.print(TAG, "jumpToSelectTrainPlanPage");

    }

    @Override
    public void setPresenter(TrainCenterInitContract.Presenter presenter) {

    }
}
