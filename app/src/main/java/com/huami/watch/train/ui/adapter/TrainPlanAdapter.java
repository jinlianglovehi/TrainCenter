package com.huami.watch.train.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huami.watch.train.R;
import com.huami.watch.train.data.greendao.template.TrainPlan;
import com.huami.watch.train.utils.DrawableUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jinliang on 16/11/14.
 *
 * 训练计划Adapter  （其中的训练计划的是）
 */

public class TrainPlanAdapter  extends RecyclerView.Adapter<TrainPlanAdapter.ViewHolder>{
 private static final String TAG = TrainPlanAdapter.class.getSimpleName();

    private List<TrainPlan> list ;
    private Context mContext ;

    private boolean isExpand =false ;

   public void  toggleExpand(){
       isExpand= !isExpand;
   }


    public TrainPlanAdapter(Context mContext , List<TrainPlan> list) {
        this.list = list;
        this.mContext = mContext;
    }

    private OnItemClickListener  onItemClickListener;
    public void setOnItemClick(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void addData(List<TrainPlan> trainPlanList){
        this.list.addAll(trainPlanList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.adapter_train_plan, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TrainPlan trainPlan = list.get(position);
        holder.train_plan.setText(trainPlan.getTitle());

//        mContext.getResources().getDrawable(DrawableUtils.getTrainPlanResIdByType(trainPlan.getType()));
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(DrawableUtils.getTrainPlanResIdByType(trainPlan.getType())));
//        Glide.with(mContext).load(DrawableUtils.getTrainPlanResIdByType(trainPlan.getType()))
//                .into(holder.imageView);

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
        if(isExpand){
            return list.size();
        }else{
            return 0 ;
        }
    }

    class ViewHolder extends  RecyclerView.ViewHolder{


       @Bind(R.id.icon_train_plan)
       ImageView imageView;
        @Bind(R.id.txt_train_plan)
        TextView train_plan;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

//    根据数据的分类获取不同的drawable


}
