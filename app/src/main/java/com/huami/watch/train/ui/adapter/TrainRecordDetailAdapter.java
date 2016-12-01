package com.huami.watch.train.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huami.watch.train.R;
import com.huami.watch.train.utils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jinliang on 16/11/21.
 * 训练记录详情中 从 1- 8 周
 */

public class TrainRecordDetailAdapter extends RecyclerView.Adapter<TrainRecordDetailAdapter.ViewHolder>{

     private static final String TAG = TrainRecordDetailAdapter.class.getSimpleName();
    private List<Integer> weekNumbers ;

    private Integer currentWeekNumber ;

    private Context mContextx ;
    public TrainRecordDetailAdapter(Context mContext , List<Integer> weekNumbers, int currentWeekNumber) {
        this.weekNumbers = weekNumbers;
        this.currentWeekNumber = currentWeekNumber;
        this.mContextx =mContext;

    }


    private OnItemClickListener onItemClickListener;
    public void setOnItemClick(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_train_record_detail, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Integer model = weekNumbers.get(position);
        holder.weeklyNumber.setText(String.format(mContextx.getString(R.string.train_detail_number_weekly),model));

        //设置左侧的
        Drawable leftDrawable =mContextx.getDrawable(R.mipmap.current_weekly_mark);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        if(model==currentWeekNumber){
            holder.weeklyNumber.setCompoundDrawables(leftDrawable,null,null,null);
        }else{
            holder.weeklyNumber.setCompoundDrawables(null,null,null,null);
        }
        // 设置右边的选中的标签
        if(model<= currentWeekNumber){
            holder.dayTrainStatus.setImageResource(R.mipmap.day_train_right_selected);  
        }else {
            holder.dayTrainStatus.setImageResource(R.mipmap.day_train_right_un_selected);
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
        return weekNumbers.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.txt_weekly_number)
        TextView weeklyNumber ;

        @BindView(R.id.icon_day_train_status)
        ImageView dayTrainStatus;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
