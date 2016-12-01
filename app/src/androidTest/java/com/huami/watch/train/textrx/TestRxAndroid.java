package com.huami.watch.train.textrx;

import com.huami.watch.train.base.BaseTest;
import com.huami.watch.train.data.IResultCallBack;
import com.huami.watch.train.utils.LogUtils;
import com.huami.watch.train.utils.RxUtils;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by jinliang on 16/11/11.
 */
public class TestRxAndroid extends BaseTest{
 private static final String TAG = TestRxAndroid.class.getSimpleName();
//    @Test
    public void test(){

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    LogUtils.sysPrint(TAG,"start");
                    Thread.sleep(2000l);
                    subscriber.onNext("01");
                    LogUtils.sysPrint(TAG,"oncompleted");
                    Thread.sleep(2000l);
                    subscriber.onNext("02");
                    subscriber.onCompleted();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

                LogUtils.sysPrint(TAG,"oncompleted");

            }
            @Override
            public void onError(Throwable e) {
                LogUtils.sysPrint(TAG,"onError:"+ e.getLocalizedMessage());
            }

            @Override
            public void onNext(String s) {

                LogUtils.sysPrint(TAG,"onNext:"+ s);

            }
        });

    }

    @Test
    public void testRxAndroid(){

        RxUtils.operate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    Thread.sleep(5000l);
                    subscriber.onNext("测试");
                    Thread.sleep(5000l);
                    subscriber.onNext("02");
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, new IResultCallBack<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFail(String s, String msg) {

            }
        });

    }


}
