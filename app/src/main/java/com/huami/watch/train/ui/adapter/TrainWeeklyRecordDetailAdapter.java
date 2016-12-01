package com.huami.watch.train.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huami.watch.template.model.DayTrainPlan;
import com.huami.watch.train.R;
import com.huami.watch.train.model.DayTrainRecord;
import com.huami.watch.train.utils.Constant;
import com.huami.watch.train.utils.DataUtils;
import com.huami.watch.train.utils.SAXUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jinliang on 16/11/22.
 *  周训练记录的详情
 */

public class TrainWeeklyRecordDetailAdapter  extends RecyclerView.Adapter<TrainWeeklyRecordDetailAdapter.ViewHolder> {

     private static final String TAG = TrainWeeklyRecordDetailAdapter.class.getSimpleName();
    private List<DayTrainRecord> list ;

    private int todayOffsetDays  ;
    private Context mContext;
    private long startDate ;
    public TrainWeeklyRecordDetailAdapter(Context mContext,List<DayTrainRecord> list,long startDate ,int offsetDays) {
        this.list = list;
        this.mContext =mContext;
        this.todayOffsetDays = offsetDays;
        this.startDate =startDate;
    }


    public void updateOffetDays(int offsetDays){
        this.todayOffsetDays = offsetDays;
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClick(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_train_weekly_record_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DayTrainRecord currentItem = list.get(position);
        holder.trainDetailWeek.setText(DataUtils.getDateByOffsetDays(mContext,startDate,currentItem.getOffsetDays(),todayOffsetDays));
        DayTrainPlan  dayTrainPlan = SAXUtils.getCurrentDayTrainPlan(mContext,currentItem.getTrainType(),currentItem.getOffsetDays());
        holder.dayTrainContent.setText(dayTrainPlan.getDesc());
        if(currentItem.getOffsetDays()<todayOffsetDays){ // 以前的

            switch (currentItem.getDayTrainStatus()){
                case Constant.TRAIN_RECORD_UN_DONE:
                    holder.iconFinish.setVisibility(View.GONE);
                    holder.finishStatus.setVisibility(View.VISIBLE);
                    holder.finishStatus.setBackground(null);
                    holder.finishStatus.setText(mContext.getString(R.string.day_train_record_statis_un_finish));
                    break;
                case Constant.TRAIN_RECORD_DONE:
                    holder.iconFinish.setVisibility(View.VISIBLE);
                    holder.finishStatus.setVisibility(View.GONE);
                    break;
            }
        }else if(currentItem.getOffsetDays()==todayOffsetDays){// 今天
            switch (currentItem.getDayTrainStatus()){
                case Constant.TRAIN_RECORD_UN_DONE:
                    holder.iconFinish.setVisibility(View.GONE);
                    holder.finishStatus.setVisibility(View.VISIBLE);
                    holder.finishStatus.setText(mContext.getString(R.string.day_train_record_status_finish));
                    holder.finishStatus.setBackground(mContext.getDrawable(R.drawable.train_detail));
                    break;
                case Constant.TRAIN_RECORD_DONE:
                    holder.iconFinish.setVisibility(View.VISIBLE);
                    holder.finishStatus.setVisibility(View.GONE);
                    break;
            }


        }else if(currentItem.getOffsetDays()> todayOffsetDays){// 将来的
            holder.iconFinish.setVisibility(View.GONE);
            holder.finishStatus.setVisibility(View.GONE);

        }


        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView,position);
                    return true;
                }
            });

        }





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{


        @Bind(R.id.train_detail_week)
        TextView trainDetailWeek ;

        @Bind(R.id.day_train_content)
        TextView dayTrainContent ;

        @Bind(R.id.icon_finish)
        ImageView iconFinish ;

        @Bind(R.id.finish_status)
        TextView finishStatus;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
