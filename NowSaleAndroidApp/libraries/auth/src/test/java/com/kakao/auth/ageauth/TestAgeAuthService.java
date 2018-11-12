package com.kakao.auth.ageauth;

import android.app.Activity;
import android.os.Bundle;

import com.kakao.auth.AuthService;

/**
 * @author kevin.kang. Created on 2017. 12. 4..
 */

public class TestAgeAuthService implements AgeAuthService {
    @Override
    public int requestAgeAuth(Bundle ageAuthParams, Activity activity, boolean useSmsReceiver) {
        return AuthService.AgeAuthStatus.SUCCESS.getValue();
    }
}
