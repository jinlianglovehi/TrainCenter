package com.huami.watch.train.base;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Created by jinliang on 16/11/11.
 */

@RunWith(AndroidJUnit4.class)
public class BaseTest {
    protected Context appContext ;
    @Before
    public void getAppContext(){
        appContext = InstrumentationRegistry.getTargetContext();
    }


}
