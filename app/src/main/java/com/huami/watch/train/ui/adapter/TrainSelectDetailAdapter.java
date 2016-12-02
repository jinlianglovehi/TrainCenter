package com.huami.watch.train.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huami.watch.train.R;
import com.huami.watch.train.data.greendao.db.TrainRecord;
import com.huami.watch.train.data.greendao.template.TrainPlan;
import com.huami.watch.train.ui.widget.CircleProgressView;
import com.huami.watch.train.utils.SAXUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jinliang on 16/11/23.
 */

public class TrainSelectDetailAdapter  extends RecyclerView.Adapter<TrainSelectDetailAdapter.ViewHolder> {

     private static final String TAG = TrainSelectDetailAdapter.class.getSimpleName();
    private List<TrainRecord> trainRecordList ;
    private Context mContext ;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public TrainSelectDetailAdapter( Context mContext,List<TrainRecord> trainRecordList) {
        this.trainRecordList = trainRecordList;
        this.mContext = mContext;
    }

    private OnItemClickListener onItemClickListener;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_history_train_record_item, parent, false);
        return new ViewHolder(itemView);
    }
    public void setOnItemClick(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        TrainRecord currentTrainRecord =  trainRecordList.get(position);

        int percent = currentTrainRecord.getTrainDays()*100/currentTrainRecord.getTotalDays();
        holder.circleProgressView.setProgress(percent);
        holder.txtProgressLevel.setText(percent+"%");
        TrainPlan trainPlan = SAXUtils.getCurrentTrainPlanFromXml(mContext,currentTrainRecord.getTrainPlanId());
        holder.trainRecordTitle.setText(trainPlan.getTitle());

        holder.txtStartDate.setText(String.format(mContext.getString(R.string.hidtory_train_start_date)
        ,simpleDateFormat.format(currentTrainRecord.getStartData())
        ));

        holder.txtEndDate.setText(String.format(mContext.getString(R.string.hidtory_train_end_date),
                simpleDateFormat.format(currentTrainRecord.getEndData())
                ));

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
        return trainRecordList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        @Bind(R.id.train_record_title)
        TextView trainRecordTitle ;

        @Bind(R.id.txt_start_date)
        TextView txtStartDate ;

        @Bind(R.id.txt_end_date)
        TextView txtEndDate ;

        @Bind(R.id.circle_progress_view)
        CircleProgressView circleProgressView;

        @Bind(R.id.txt_progress_level)
        TextView txtProgressLevel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
