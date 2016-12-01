package com.huami.watch.template;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;


import com.huami.watch.template.model.TrainPlan;
import com.huami.watch.template.model.TrainPlanRoot;
import com.huami.watch.template.utils.JaxUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.huami.watch.xstreanlib.test", appContext.getPackageName());
    }


    @Test
    public void testTemplate(){


        TrainPlanRoot trainPlanRoot = new TrainPlanRoot();


        List<TrainPlan> trainPlanList = new ArrayList<>();

        TrainPlan trainPlan = new TrainPlan(1,"新手",1,1);
        trainPlanList.add(trainPlan);

//        JaxUtils.convertToXml(trainPlanRoot);



    }
}
