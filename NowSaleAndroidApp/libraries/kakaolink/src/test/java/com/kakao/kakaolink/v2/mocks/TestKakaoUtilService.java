package com.kakao.kakaolink.v2.mocks;

import android.content.Context;
import android.content.Intent;

import com.kakao.kakaolink.v2.KakaoLinkTestHelper;
import com.kakao.util.IConfiguration;
import com.kakao.util.KakaoUtilService;
import com.kakao.util.RequestConfiguration;

/**
 * @author kevin.kang. Created on 2017. 6. 13..
 */

public class TestKakaoUtilService implements KakaoUtilService {
    @Override
    public Intent resolveIntent(Context context, Intent intent, int minVersion) {
        return intent;
    }

    @Override
    public IConfiguration getAppConfiguration(Context context) {
        return KakaoLinkTestHelper.createMockRequestConfiguration();
    }
}
