package com.huami.watch.train.textxml;

import com.huami.watch.train.base.BaseTest;
import com.huami.watch.train.data.greendao.template.RunRemind;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.SAXUtils;

import org.junit.Test;

import java.util.List;

/**
 * Created by jinliang on 16/11/15.
 */

public class TestRunTypeWriteCopy extends BaseTest {

     private static final String TAG = TestRunTypeWriteCopy.class.getSimpleName();

    @Test
    public void testGetRunWriteCopy(){

       List<RunRemind> list =  SAXUtils.getRunRemindsFromXml(appContext);

        for (RunRemind item:list
             ) {
            LogUtils.sysPrint(TAG,item.toString());
        }


    }
}
