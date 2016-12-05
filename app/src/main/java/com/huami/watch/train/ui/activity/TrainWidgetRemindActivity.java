package com.huami.watch.train.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseActivity;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.data.greendao.template.DayTrainPlan;
import com.huami.watch.train.data.greendao.template.TrainPlan;
import com.huami.watch.train.data.manager.DayTrainRecordManager;
import com.huami.watch.train.data.manager.TrainRecordManager;
import com.huami.watch.train.data.greendao.db.DayTrainRecord;
import com.huami.watch.train.data.greendao.db.TrainRecord;
import com.huami.watch.train.ui.event.AssgnTrainFinishEvent;
import com.huami.watch.train.ui.widget.AssignPlanDialog;
import com.huami.watch.train.utils.ActivityUtils;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;
import com.huami.watch.train.utils.SAXUtils;
import com.huami.watch.train.utils.SPUtils;
import com.huami.watch.train.utils.StringUtils;
import com.huami.watch.train.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by jinliang on 16/11/15.
 *
 *  选择训练计划后的提醒界面
 */

public class TrainWidgetRemindActivity extends BaseActivity {


 private static final String TAG = TrainWidgetRemindActivity.class.getSimpleName();



    @Bind(R.id.train_record_title)
    TextView train_record_title ;  // 设置的是选择训练的标题

    @Bind(R.id.train_plan_remind)
    TextView train_plan_remind ;

    private Long trainRecordId ; // 当前进行训练记录的id

    private int trainPlanId ;
    private TrainPlan currentTrainPlan ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_remind);
        ButterKnife.bind(this);



    }



    @Override
    protected void onResume() {
        super.onResume();
        trainPlanId = getIntent().getIntExtra(ActivityUtils.TRAIN_PLAN_ID,-1);
        LogUtils.print(TAG, "onCreate trainRecordId:"+ trainPlanId);


        initTrainRecordData(trainPlanId);
    }

    private void initTrainRecordData(final  int trainPlanId){

        RxUtils.operate(
                new Observable.OnSubscribe<TrainPlan>() {
                    @Override
                    public void call(Subscriber<? super TrainPlan> subscriber) {
                        TrainPlan trainPlan = SAXUtils.getCurrentTrainPlanFromXml(getApplication(),trainPlanId);

                        subscriber.onNext(trainPlan);
                        subscriber.onCompleted();
                    }
                }, new IResultCallBack<TrainPlan>() {

                    @Override
                    public void onSuccess(TrainPlan trainPlan) {
                        LogUtils.print(TAG, "onSuccess:" + trainPlan.getTitle()  + "remind:"+ trainPlan.getCopyWrite());
                        currentTrainPlan = trainPlan;
                        train_record_title.setText(trainPlan.getTitle());
                        train_plan_remind.setText(StringUtils.replaceNewLine(trainPlan.getCopyWrite()));
                    }

                    @Override
                    public void onFail(TrainPlan trainPlan, String msg) {
                        LogUtils.print(TAG, "onFail");

                    }
                }
        );


    }


    private AssignPlanDialog assignPlanDialog ;
    /**
     * 指定计划
     */
    @OnClick(R.id.txt_start_train)
    public void  clickToAssignPlan(){


        // 弹出diaolog
        AssignPlanDialog.Builder builder = new AssignPlanDialog.Builder(TrainWidgetRemindActivity.this);
        builder.setTrainContent(StringUtils.replaceNewLine(String.format(getString(R.string.assing_train_plan),currentTrainPlan.getTitle())));
        assignPlanDialog = builder.create();

// TODO: 16/11/16  动画的过度界面
        LogUtils.print(TAG, "click _to_start_train");
        RxUtils.operate(
                new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {

                        if(currentTrainPlan!=null){
                            TrainRecord conver_trainRecord = Utils.trainPlanToTrainRecord(currentTrainPlan);
                            Long trainRecordId = TrainRecordManager.getInstance().insert(conver_trainRecord);

                            //
                            SPUtils.setCurrentTrainRecordId(getApplication(),trainRecordId);
                            SPUtils.setTrainStatus(getApplication(),SPUtils.TRAIN_STATUS_TASKING);
                            //
                            TrainRecord trainRecord = TrainRecordManager.getInstance().selectByPrimaryKey(trainRecordId);
                            LogUtils.print(TAG, "call clickToAssignPlan trainRecord:"+ trainRecord.toString());
                            int trainType = trainRecord.getTrainType();
                            LogUtils.print(TAG, "call clickToAssignPla trainType:"+trainType);
                            List<DayTrainPlan> dayTrainPlanList = SAXUtils.getDayTrainPlansFromXml(getApplication(),trainType);

                            LogUtils.print(TAG, "call dayTrainPlanListSize:"+dayTrainPlanList.size());
                            List<DayTrainRecord>  dayTrainRecordList =Utils.dayTrainPlansToRecords(trainRecord.getId(),trainType,dayTrainPlanList);
                            LogUtils.print(TAG, "call dayTrainRecordListSize:"+dayTrainPlanList.size());
                            boolean status = DayTrainRecordManager.getInstance().insertList(dayTrainRecordList);

                            LogUtils.print(TAG, "call clickToAssignPlan inser status:"+ status);
                            subscriber.onNext(status);
                        }else{
                            subscriber.onNext(false);
                        }
                        subscriber.onCompleted();
                    }
                }, new IResultCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if(aBoolean){
                            EventBus.getDefault().post(new AssgnTrainFinishEvent());
                            assignPlanDialog.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    LogUtils.print(TAG, "run");
                                    try {
                                        LogUtils.print(TAG, "run");
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    LogUtils.print(TAG, "junmpToTrainCenterStatusAcitivity");
                                    Intent intent = new Intent(getApplicationContext(),TrainCenterStatusActivity.class) ;
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                            }).start();

                        }

                    }

                    @Override
                    public void onFail(Boolean aBoolean, String msg) {
                        LogUtils.print(TAG, "onFail clickToAssignPla :" +aBoolean +"-"+ msg);

                    }
                }
        );


    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(assignPlanDialog!=null){
            assignPlanDialog.dismiss();
        }

        ButterKnife.unbind(this);
    }
}
