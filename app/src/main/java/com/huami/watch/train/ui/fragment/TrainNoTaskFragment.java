package com.huami.watch.train.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseFragment;
import com.huami.watch.train.ui.activity.TrainPlanSelectActivity;
import com.huami.watch.train.utils.ActivityUtils;
import com.huami.watch.train.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinliang on 16/11/15.
 *
 *  没有任务进行中的fragment
 */

public class TrainNoTaskFragment extends BaseFragment {
 private static final String TAG = TrainNoTaskFragment.class.getSimpleName();


    @Bind(R.id.rl_container)
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root  =  inflater.inflate(R.layout.fragment_train_no_task,container,false);
        ButterKnife.bind(this,root);
        return root ;

    }

    @OnClick(R.id.icon_train_plan)
    public void initEvent() {
        LogUtils.print(TAG, "initEvent  jump to trainPlanSelctActivity  ");
        ActivityUtils.startActivity(getActivity(), TrainPlanSelectActivity.class);
        getActivity().finish();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
