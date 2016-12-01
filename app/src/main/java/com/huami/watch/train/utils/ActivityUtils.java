/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huami.watch.train.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    public static final String TRAIN_RECOTD_ID = "train_record_id" ;// 训练记录id

    public static final String TRAIN_PLAN_ID = "train_plan_id" ;// 训练计划id

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


    /**
     * 进行界面跳转 含有参数
     * @param mContext
     * @param className
     */
    public static void startActivity(Context mContext , Class className,Integer trainPlanId){
        Intent intent = new Intent(mContext,className);
        intent.putExtra(TRAIN_PLAN_ID,trainPlanId);
        mContext.startActivity(intent);

    }


    public static void startActivity(Context mContext , Class className,Long  trainRecordId){
        Intent intent = new Intent(mContext,className);
        Bundle bundle = new Bundle();
        bundle.putLong(Constant.ENTITY_CURRENT_TRAIN_RECORD_ID,trainRecordId);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 进行界面跳转 不含有参数
     * @param mContext
     * @param className
     */
    public static void startActivity(Context mContext , Class className){
        Intent intent = new Intent(mContext,className);
        mContext.startActivity(intent);

    }

}
