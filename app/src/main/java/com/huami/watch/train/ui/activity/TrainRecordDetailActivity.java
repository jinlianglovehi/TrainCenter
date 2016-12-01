package com.huami.watch.train.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseActivity;
import com.huami.watch.train.ui.fragment.TrainPlanInitSelectFragment;
import com.huami.watch.train.ui.fragment.TrainRecordDetailFragment;
import com.huami.watch.train.utils.Constant;

/**
 * Created by jinliang on 16/11/15.
 *
 *  训练记录详情的界面
 */

public class TrainRecordDetailActivity extends BaseActivity {


    private Bundle bundle ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
         bundle = getIntent().getExtras();
        replaceTrainRecordFragment();
    }

    /**
     *  初始化选择界面
     */
    private void replaceTrainRecordFragment(){

        TrainRecordDetailFragment trainRecordDetailFragment = new TrainRecordDetailFragment();
        trainRecordDetailFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.fragment_container,trainRecordDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
