package com.huami.watch.train.testutils;

import android.nfc.Tag;

import com.huami.watch.train.base.BaseTest;
import com.huami.watch.train.utils.DataUtils;
import com.huami.watch.train.utils.LogUtils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jinliang on 16/12/2.
 */

public class TestDateUtils extends BaseTest{

     private static final String TAG = TestDateUtils.class.getSimpleName();
    @Test
    public void test(){

        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy/MM/dd");

        Date date = DataUtils.getOffsetDateFromStartDate(new Date(),1);

       String result =  simpleDateFormat.format(date);
        LogUtils.sysPrint(TAG,result);
    }
}
