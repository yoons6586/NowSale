package com.kakao.util;

import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.CommonProtocol;

import org.json.JSONObject;

/**
 * Class used to hold app-specific configurations.
 * @author kevin.kang. Created on 2017. 5. 10..
 */

public class RequestConfiguration implements IConfiguration {
    private String appKey;
    private String clientSecret;
    private String keyHash;
    private String kaHeader;
    private String appVer;
    private String packageName;
    private JSONObject extras;

    public RequestConfiguration(final String appKey, final String clientSecret, String keyHash, String kaHeader, String appVer, String packageName, JSONObject extras) throws KakaoException {
        if (appKey == null || appKey.length() == 0) {
            throw new KakaoException(KakaoException.ErrorType.MISS_CONFIGURATION, String.format("need to declare %s in your AndroidManifest.xml", CommonProtocol.APP_KEY_PROPERTY));
        }
        if (keyHash == null || keyHash.length() == 0) {
            throw new KakaoException(KakaoException.ErrorType.MISS_CONFIGURATION, "Android key hash is null.");
        }
        this.appKey = appKey;
        this.clientSecret = clientSecret;
        this.keyHash = keyHash;
        this.kaHeader = kaHeader;
        this.appVer = appVer;
        this.packageName = packageName;
        this.extras = extras;
    }

    @Override
    public String getAppKey() {
        return appKey;
    }
    @Override
    public String getClientSecret() {
        return clientSecret;
    }
    @Override
    public String getKeyHash() {
        return keyHash;
    }
    @Override
    public String getKAHeader() {
        return kaHeader;
    }
    @Override
    public String getAppVer() {
        return appVer;
    }
    @Override
    public String getExtras() {
        if (extras == null) return  null;
        return extras.toString();
    }

    @Override
    public JSONObject getExtrasJson() {
        return extras;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }
}
