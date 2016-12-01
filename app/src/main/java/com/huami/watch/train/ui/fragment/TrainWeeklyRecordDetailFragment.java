package com.huami.watch.train.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.huami.watch.train.R;
import com.huami.watch.train.base.BaseFragment;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.data.manager.DayTrainRecordManager;
import com.huami.watch.train.model.DayTrainRecord;
import com.huami.watch.train.model.DayTrainRecordDao;
import com.huami.watch.train.ui.adapter.OnItemClickListener;
import com.huami.watch.train.ui.adapter.TrainWeeklyRecordDetailAdapter;
import com.huami.watch.train.ui.event.UpdateTrainStatusEvent;
import com.huami.watch.train.ui.widget.CustomDialog;
import com.huami.watch.train.ui.widget.DividerItemDecoration;
import com.huami.watch.train.ui.widget.FullScrollView;
import com.huami.watch.train.ui.widget.FullyLinearLayoutManager;
import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.DataUtils;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;
import com.huami.watch.train.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by jinliang on 16/11/22.
 *
 *  周训练记录详情界面
 */

public class TrainWeeklyRecordDetailFragment extends BaseFragment {

     private static final String TAG = TrainWeeklyRecordDetailFragment.class.getSimpleName();

    private String title ;
    private int weekNumber ;
    private Long trainRecordId ;

    private Integer trainRecordStatus ;
    private Long start_date;
    @Bind(R.id.train_record_title)
    TextView trainRecordTitle ;

    @Bind(R.id.current_week_number)
    TextView currentWeekNumber;

    @Bind(R.id.recyclear_view)
    RecyclerView recyclearView;

    @Bind(R.id.fullScrollView)
    FullScrollView fullScrollView;
    private TrainWeeklyRecordDetailAdapter trainWeeklyAdapter ;


    private List<DayTrainRecord>  list ;

    private int offsetDays ;

