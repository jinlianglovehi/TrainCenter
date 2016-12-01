package com.huami.watch.train.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.huami.watch.train.model.UserInfo;

/**
 * Created by jinliang on 16/11/18.
 */

public class UserInfoManager {

    private static final String USER_INFO_CONTENT_URI = "content://com.huami.watch.setup.usersettings";
    private static final Uri USER_INFO_URI = Uri.parse(USER_INFO_CONTENT_URI);
    private static final String CONTENT_KEY_GENDER = "gender";
    private static final String CONTENT_KEY_YEAR = "year";
    private static final String CONTENT_KEY_MONTH = "month";
    private static final String CONTENT_KEY_HEIGHT = "height";
    private static final String CONTENT_KEY_WEIGHT = "weight";
    private static UserInfoManager sInstance = null;
    private static final String TAG = "HmUserInfoManager";
    private UserInfo mUserInfo = null;

    private UserInfoManager() {

    }

    public static UserInfoManager getInstance() {
        synchronized (UserInfoManager.class) {
            if (sInstance == null) {
                sInstance = new UserInfoManager();
            }
        }
        return sInstance;
    }

    public UserInfo getUserInfo(Context context) {
        if (mUserInfo == null) {
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = null;
            try {
                cursor = cr.query(USER_INFO_URI, null, null, null, null);
                UserInfo userInfo = new UserInfo();
                if (cursor != null && cursor.moveToFirst()) {
                    userInfo.setGender(cursor.getInt(cursor.getColumnIndex(CONTENT_KEY_GENDER)));
                    userInfo.setGender(cursor.getInt(cursor.getColumnIndex(CONTENT_KEY_YEAR)));
                    userInfo.setMonth(cursor.getInt(cursor.getColumnIndex(CONTENT_KEY_MONTH)));
                    userInfo.setWeight(cursor.getFloat(cursor.getColumnIndex(CONTENT_KEY_WEIGHT)));
                    userInfo.setHeight(cursor.getInt(cursor.getColumnIndex(CONTENT_KEY_HEIGHT)));
                }
                mUserInfo = userInfo;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        // TODO: 16/12/1 上线后需要修改
        mUserInfo = new UserInfo();
        mUserInfo.setYear(1989);
        mUserInfo.setMonth(04);
        return mUserInfo;

    }

}
