package com.huami.watch.train.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.huami.watch.template.model.TrainPlan;
import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseFragment;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.ui.adapter.OnItemClickListener;
import com.huami.watch.train.ui.adapter.TrainPlanAdapter;
import com.huami.watch.train.ui.widget.DividerItemDecoration;
import com.huami.watch.train.ui.widget.FullScrollView;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;
import com.huami.watch.train.utils.SAXUtils;
import com.huami.watch.train.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;

/**
 * Created by jinliang on 16/11/14.
 *
 *   初始化进入界面时候的训练计划的选择界面
 *
 *   init 选择界面
 */

public class TrainPlanInitSelectFragment extends BaseFragment {

    private static final String TAG = TrainPlanInitSelectFragment.class.getSimpleName();
    private List<TrainPlan> trainPlanList;
    private TrainPlanAdapter trainPlanAdapter;

    private LinearLayoutManager manager ;
    @BindView(R.id.recyclearView)
    RecyclerView recyclerView;


    @BindView(R.id.ll_scrollView)
    FullScrollView fullScrollView;
    private Unbinder  unbinder ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root  =  inflater.inflate(R.layout.fragment_init_select,container,false);
        unbinder = ButterKnife.bind(this,root);

        trainPlanList = new ArrayList<>();
        trainPlanAdapter  = new TrainPlanAdapter(getActivity(),trainPlanList);
        manager = new LinearLayoutManager(getActivity());

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),manager.getOrientation()));
        recyclerView.setAdapter(trainPlanAdapter);
        initData();
        initClickEvent();

        return root;
    }



    private void initData() {

        // 异步操作
        RxUtils.operate(new rx.Observable.OnSubscribe<List<TrainPlan>>() {
            @Override
            public void call(Subscriber<? super List<TrainPlan>> subscriber) {
                List<TrainPlan> trainPlanList =  SAXUtils.getTrainPlanFromXml(getActivity());
                LogUtils.print(TAG, "call" + trainPlanList.size());
                subscriber.onNext(trainPlanList);
            }
        }, new IResultCallBack<List<TrainPlan>>() {
            @Override
            public void onSuccess(List<TrainPlan> trainPlanList) {
                LogUtils.print(TAG, "onSuccess");
                trainPlanAdapter.addData(trainPlanList);
                trainPlanAdapter.toggleExpand();
                trainPlanAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable(){

                    public void run() {
                        fullScrollView.scrollTo(0, 0);
                        fullScrollView.fullScroll(ScrollView.FOCUS_UP);

                    }

                },5);
            }

            @Override
            public void onFail(List<TrainPlan> trainPlen, String msg) {
                LogUtils.print(TAG, "onFail");

            }
        });

    }

    private void  initClickEvent(){

        trainPlanAdapter.setOnItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtils.print(TAG, "onItemClick");
                Utils.selectTrainPlanJumpToPage(getActivity(),position,trainPlanList);
                

            }
            @Override
            public void onItemLongClick(View view, int position) {
                LogUtils.print(TAG, "onItemLongClick");

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




}
