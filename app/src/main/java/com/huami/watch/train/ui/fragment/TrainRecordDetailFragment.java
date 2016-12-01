package com.huami.watch.train.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huami.watch.template.model.TrainPlan;
import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseFragment;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.data.manager.TrainRecordManager;
import com.huami.watch.train.model.TrainRecord;
import com.huami.watch.train.ui.activity.TrainCenterStatusActivity;
import com.huami.watch.train.ui.activity.TrainWeeklyRecordDetailActivity;
import com.huami.watch.train.ui.adapter.OnItemClickListener;
import com.huami.watch.train.ui.adapter.TrainRecordDetailAdapter;
import com.huami.watch.train.ui.event.TrainRecordFinishEvent;
import com.huami.watch.train.ui.widget.CustomDialog;
import com.huami.watch.train.ui.widget.DividerItemDecoration;
import com.huami.watch.train.ui.widget.FullScrollView;
import com.huami.watch.train.ui.widget.FullyLinearLayoutManager;
import com.huami.watch.train.ui.widget.NumberTextView;
import com.huami.watch.train.utils.ActivityUtils;
import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.DataUtils;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;
import com.huami.watch.train.utils.SAXUtils;
import com.huami.watch.train.utils.SPUtils;
import com.huami.watch.train.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by jinliang on 16/11/21.
 *
 * 训练记录详情
 */

public class TrainRecordDetailFragment extends BaseFragment {

     private static final String TAG = TrainRecordDetailFragment.class.getSimpleName();

    @Bind(R.id.txt_my_train_title)
    TextView myTrainTitle ; // 我的训练title

    @Bind(R.id.txt_train_progress)
    NumberTextView trainProgress ;//训练进度

    @Bind(R.id.train_days)
    NumberTextView trainDays ;// 训练天数

    @Bind(R.id.train_mils)
    NumberTextView trainMils;// 训练公里数

    @Bind(R.id.list_train_weeklys)
    RecyclerView listTrainWeeklys ;// 训练周数

    @Bind(R.id.ll_scrollView)
    FullScrollView ll_scrollView;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar ;


    @Bind(R.id.txt_train_finish)
    TextView txtTrainFinish ;

    private List<Integer> weekNumbers ;// Adapter中的数据

    private TrainRecord currentTrainRecord ;
    private TrainPlan currentTrainPlan ;
    private Long currentTrainRecordId ;//当前的训练计划信息
    private TrainRecordDetailAdapter  trainDetailAdapter ;
    private int currentWeekly ;

