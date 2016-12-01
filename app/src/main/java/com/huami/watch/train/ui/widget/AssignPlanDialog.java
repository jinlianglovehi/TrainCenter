package com.huami.watch.train.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.huami.watch.train.R;


/**
 * Created by jinliang on 16/11/18.
 */

public class AssignPlanDialog extends Dialog {

    public AssignPlanDialog(Context context) {
        super(context);
    }

    public AssignPlanDialog(Context context, int theme) {
        super(context, theme);
    }


    public static class Builder {
        private Context context;
        private String trainContent;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTrainContent(String trainContent ){
            this.trainContent = trainContent;
            return this ;
        }

        public AssignPlanDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final AssignPlanDialog dialog = new AssignPlanDialog(context, R.style.base_dialog);
            View layout = inflater.inflate(R.layout.dialog_assing_plan, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            TextView textView = (TextView) layout.findViewById(R.id.train_plan_remind);
            if(trainContent!=null){
                textView.setText(trainContent);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }


}
