package com.huami.watch.train.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseActivity;
import com.huami.watch.train.ui.fragment.TrainNoTaskFragment;
import com.huami.watch.train.ui.fragment.TrainTaskingFragment;
import com.huami.watch.train.ui.fragment.TrainUnStartFragment;
import com.huami.watch.train.ui.notification.NotificationService;
import com.huami.watch.train.utils.FragmentUtils;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.SPUtils;

/**
 * Created by jinliang on 16/11/9.
 *
 *   第一次进入训练中心的界面
 */
public class TrainCenterStatusActivity extends BaseActivity {

 private static final String TAG = TrainCenterStatusActivity.class.getSimpleName();
//    @Inject
//    TrainCenterInitPresenter mPresenter;
//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initPage();

    }


    private void initPage(){

        /**
         * 根据数据的状态进行判断展示的不同的界面
         */
        int train_status = SPUtils.getTrainStatus(this);
        LogUtils.print(TAG, "initPage" + "trian_status:"+ train_status);
        switch (train_status){
            case SPUtils.TRAIN_STATUS_INIT: // 界面初始化
                LogUtils.print(TAG, "onCreate" ," train_center_status:init enter ");
                unTrainNotJoinFragment();
                break;
            case SPUtils.TRAIN_STATUS_TASKING:// 正在进行中训练
                LogUtils.print(TAG, "onCreate","train_center_status:"+ " tasking ");
                joinTrainAndTraining();
                break;
            case SPUtils.TRAIN_STATUS_HAS_NO_TASK:// 已经进行过训练，但是当前没有进行中的训练
                LogUtils.print(TAG, "onCreate","train_center_status:has no task ");
                joinTrainAndHasNotTasking();
                break;
        }
    }

    /**
     * 尚未参加过的训练的fragmet Init
     */
    private void unTrainNotJoinFragment(){

        TrainUnStartFragment trainUnStartFragment = new TrainUnStartFragment();
        FragmentUtils.replaceFragment(this,R.id.fragment_container,trainUnStartFragment);
    }

    /**
     * 没有训练任务在身
     */
    private void joinTrainAndHasNotTasking(){

        TrainNoTaskFragment trainNoTaskFragment = new TrainNoTaskFragment();
        FragmentUtils.replaceFragment(this,R.id.fragment_container,trainNoTaskFragment);

    }

    /**
     * 参加训练况且 有训练任务在身
     */
    private void joinTrainAndTraining(){
        TrainTaskingFragment trainTaskingFragment = new TrainTaskingFragment();
        FragmentUtils.replaceFragment(this,R.id.fragment_container,trainTaskingFragment);
    }


}
