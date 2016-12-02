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

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseFragment;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.data.greendao.template.TrainPlan;
import com.huami.watch.train.data.manager.TrainRecordManager;
import com.huami.watch.train.data.greendao.db.TrainRecord;
import com.huami.watch.train.data.greendao.db.TrainRecordDao;
import com.huami.watch.train.ui.activity.TrainRecordDetailActivity;
import com.huami.watch.train.ui.adapter.OnItemClickListener;
import com.huami.watch.train.ui.adapter.TrainPlanAdapter;
import com.huami.watch.train.ui.adapter.TrainSelectDetailAdapter;
import com.huami.watch.train.ui.widget.DividerItemDecoration;
import com.huami.watch.train.ui.widget.FullScrollView;
import com.huami.watch.train.ui.widget.FullyLinearLayoutManager;
import com.huami.watch.train.ui.widget.NumberTextView;
import com.huami.watch.train.utils.ActivityUtils;
import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;
import com.huami.watch.train.utils.SAXUtils;
import com.huami.watch.train.utils.ScrollUtils;
import com.huami.watch.train.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by jinliang on 16/11/14.
 *
 *  训练记录的选择和历史记录的界面Fragment
 */

public class TrainPlanSelectDetailFragment extends BaseFragment {

     private static final String TAG = TrainPlanSelectDetailFragment.class.getSimpleName();


    @Bind(R.id.ll_scrollView)
    FullScrollView fullScrollView ;

    @Bind(R.id.number_train_days)
    NumberTextView numberTrainDays ;
    @Bind(R.id.number_train_mils)
    NumberTextView numberTrainMils ;

    @Bind(R.id.list_train_plans)
    RecyclerView recyclerView_plans ;

    @Bind(R.id.list_history_records)
    RecyclerView recyclerView_history ;

    private List<TrainRecord>  trainRecordList ;
    private List<TrainPlan> trainPlanList ;

    private TrainPlanAdapter trainPlanAdapter ;
    TrainSelectDetailAdapter trainSelectDetailAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root  =  inflater.inflate(R.layout.fragment_train_plan_select_detail,container,false);
        ButterKnife.bind(this,root);

        ScrollUtils.setHMScrollStyle(fullScrollView);
        //历史记录
        FullyLinearLayoutManager history_manager = new FullyLinearLayoutManager(getActivity());
        history_manager.setSmoothScrollbarEnabled(true);
        history_manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_history.setLayoutManager(history_manager);
        recyclerView_history.addItemDecoration(new DividerItemDecoration(getActivity(),history_manager.getOrientation()));

        // 模板选择
        FullyLinearLayoutManager plan_manager = new FullyLinearLayoutManager(getActivity());
        plan_manager.setOrientation(LinearLayoutManager.VERTICAL);
        plan_manager.setSmoothScrollbarEnabled(true);
        recyclerView_plans.setLayoutManager(plan_manager);
        recyclerView_plans.addItemDecoration(new DividerItemDecoration(getActivity(),plan_manager.getOrientation()));
        initHistoryTrainDate();
        return  root;

    }

    @OnClick(R.id.start_new_train_plan)
    public void startNewTrainPlan(){

        if(trainPlanAdapter!=null){
            trainPlanAdapter.toggleExpand();
            trainPlanAdapter.notifyDataSetChanged();

        }

    }



    private void initHistoryTrainDate(){

        RxUtils.operate(new Observable.OnSubscribe<Map<Object, Object>>() {

            public void call(Subscriber<? super Map<Object, Object>> subscriber) {

                List<TrainRecord>  trainRecordList = TrainRecordManager.getInstance().getQueryBuilder()
                        .where(TrainRecordDao.Properties.TrainStatus.eq(Constant.TRAIN_RECORD_DONE))
                        .orderDesc(TrainRecordDao.Properties.EndData).orderDesc(TrainRecordDao.Properties.StartData)
                        .list();
                // 总的训练统计
                int summaryTrainDays = Utils.getSummaryTrainDays(trainRecordList);
                int summaryTrainMils  =Utils.getSummaryTrainMils(trainRecordList);

                List<TrainPlan> trainPlans= SAXUtils.getTrainPlanFromXml(getActivity());
                Map<Object,Object> map =new HashMap<Object, Object>();
                map.put("trainRecordList",trainRecordList);
                map.put("summaryTrainDays",summaryTrainDays);
                map.put("summaryTrainMils",summaryTrainMils);
                map.put("trainPlans",trainPlans);
                subscriber.onNext(map);
                subscriber.onCompleted();

            }
        }, new IResultCallBack<Map<Object, Object>>() {
            @Override
            public void onSuccess(Map<Object, Object> objectObjectMap) {
                LogUtils.print(TAG, "onSuccess:"+objectObjectMap.toString());
                numberTrainDays.setText(objectObjectMap.get("summaryTrainDays").toString());
                LogUtils.print(TAG, "numberTrainDays");
                numberTrainMils.setText(objectObjectMap.get("summaryTrainMils").toString());
                LogUtils.print(TAG, "numberTrainMils");

                trainRecordList = (List<TrainRecord>) objectObjectMap.get("trainRecordList");
                LogUtils.print(TAG, "trainRecordList size:"+ trainRecordList.size());
                trainSelectDetailAdapter = new TrainSelectDetailAdapter(getActivity(),trainRecordList);
                recyclerView_history.setAdapter(trainSelectDetailAdapter);

                trainPlanList = (List<TrainPlan>) objectObjectMap.get("trainPlans");

                trainPlanAdapter =new TrainPlanAdapter(getActivity(),trainPlanList);
                recyclerView_plans.setAdapter(trainPlanAdapter);

                new Handler().postDelayed(new Runnable(){

                    public void run() {
                        fullScrollView.scrollTo(0, 0);
                        fullScrollView.fullScroll(ScrollView.FOCUS_UP);

                    }

                },5);

                // 处理点击事件event
                selectTrainPlanEvent();
//                selectHistoryTrainRecordEvent();
            }
            @Override
            public void onFail(Map<Object, Object> objectObjectMap, String msg) {
                LogUtils.print(TAG, "onFail" + msg);

            }
        });


    }

    private void selectTrainPlanEvent(){

        if(trainPlanAdapter!=null){

            trainPlanAdapter.setOnItemClick(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    LogUtils.print(TAG, "onItemClick selectTrainPlan:"+ position);
                    Utils.selectTrainPlanJumpToPage(getActivity(),position,trainPlanList);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

        }

    }

    private void selectHistoryTrainRecordEvent(){

        if(trainSelectDetailAdapter!=null){
            trainSelectDetailAdapter.setOnItemClick(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // 选择训练记录
                    TrainRecord selectTrainRecord = trainRecordList.get(position);
                    ActivityUtils.startActivity(getActivity(),TrainRecordDetailActivity.class,selectTrainRecord.getId());

                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
