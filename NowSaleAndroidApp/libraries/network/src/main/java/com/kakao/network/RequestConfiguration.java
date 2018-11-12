package com.kakao.network;

import android.content.Context;

import com.kakao.util.IConfiguration;
import com.kakao.util.helper.CommonProtocol;
import com.kakao.util.helper.SystemInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * API request를 보내기 위해 필요한 다양한 값들을 관리하는 클래스.
 *
 * @deprecated Use {@link com.kakao.util.RequestConfiguration} instead.
 * Created by kevin.kang on 2016. 11. 29..
 */

@Deprecated
public class RequestConfiguration implements IConfiguration {
    private IConfiguration appConfig;
    private String extras;

    private static RequestConfiguration configuration;

    public RequestConfiguration(IConfiguration appConfig, String kaHeader, String extras) {
        this.appConfig = appConfig;
        this.extras = extras;
    }

    public static RequestConfiguration createRequestConfiguration(final Context context) {
        if (configuration != null)
            return configuration;

        IConfiguration appConfig = IConfiguration.Factory.createConfiguration(context);
        SystemInfo.initialize(context);
        String kaHeader = SystemInfo.getKAHeader();

        String extrasString;
        JSONObject extras = new JSONObject();
        try {
            extras.put(CommonProtocol.APP_PACKAGE, context.getPackageName());
            extras.put(CommonProtocol.KA_HEADER_KEY, kaHeader);
            extras.put(CommonProtocol.APP_KEY_HASH, appConfig.getKeyHash());
            extrasString = extras.toString();
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided. Detailed error message: " + e.toString());
        }

        configuration = new RequestConfiguration(appConfig, kaHeader, extrasString);
        return configuration;
    }


    public String getAppKey() {
        return appConfig.getAppKey();
    }

    @Override
    public String getClientSecret() {
        return configuration.getClientSecret();
    }

    public String getKeyHash() {
        return appConfig.getKeyHash();
    }

    @Override
    public String getKAHeader() {
        return appConfig.getKAHeader();
    }

    public String getAppVer() {
        return appConfig.getAppVer();
    }
    public String getPackageName() {
        return appConfig.getPackageName();
    }
    public String getExtras() {
        return extras;
    }

    @Override
    public JSONObject getExtrasJson() {
        throw new UnsupportedOperationException("This method is not implemented in this class. Use com.kakao.util.RequestConfiguration instead.");
    }
}
