package com.huami.watch.train.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jinliang on 16/11/21.
 *
 * 训练中心中的数字的样式
 */

public class NumberTextView extends TextView {

    static Typeface tf = null;

    public NumberTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if(tf == null){

            tf = Typeface.createFromAsset(getContext().getAssets(),
                    "huamifont.ttf");

        }
        setTypeface(tf);
    }

}
