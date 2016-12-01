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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jinliang on 16/11/15.
 *
 *  初始化进入界面的时候
 */

public class TrainUnStartFragment  extends BaseFragment {



    @Bind(R.id.ll_train_center_init)

    RelativeLayout ll_train_center_init ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root  =  inflater.inflate(R.layout.fragment_train_init,container,false);
        ButterKnife.bind(this,root);

        initClickEvent();

        return root ;

    }

    private void initClickEvent(){

        ll_train_center_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(getActivity(), TrainPlanSelectActivity.class);
                getActivity().finish();
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