    private FullyLinearLayoutManager manager ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Constant.ENTITY_TITLE)) {
            title=getArguments().getString(Constant.ENTITY_TITLE);
        }
        if(getArguments().containsKey(Constant.ENTITY_WEEK_NUMBER)){
            weekNumber = getArguments().getInt(Constant.ENTITY_WEEK_NUMBER);
        }
        if(getArguments().containsKey(Constant.ENTITY_CURRENT_TRAIN_RECORD_ID)){
            trainRecordId =getArguments().getLong(Constant.ENTITY_CURRENT_TRAIN_RECORD_ID);

        }
        if(getArguments().containsKey(Constant.ENTITY_START_DATE)){
            start_date =getArguments().getLong(Constant.ENTITY_START_DATE);
        }
        if(getArguments().containsKey(Constant.ENTITY_TRAIN_RECORD_STATUS)){
            trainRecordStatus =getArguments().getInt(Constant.ENTITY_TRAIN_RECORD_STATUS);
        }
        LogUtils.print(TAG, "onCreate,title:"+title+",weekNuber:"+weekNumber+",trainRecordId:"+trainRecordId+",startDate:"+DataUtils.getDateFormat(start_date));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weekly_train_record_detail,container,false);
        ButterKnife.bind(this,root);
        trainRecordTitle.setText(title);
        currentWeekNumber.setText(getResources().getStringArray(R.array.weekdays)[weekNumber]);
         manager = new FullyLinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        recyclearView.setLayoutManager(manager);
        recyclearView.addItemDecoration(new DividerItemDecoration(getActivity(),manager.getOrientation()));
        offsetDays =DataUtils.getOffsetDaysToToday(start_date);
        initDate();
        return root;
     }

    @Override
    public void onResume() {
        super.onResume();

       int currentOffsetDays =  DataUtils.getOffsetDaysToToday(start_date);
        if(currentOffsetDays!=offsetDays && trainWeeklyAdapter!=null){
            offsetDays = currentOffsetDays;
            trainWeeklyAdapter.updateOffetDays(offsetDays);
            trainWeeklyAdapter.notifyDataSetChanged();
        }


    }

    private void initDate(){

        RxUtils.operate(new Observable.OnSubscribe<List<DayTrainRecord>>() {

            @Override
            public void call(Subscriber<? super List<DayTrainRecord>> subscriber) {
                int startOffsetDay = (weekNumber) * 7 ;
                int endOffsetDay = (weekNumber+1) * 7 -1  ;
                LogUtils.print(TAG, "call:" +"weekNumber:"+weekNumber+",startOffsetDays:"+startOffsetDay+",endOffsetDay:"+endOffsetDay);
                 List<DayTrainRecord>  dayTrainRecordList = DayTrainRecordManager.getInstance().getQueryBuilder()
                        .where(DayTrainRecordDao.Properties.TrainRecordId.eq(trainRecordId),DayTrainRecordDao.Properties.OffsetDays.ge(startOffsetDay),
                                DayTrainRecordDao.Properties.OffsetDays.le(endOffsetDay)
                        ).orderAsc(DayTrainRecordDao.Properties.OffsetDays).list();
                subscriber.onNext(dayTrainRecordList);
                subscriber.onCompleted();
            }
        }, new IResultCallBack<List<DayTrainRecord>>() {
            @Override
            public void onSuccess(List<DayTrainRecord> dayTrainRecords) {

                list = dayTrainRecords;
                // 训练记录
                trainWeeklyAdapter =new TrainWeeklyRecordDetailAdapter(getActivity(),list,start_date,offsetDays);

                recyclearView.setAdapter(trainWeeklyAdapter);

                new Handler().postDelayed(new Runnable(){

                    public void run() {
                        fullScrollView.scrollTo(0, 0);
                        fullScrollView.fullScroll(ScrollView.FOCUS_UP);

                    }

                },5);

                trainWeeklyAdapter.setOnItemClick(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                         if(list!=null&& trainRecordStatus==Constant.TRAIN_RECORD_UN_DONE){
                             DayTrainRecord dayTrainRecord = list.get(position);
                             if(dayTrainRecord.getOffsetDays()==offsetDays && dayTrainRecord.getDayTrainStatus()==Constant.TRAIN_RECORD_UN_DONE){
                                 showMarkDialog(position,list.get(position).getId());
                             }
                         }else if(list!=null && trainRecordStatus==Constant.TRAIN_RECORD_DONE){
                             LogUtils.print(TAG, "onItemClick not today ");
                             Toast.makeText(getActivity(),getString(R.string.this_train_record_finish),Toast.LENGTH_SHORT).show();
                         }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

            }

            @Override
            public void onFail(List<DayTrainRecord> dayTrainRecords, String msg) {

            }
        });


    }


    /**
     * 弹出dialog 将 今天的训练记录记性标记
     */
    private void showMarkDialog(final int position , final Long dayTrainRecordId){

        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.dialog_day_train_plan_is_complete));
        // 设置结束按钮
        builder.setPositiveButton(getString(R.string.day_train_plan_record_marked), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.print(TAG, "onClick confirm ");
                updateTodayTrainRecordFinish(dialog,dayTrainRecordId,position);
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

    private void updateTodayTrainRecordFinish(final DialogInterface dialog, final Long dayTrainRecordId, final int position){
        
        RxUtils.operate(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                Utils.finishDayTrainRecord(getActivity(),trainRecordId,dayTrainRecordId);
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }, new IResultCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if(aBoolean){
                    list.get(position).setDayTrainStatus(Constant.TRAIN_RECORD_DONE);
                    trainWeeklyAdapter.notifyDataSetChanged();
                    EventBus.getDefault().post(new UpdateTrainStatusEvent());
                    dialog.dismiss();
                }
                
            }

            @Override
            public void onFail(Boolean aBoolean, String msg) {

            }
        });
        

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
