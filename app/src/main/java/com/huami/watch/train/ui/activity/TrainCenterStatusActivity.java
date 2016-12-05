package com.huami.watch.train.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseActivity;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.data.greendao.db.TrainRecord;
import com.huami.watch.train.data.manager.TrainRecordManager;
import com.huami.watch.train.ui.fragment.TrainNoTaskFragment;
import com.huami.watch.train.ui.fragment.TrainTaskingFragment;
import com.huami.watch.train.ui.fragment.TrainUnStartFragment;
import com.huami.watch.train.utils.DataUtils;
import com.huami.watch.train.utils.FragmentUtils;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;
import com.huami.watch.train.utils.SPUtils;
import com.huami.watch.train.utils.Utils;

import java.util.Date;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jinliang on 16/11/9.
 *
 *   第一次进入训练中心的界面
 */
public class TrainCenterStatusActivity extends BaseActivity {

 private static final String TAG = TrainCenterStatusActivity.class.getSimpleName();

    private int train_status = -1 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
//        checkTrainRecordExpire(this);
        train_status = SPUtils.getTrainStatus(this);
        initPage();
    }


    /**
     * 检查是否过期
     */
//    private void checkTrainRecordExpire(final Context mContext){
//        LogUtils.print(TAG, "checkTrainRecordExpire");
//        RxUtils.operate(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                LogUtils.print(TAG, "call");
//                int  train_status = SPUtils.getTrainStatus(mContext);
//                if(train_status==SPUtils.TRAIN_STATUS_TASKING){
//                    Long currentTrainRecordId =  SPUtils.getCurrentTrainRecordId(mContext);
//                    TrainRecord trainRecord = TrainRecordManager.getInstance().selectByPrimaryKey(currentTrainRecordId);
//                    int offset = DataUtils.getOffsetDaysFromStartData(trainRecord.getStartData(),new Date());
//
//                    boolean trainRecordExpire  = offset>(trainRecord.getTotalDays()-1) ;// 是否过期
//                    LogUtils.print(TAG, "call checkTrainRecordExpire: offsetDays:"+ offset
//                            +",trainRecodId:"+ trainRecord.getId()
//                            +",expireResult:"+trainRecordExpire
//
//                    );
//                    subscriber.onNext(trainRecordExpire);
//                }else {
//                    subscriber.onNext(false);
//                }
//                subscriber.onCompleted();
//            }
//        }, new IResultCallBack<Boolean>() {
//            @Override
//            public void onSuccess(Boolean result) {
//                LogUtils.print(TAG, "onSuccess " + result);
//                if(result){// 过期
//                        LogUtils.print(TAG, "onSuccess trainStatus:"+train_status);
//                        train_status = SPUtils.TRAIN_STATUS_HAS_NO_TASK;
//                        handlerTrainRecordExpire(mContext);
//                         initPage();
//                }else {// 不过期
//                    train_status = SPUtils.getTrainStatus(mContext);
//                    initPage();
//                }
//
//            }
//
//            @Override
//            public void onFail(Boolean b, String msg) {
//                LogUtils.print(TAG, "onFail  boolean:"+b +",msg:"+msg);
//
//            }
//        });
//
//
//    }
//
//
//    private void handlerTrainRecordExpire(final Context mContext ){
//
//        RxUtils.operate(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean result = Utils.expireAutoFinishTrainRecord(mContext);
//                subscriber.onNext(result);
//                subscriber.onCompleted();
//            }
//        }, new IResultCallBack<Boolean>() {
//            @Override
//            public void onSuccess(Boolean result) {
//                LogUtils.print(TAG, "  handlerTrainRecordExpire  onSuccess  result:"+ result);
//            }
//
//            @Override
//            public void onFail(Boolean result, String msg) {
//                LogUtils.print(TAG, "onFail  result:"+result +",msg:"+msg);
//
//            }
//        });
//
//
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.print(TAG, " trainStatus:"+ train_status);
        if(train_status>-1 && train_status!=SPUtils.getTrainStatus(this)){
            LogUtils.print(TAG, "onResume  update data ");
            train_status = SPUtils.getTrainStatus(this);
            initPage();
        }
    }

    private void initPage(){

        /**
         * 根据数据的状态进行判断展示的不同的界面
         */

        LogUtils.print(TAG, "initPage" + "trian_status:"+ train_status);
        switch (train_status){
            case SPUtils.TRAIN_STATUS_INIT: // 界面初始化
                LogUtils.print(TAG, "onCreate" ," train_center_status:init enter ");
                unTrainNotJoinFragment();
                break;
            case SPUtils.TRAIN_STATUS_TASKING:// 正在进行中训练
                LogUtils.print(TAG, "onCreate","train_center_status:"+ " tasking ");
                joinTrainAndTraining();
                break;
            case SPUtils.TRAIN_STATUS_HAS_NO_TASK:// 已经进行过训练，但是当前没有进行中的训练
                LogUtils.print(TAG, "onCreate","train_center_status:has no task ");
                joinTrainAndHasNotTasking();
                break;
        }
    }


    /**
     * 尚未参加过的训练的fragmet Init
     */
    private void unTrainNotJoinFragment(){

        TrainUnStartFragment trainUnStartFragment = new TrainUnStartFragment();
        FragmentUtils.replaceFragment(this,R.id.fragment_container,trainUnStartFragment);
    }

    /**
     * 没有训练任务在身
     */
    private void joinTrainAndHasNotTasking(){

        TrainNoTaskFragment trainNoTaskFragment = new TrainNoTaskFragment();
        FragmentUtils.replaceFragment(this,R.id.fragment_container,trainNoTaskFragment);

    }

    /**
     * 参加训练况且 有训练任务在身
     */
    private void joinTrainAndTraining(){
        TrainTaskingFragment trainTaskingFragment = new TrainTaskingFragment();
        FragmentUtils.replaceFragment(this,R.id.fragment_container,trainTaskingFragment);
    }


}
