package com.huami.watch.train;

import android.content.Context;
import android.util.Log;

import dagger.Module;
import dagger.Provides;


/**
 * This is a Dagger module. We use this to pass in the Context dependency to the
 * {@link
 */
@Module
public final class TrainApplicationModule {


    private static final String TAG =TrainApplicationModule.class.getSimpleName() ;
    private final Context mContext;

    TrainApplicationModule(Context context) {
        Log.i(TAG, "ApplicationModule: ");
        mContext = context;
    }

    @Provides
    Context provideApplicastionContext() {
        Log.i(TAG, "provideContext: ");
        return mContext;
    }
}