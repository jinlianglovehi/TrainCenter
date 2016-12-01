package com.huami.watch.train.ui.fragment;

/**
 * Created by jinliang on 16/11/15.
 */


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huami.watch.template.model.DayTrainPlan;
import com.huami.watch.template.model.RunRemind;
import com.huami.watch.template.model.TrainPlan;
import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseFragment;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.data.manager.DayTrainRecordManager;
import com.huami.watch.train.data.manager.TrainRecordManager;
import com.huami.watch.train.model.DayTrainRecord;
import com.huami.watch.train.model.DayTrainRecordDao;
import com.huami.watch.train.model.TrainRecord;
import com.huami.watch.train.ui.activity.TrainRecordDetailActivity;
import com.huami.watch.train.ui.event.TrainRecordFinishEvent;
import com.huami.watch.train.ui.event.UpdateTrainStatusEvent;
import com.huami.watch.train.ui.widget.CustomDialog;
import com.huami.watch.train.utils.ActivityUtils;
import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.DataUtils;
import com.huami.watch.train.utils.DrawableUtils;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;
import com.huami.watch.train.utils.SAXUtils;
import com.huami.watch.train.utils.SPUtils;
import com.huami.watch.train.utils.StringUtils;
import com.huami.watch.train.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscriber;

/**
 * 训练进行中的界面
 */
public class TrainTaskingFragment  extends BaseFragment{


     private static final String TAG = TrainTaskingFragment.class.getSimpleName();
    @BindView(R.id.train_record_title)
    TextView trainRecordTitile ; // 5公里训练

    @BindView(R.id.day_train_record_stag)
    TextView day_train_record_stag ;// 基础阶段 第 1 周 第 1天

    @BindView(R.id.day_train_record_rich_title)
    TextView dayTrainRecordRichTitle ; // 每天的训练的标题

    @BindView(R.id.day_train_record_copywrite)
    TextView dayTrainRecordCopyWrite ;// 每天训练提醒 或者是 标记完成状态 或者是距离天数

    private Unbinder unbinder ;
    private Long currentTrainRecordId  ;


    private int runremindId ;// 跑步类型

    private TrainRecord currentTrainRecord ; // 当前的训练记录

    private DayTrainRecord currentDayTrainRecord;// 当前的每天训练记录

    private TrainPlan  currentTrainPlan  ; // 当前训练计划

    private DayTrainPlan currentDayTrainPlan ;// 当前每天的训练计划

    private RunRemind currnetRunRemind ;// 提醒

    private int currentOffsetDays  = 0 ;// 当前偏移天数

