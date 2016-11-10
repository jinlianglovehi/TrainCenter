package com.huami.watch.train.businessmodolex.traininit;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseFragment;
import com.huami.watch.train.ui.TrainCenterStartActivity;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jinliang on 16/11/9.
 *
 *  初次进入训练中心的 fragment
 */

public class TrainCenterInitFragment extends BaseFragment implements  TrainCenterInitContract.View{

    private static final String TAG = TrainCenterInitFragment.class.getSimpleName();

    private TrainCenterInitContract.Presenter mPresenter ;

    private Unbinder unbinder ;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root  =  inflater.inflate(R.layout.fragment_train_init,container,false);
        unbinder = ButterKnife.bind(this,root);
        return root;
    }


    @OnClick(R.id.ll_train_center_init)
    void pageClick(){
        LogUtils.print(TAG, "pageClick");
        mPresenter.startToSelectTrainPlan(getContext());

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    public static TrainCenterInitFragment newInstance(){
        LogUtils.print(TAG, "newInstance");
        return new TrainCenterInitFragment();
    }


    @Override
    public void jumpToSelectTrainPlanPage() {
        LogUtils.print(TAG, "jumpToSelectTrainPlanPage");
        Utils.startActivity(getActivity(), TrainCenterStartActivity.class);
    }

    @Override
    public void setPresenter(TrainCenterInitContract.Presenter presenter) {

        this.mPresenter = presenter;
    }
}