    private AtomicBoolean isScrollAtom= new AtomicBoolean(true) ;


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Constant.ENTITY_CURRENT_TRAIN_RECORD_ID)) {
            currentTrainRecordId=getArguments().getLong(Constant.ENTITY_CURRENT_TRAIN_RECORD_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root  =  inflater.inflate(R.layout.fragment_train_record_detail,container,false);
        ButterKnife.bind(this,root);

        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        listTrainWeeklys.setLayoutManager(manager);
        listTrainWeeklys.addItemDecoration(new DividerItemDecoration(getActivity(),manager.getOrientation()));

        return root;
    }


    private void initData(final Long trainRecordId){

        //初始化数据信息
        RxUtils.operate(new Observable.OnSubscribe<Map<Object,Object>>() {
            @Override
            public void call(Subscriber<? super Map<Object,Object>> subscriber) {
                LogUtils.print(TAG, "call");
                //获取的是当期那的训练任务
                TrainRecord  trainRecord =  TrainRecordManager.getInstance().selectByPrimaryKey(trainRecordId);

                TrainPlan  trainPlan = SAXUtils.getCurrentTrainPlanFromXml(getActivity(),trainRecord.getTrainPlanId());
                Map<Object,Object> map = new HashMap<Object, Object>();
                map.put("trainRecord",trainRecord);
                map.put("trainPlan",trainPlan);
                subscriber.onNext(map);
                subscriber.onCompleted();

            }
        }, new IResultCallBack<Map<Object,Object>>() {
            @Override
            public void onSuccess(Map<Object,Object>  map) {
                LogUtils.print(TAG, "onSuccess:" +map.toString());

                currentTrainRecord = (TrainRecord) map.get("trainRecord");
                currentTrainPlan = (TrainPlan) map.get("trainPlan");

                myTrainTitle.setText(currentTrainPlan.getTitle());


                trainDays.setText(new StringBuilder().append(currentTrainRecord.getTrainDays()).append(Constant.SEPEAR)
                        .append(currentTrainRecord.getTotalDays()).toString());

                trainMils.setText(new StringBuffer(String.format("%.1f",currentTrainRecord.getTrainTotalLength())).append(Constant.SEPEAR)
                .append(String.format("%.1f",currentTrainRecord.getTotalLength())).toString());

                int trainPercent = currentTrainRecord.getTrainDays()*100 / currentTrainRecord.getTotalDays();

                LogUtils.print(TAG, "onSuccess:"+ trainPercent);
                trainProgress.setText(trainPercent+"%");
                progressBar.setProgress(trainPercent);

                initRecyclerViewData();

                /**
                 * 如果当前的trainRecord 已经完成 不显示标记完成。 
                 * 
                 */
                LogUtils.print(TAG, "onSuccess trainStatus:"+ currentTrainRecord.getTrainStatus());
                if(currentTrainRecord.getTrainStatus()==Constant.TRAIN_RECORD_DONE){
                    txtTrainFinish.setText(getResources().getString(R.string.train_finished));
                }else {
                    txtTrainFinish.setText(getResources().getString(R.string.train_finish));
                }

            }

            @Override
            public void onFail(Map<Object,Object> map, String msg) {
                LogUtils.print(TAG, "onFail" + msg);

            }
        });



    }


    /**
     * 初始化 周列表中的数据
     */
    private void initRecyclerViewData(){

        if(currentTrainRecord!=null){
            LogUtils.print(TAG, "initRecyclerViewData");
            currentWeekly = DataUtils.getCurrentWeekNumber(currentTrainRecord.getStartData(),new Date());
            weekNumbers =new ArrayList<>();
            for (int i = 1; i <9 ; i++) {
                weekNumbers.add(i);
            }
            trainDetailAdapter = new TrainRecordDetailAdapter(getActivity(),weekNumbers,currentWeekly);
            listTrainWeeklys.setAdapter(trainDetailAdapter);


            if(isScrollAtom.get()){
                new Handler().postDelayed(new Runnable(){

                public void run() {
                    ll_scrollView.scrollTo(0, 0);
                    ll_scrollView.fullScroll(ScrollView.FOCUS_UP);
                    isScrollAtom.set(false);
                }

            },5);}


            trainDetailAdapter.setOnItemClick(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    LogUtils.print(TAG, "onItemClick:"+ position);
                    jumpToTrainWeek(position);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });


        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.print(TAG, "onResume");
        initData(currentTrainRecordId);
    }

    /**
     *  结束此次训练计划
     */
    @OnClick(R.id.txt_train_finish)
    public void finishTrainRecord(){


        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.dialog_train_plan_is_complete));
        // 设置结束按钮
        builder.setPositiveButton(getString(R.string.train_plan_recode_finished), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.print(TAG, "onClick confirm ");
                updateDBStatus(dialog);
            }
        });

        // 直接退出
        builder.setNegativeButton(getString(R.string.cancel),
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtils.print(TAG, "onClick cancel ");
                        dialog.dismiss();
                    }
                });

        builder.create().show();


    }

    private void  updateDBStatus(final DialogInterface dialog){

        RxUtils.operate(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                currentTrainRecord.setTrainStatus(Constant.TRAIN_RECORD_DONE);// 设置训练技术
                currentTrainRecord.setEndData(new Date());
                boolean insertStatus =TrainRecordManager.getInstance().update(currentTrainRecord);// 修改数据状态
                int currentOffsetDay = DataUtils.getOffsetDaysToToday(currentTrainRecord.getStartData().getTime()/1000);
                LogUtils.print(TAG, "autoMarkOffsetDays:"+ currentOffsetDay);
                Utils.autoMarkedDayTrainRecords(currentTrainRecord,currentOffsetDay);//将剩余的没有完成的训练记录标记为完成
                subscriber.onNext(insertStatus);
                subscriber.onCompleted();
            }
        }, new IResultCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                LogUtils.print(TAG, "onSuccess updateDBStatus:"+ aBoolean);
                EventBus.getDefault().post(new TrainRecordFinishEvent());
                if(aBoolean){
                    dialog.dismiss();
                    SPUtils.setTrainStatus(getActivity(),SPUtils.TRAIN_STATUS_HAS_NO_TASK);
                    SPUtils.setCurrentTrainRecordId(getActivity(),-1L);
                    ActivityUtils.startActivity(getActivity(), TrainCenterStatusActivity.class);
                    getActivity().finish();

                }

            }

            @Override
            public void onFail(Boolean aBoolean, String msg) {
                LogUtils.print(TAG, "onFail  updateDBStatus :"+ msg);
                dialog.dismiss();

            }
        });


    }

    private void jumpToTrainWeek(int weekNumber){

        Intent intent = new Intent(getActivity(), TrainWeeklyRecordDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(Constant.ENTITY_TITLE,currentTrainPlan.getTitle());
        bundle.putInt(Constant.ENTITY_WEEK_NUMBER,weekNumber);
        bundle.putLong(Constant.ENTITY_CURRENT_TRAIN_RECORD_ID,currentTrainRecord.getId());//当前的训练记录id
        bundle.putLong(Constant.ENTITY_START_DATE,currentTrainRecord.getStartData().getTime()/1000);//记录开始时间
        bundle.putInt(Constant.ENTITY_TRAIN_RECORD_STATUS,currentTrainRecord.getTrainStatus());
        intent.putExtras(bundle);
        getActivity().startActivity(intent);



    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(TrainRecordFinishEvent event) {
        LogUtils.print(TAG, "onEvent TrainRecordFinishEvent ");
        getActivity().finish();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