    private Date  lastUpdateDate = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root  =  inflater.inflate(R.layout.fragment_train_tasking,container,false);
        unbinder =  ButterKnife.bind(this,root);
        EventBus.getDefault().register(this);
        lastUpdateDate = new Date();
        initDate();
        return root ;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Date nowDate = new Date() ;
        boolean isSameDay =  DataUtils.isSameDay(lastUpdateDate,nowDate);
        if(!isSameDay){
            initDate();
        }
    }

    private void initDate() {
        LogUtils.print(TAG, "initDate");

        RxUtils.operate(new Observable.OnSubscribe<Map<Object, Object>>() {
            @Override
            public void call(Subscriber<? super Map<Object, Object>> subscriber) {

                LogUtils.print(TAG, "intData subscriber ");
                // 进入了最复杂的流程
                currentTrainRecordId =SPUtils.getCurrentTrainRecordId(getActivity());

                LogUtils.print(TAG, "initDate  currentTrainRecordId:"+ currentTrainRecordId);
                // 获取startData
                TrainRecord trainRecord = TrainRecordManager.getInstance().selectByPrimaryKey(currentTrainRecordId);

                int offsetDays =DataUtils.getOffsetDaysFromStartData(trainRecord.getStartData(),new Date());
                LogUtils.print(TAG, "initDate  offsetDays:"+ offsetDays+ ",trainPlanId:"+trainRecord.getTrainPlanId()+",status:"+ (trainRecord.getTrainDays()==Constant.TRAIN_RECORD_DONE));

                TrainPlan trainPlan = SAXUtils.getCurrentTrainPlanFromXml(getActivity(),trainRecord.getTrainPlanId());

                LogUtils.print(TAG, "initDate  trainPlan:"+trainPlan.toString());
                // 获取的是当前的dayTrainRecord
                DayTrainRecord dayTrainRecord =DayTrainRecordManager.getInstance()
                        .getAbstractDao()
                        .queryBuilder().where(
                                DayTrainRecordDao.Properties.TrainRecordId.eq(currentTrainRecordId),
                                DayTrainRecordDao.Properties.OffsetDays.eq(offsetDays)
                        ).unique();

                // 每天的训练状态
                int dayTrainStatus =  dayTrainRecord.getDayTrainStatus();

                DayTrainPlan dayTrainPlan = SAXUtils.getCurrentDayTrainPlan(getActivity(),trainRecord.getTrainType(),offsetDays);


                LogUtils.print(TAG, "call DayTrainPlan:"+ dayTrainPlan.toString());
                Map<Object,Object> map = new HashMap<Object ,Object>();

                map.put("trainRecord",trainRecord);
                map.put("trainPlan",trainPlan);
                map.put("dayTrainRecord",dayTrainRecord);
                map.put("dayTrainPlan",dayTrainPlan);

                map.put("offfsetDays",""+offsetDays); // 偏移天数
                RunRemind  runRemind = SAXUtils.getCurrentRunmindFromXml(getActivity(),dayTrainPlan.getRunremindId());
                map.put("runRemind",runRemind);
                LogUtils.print(TAG, "call map:"+map.toString());
                subscriber.onNext(map);
                subscriber.onCompleted();
            }
        }, new IResultCallBack<Map<Object, Object>>() {
            @Override
            public void onSuccess(Map<Object, Object> stringStringMap) {
                LogUtils.print(TAG, "onSuccess:"+ stringStringMap.toString());


                currentTrainRecord = (TrainRecord) stringStringMap.get("trainRecord");

                currentTrainPlan  = (TrainPlan) stringStringMap.get("trainPlan");

                currentDayTrainRecord = (DayTrainRecord) stringStringMap.get("dayTrainRecord");

                currentDayTrainPlan = (DayTrainPlan) stringStringMap.get("dayTrainPlan");

                currnetRunRemind = (RunRemind) stringStringMap.get("runRemind");

                // Ui 设置
                trainRecordTitile.setText(currentTrainPlan.getTitle());// 设置大的分类
                //设置阶段性文案
                currentOffsetDays = Integer.parseInt(stringStringMap.get("offfsetDays").toString());
                day_train_record_stag.setText(StringUtils.getDayTrainRecordStag(
                        getActivity(),currentDayTrainPlan.getStageDesc(),
                        currentOffsetDays));


                runremindId = currentDayTrainPlan.getRunremindId();
                Drawable leftDrawable =DrawableUtils.getDrawablByRunRemindType(
                        getActivity(),runremindId);

                leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                dayTrainRecordRichTitle.setCompoundDrawables(leftDrawable, null, null, null);


                dayTrainRecordRichTitle.setText(currentDayTrainPlan.getDesc());

                // 提醒
                if(currentDayTrainRecord.getDayTrainStatus()==Constant.TRAIN_RECORD_DONE
                    && (runremindId!= Constant.SPORT_CATEGORY_REST) ){
                    dayTrainRecordCopyWrite.setText(R.string.day_train_status_done);
                }else {
                    String result  = StringUtils.getDayTrainRecordCopyWrite(getActivity(),
                            runremindId,
                            currnetRunRemind.getCopyWrite(),
                            currentDayTrainRecord.getRateStart(),
                            currentDayTrainRecord.getRateEnd());

                    LogUtils.print(TAG, " copyWrite:"+ result);
                    dayTrainRecordCopyWrite.setText(StringUtils.replaceNewLine(result));

                }
                // 自动标记历史数据的完成状态
                autoMarkHistoryRestDayTrainRecord();
            }

            @Override
            public void onFail(Map<Object, Object> stringStringMap, String msg) {
                LogUtils.print(TAG, "onFail" +msg);

            }
        });

    }




    @OnClick(R.id.txt_train_center_detail)
    public void jumpToTrainRecored(){
        ActivityUtils.startActivity(getActivity(), TrainRecordDetailActivity.class,currentTrainRecordId);
    }

    @OnClick(R.id.rl_middle_container)
    public void  jumpToOutApp(){

        // 当前运动类型暂时不支持的内容 游泳暂时不支持
        if(currentDayTrainRecord!=null && currentDayTrainRecord.getRunremindId()==Constant.SPORT_CATEGORY_SWIMMING
                &&  (currentDayTrainRecord.getDayTrainStatus()==Constant.TRAIN_RECORD_UN_DONE)
                ){
            LogUtils.print(TAG, "jumpToOutApp");
            createDialog();
        }else if(currentDayTrainRecord!=null && currentDayTrainRecord.getDayTrainStatus()==Constant.TRAIN_RECORD_UN_DONE){
            LogUtils.print(TAG, "jumpToOutApp");
            int sportType = DrawableUtils.getSportType(currentDayTrainRecord.getRunremindId());
            Utils.jumpToOutSportApp(getActivity(),sportType);
        }

    }
    /**
     * 弹出将今天的训练计划标记为完成或者是取消
     */

    private void createDialog(){

        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.dialog_day_train_plan_is_complete));
        builder.setPositiveButton(getString(R.string.day_train_plan_record_marked), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.print(TAG, "onClick confirm ");
                modifyDayTrainRecordStatus(dialog);

            }
        });

        builder.setNegativeButton(getString(R.string.cancel),
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtils.print(TAG, "onClick cancel ");
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }


    /**
     *  每天训练完成后处理训练结果
     * @param dialog
     */
    private void modifyDayTrainRecordStatus(final DialogInterface dialog ){

        LogUtils.print(TAG, "modifyDayTrainRecordStatus");
        RxUtils.operate(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {

                currentDayTrainRecord.setDayTrainStatus(Constant.TRAIN_RECORD_DONE);

                double totalLength = currentTrainRecord.getTrainTotalLength() + currentDayTrainPlan.getDistance();
                currentTrainRecord.setTrainTotalLength(totalLength);
                int totalTrainDays = currentTrainRecord.getTrainDays() + 1 ;// 添加训练天数
                currentTrainRecord.setTrainDays(totalTrainDays);

                LogUtils.print(TAG, "call totalLength:"+ totalLength +",totalTrainDays:"+ totalTrainDays);
                DayTrainRecordManager.getInstance().insertOrReplace(currentDayTrainRecord);
                LogUtils.print(TAG, "call dayTrainRecord insert success ");
                TrainRecordManager.getInstance().insertOrReplace(currentTrainRecord);
                LogUtils.print(TAG, "call trainRecord insert success ");
                subscriber.onNext(true);
                subscriber.onCompleted();


            }
        }, new IResultCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                LogUtils.print(TAG, "onSuccess");
                dialog.dismiss();
                dayTrainRecordCopyWrite.setText(R.string.day_train_status_done);
            }

            @Override
            public void onFail(Boolean aBoolean, String msg) {
                LogUtils.print(TAG, "onFail modifyDayTrainRecordStatus :"+ msg);

            }
        });

    }
    
    
    private void  autoMarkHistoryRestDayTrainRecord(){
        LogUtils.print(TAG, "autoMarkHistoryRestDayTrainRecord");
        if(currentTrainRecordId!=null && currentOffsetDays>0){

            RxUtils.operate(new Observable.OnSubscribe<Boolean>() {
                @Override
                public void call(Subscriber<? super Boolean> subscriber) {
                    Utils.autoHistoryRestDayTrainRecords(currentTrainRecordId,currentOffsetDays);

                }
            }, new IResultCallBack<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    LogUtils.print(TAG, "onSuccess autoMarkHistoryRestDayTrainRecord");

                }

                @Override
                public void onFail(Boolean aBoolean, String msg) {
                    LogUtils.print(TAG, "onFail autoMarkHistoryRestDayTrainRecord");

                }
            });

        }
        
        
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(UpdateTrainStatusEvent event) {// 修改训练记录状态事件
        initDate();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
