package com.kakao.auth.mocks;

import android.content.Context;
import android.content.Intent;

import com.kakao.util.IConfiguration;
import com.kakao.util.KakaoUtilService;

/**
 * @author kevin.kang. Created on 2017. 5. 30..
 */

public class TestKakaoUtilService implements KakaoUtilService {
    @Override
    public Intent resolveIntent(Context context, Intent intent, int minVersion) {
        return intent;
    }

    @Override
    public IConfiguration getAppConfiguration(Context context) {
        return null;
    }
}
