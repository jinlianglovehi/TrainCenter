package com.huami.watch.train.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseActivity;
import com.huami.watch.train.ui.event.TrainRecordFinishEvent;
import com.huami.watch.train.ui.fragment.TrainWeeklyRecordDetailFragment;
import com.huami.watch.train.utils.FragmentUtils;
import com.huami.watch.train.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by jinliang on 16/11/15.
 *  训练  周记录详情界面
 */

public class TrainWeeklyRecordDetailActivity extends BaseActivity {


 private static final String TAG = TrainWeeklyRecordDetailActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        Bundle bundle = getIntent().getExtras();
        TrainWeeklyRecordDetailFragment trainWeeklyRecordDetailFragment = new TrainWeeklyRecordDetailFragment();
        trainWeeklyRecordDetailFragment.setArguments(bundle);
        FragmentUtils.replaceFragment(this,R.id.fragment_container,trainWeeklyRecordDetailFragment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(TrainRecordFinishEvent event) {
        LogUtils.print(TAG, "onEvent TrainRecordFinishEvent  ");
        finish();

    }

}
