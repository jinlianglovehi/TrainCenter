package com.huami.watch.train;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.huami.watch.train.base.BaseTest;
import com.huami.watch.train.model.DayTrainRecordDao;
import com.huami.watch.train.ui.provider.DayTrainRecordProvider;
import com.huami.watch.train.utils.LogUtils;

import org.junit.Test;

/**
 * Created by jinliang on 16/11/25.
 */

public class TestContentProvider extends BaseTest {

     private static final String TAG = TestContentProvider.class.getSimpleName();
//    @Test
    public void test(){
        LogUtils.print(TAG, "test");
        StringBuilder sb = new StringBuilder();
        sb.append("content://");
        sb.append(DayTrainRecordProvider.AUTHORITY).append("/dayTrainRecord");
        Uri uri = Uri.parse(sb.toString());
        Uri resultUri = ContentUris.withAppendedId(uri, 10);


        LogUtils.sysPrint(TAG,resultUri.toString());

    }

//    @Test
    public void testAdd(){

        ContentResolver resolver =  appContext.getContentResolver();
        Uri uri = Uri.parse("content://com.huami.watch.train.ui.provider.dayTrainRecordProvider/person");
//添加一条记录
        ContentValues values = new ContentValues();
        values.put("name", "linjiqin");
        values.put("age", 25);
        resolver.insert(uri, values);
    }

    /**
     *
     *  DayTrainRecordDao.Properties.Id,
     DayTrainRecordDao.Properties.Distance,
     DayTrainRecordDao.Properties.RateStart,
     DayTrainRecordDao.Properties.RateEnd,
     DayTrainRecordDao.Properties.DayTrainStatus
     */
    @Test
    public void testQuery(){

        LogUtils.sysPrint(TAG," id:"+ DayTrainRecordDao.Properties.Id.toString());
        ContentResolver resolver =  appContext.getContentResolver();
        Uri uri = Uri.parse("content://com.huami.watch.train.ui.provider.dayTrainRecordProvider/getTodayTrainTask");

        Cursor cursor = resolver.query(uri,null,null,null,null);

        Long id =-1l ;
             double   distance =-1l ;
        int rateStart = -1,rateEnd = -1 ;
        int dayTrainStatus = -1 ;
        int item = 0 ;
        if(cursor!=null){
            LogUtils.print(TAG, "testQuery  cursor has data ");
            while (cursor.moveToNext()) {
                item = 0 ;
                Bundle  bundle  =  cursor.getExtras();
                LogUtils.sysPrint(TAG,bundle.getString("test"));
                id  = cursor.getLong(item++);
                distance = cursor.getDouble(item++);
                rateStart = cursor.getInt(item++);

                rateEnd =cursor.getInt(item++);
                dayTrainStatus = cursor.getInt(item++);
                LogUtils.sysPrint(TAG," id:"+id+",distance:"+distance+",rateStart:"+rateStart+",rateEnd:"+rateEnd+",dayTrainStatus:"+dayTrainStatus);

            }

        }else {


            LogUtils.print(TAG, "testQuery  cursor has no data ");
        }
     


    }
}
