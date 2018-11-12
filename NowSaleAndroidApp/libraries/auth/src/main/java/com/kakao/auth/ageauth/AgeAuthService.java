package com.kakao.auth.ageauth;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author kevin.kang. Created on 2017. 11. 28..
 */

public interface AgeAuthService {
    int requestAgeAuth(final Bundle ageAuthParams, final Activity activity, final boolean useSmsReceiver);
}
