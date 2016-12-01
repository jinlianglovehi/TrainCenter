package com.huami.watch.train.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseActivity;
import com.huami.watch.train.ui.event.AssgnTrainFinishEvent;
import com.huami.watch.train.ui.fragment.TrainPlanInitSelectFragment;
import com.huami.watch.train.ui.fragment.TrainPlanSelectDetailFragment;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jinliang on 16/11/14.
 *
 *  选择训练 计划界面
 */

public class TrainPlanSelectActivity extends BaseActivity {


     private static final String TAG = TrainPlanSelectActivity.class.getSimpleName();
    private Unbinder unbinder ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        int trainStatus = SPUtils.getTrainStatus(this);
        switch (trainStatus){

            case SPUtils.TRAIN_STATUS_INIT:  // 初次进入界面
                replaceInitSelectFragment();
                break;
            case SPUtils.TRAIN_STATUS_HAS_NO_TASK : // 当前没有训练在执行T
                replaceSelectDetailFragmemt();
                break;
            case SPUtils.TRAIN_STATUS_TASKING: // 当前正在执行训练
                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.print(TAG, "onStart");

    }

    /**
     *  初始化选择界面
     */
    private void replaceInitSelectFragment(){

        TrainPlanInitSelectFragment trainPlanInitSelect = new TrainPlanInitSelectFragment();
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragment_container,trainPlanInitSelect);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * 含有历史记录的选择界面
     */
    private void replaceSelectDetailFragmemt(){
        TrainPlanSelectDetailFragment trainPlanSelectDetailFragment = new TrainPlanSelectDetailFragment();
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragment_container,trainPlanSelectDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();



    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(AssgnTrainFinishEvent event) {
        LogUtils.print(TAG, "onEvent AssgnTrainFinishEvent");
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
