package com.huami.watch.train.ui.listviewadapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huami.watch.train.R;
import com.huami.watch.train.data.greendao.template.TrainPlan;
import com.huami.watch.train.ui.adapter.OnItemClickListener;
import com.huami.watch.train.ui.listviewadapter.base.SuperBaseAdapter;
import com.huami.watch.train.ui.listviewadapter.base.SuperItenClickListener;
import com.huami.watch.train.utils.DrawableUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jinliang on 16/12/6.
 */

public class TrainPlanListAdapter extends SuperBaseAdapter<TrainPlan> {



    public TrainPlanListAdapter(Activity context, List<TrainPlan> list) {
        super(context,list);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClick(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public int getItemLayoutResId() {
        return R.layout.adapter_train_plan;
    }

    @Override
    public Object getViewHolder(View rootView) {
        //返回自己的 ViewHolder对象
        return new ViewHolder(rootView);
    }

    @Override
    public void setItemData(final int position, Object viewHolder) {

        final TrainPlan trainPlan  = dataList.get(position);
        //将holder 转为自己holder
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.train_plan.setText(trainPlan.getTitle());

        holder.imageView.setImageDrawable(context.getResources().getDrawable(DrawableUtils.getTrainPlanResIdByType(trainPlan.getType())));
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

    /**
     *  ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View itemView;

        @Bind(R.id.icon_train_plan)
        ImageView imageView;
        @Bind(R.id.txt_train_plan)
        TextView train_plan;

        public ViewHolder(View root) {
            this.itemView = root;
            ButterKnife.bind(this,root);

        }
    }
}
