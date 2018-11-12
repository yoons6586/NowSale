package com.kakao.kakaolink.v2;

import com.kakao.kakaolink.internal.KakaoTalkLinkProtocol;
import com.kakao.util.RequestConfiguration;
import com.kakao.util.helper.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author kevin.kang. Created on 2016. 11. 29..
            */

public class KakaoLinkTestHelper {
    public static RequestConfiguration createMockRequestConfiguration() {
        return new RequestConfiguration("sample_app_key", "client_secret",
                "sample_key_hash", "sample_ka_header","sample_app_ver",
                "sample_package_name", new JSONObject());
    }

    public static JSONObject createKakaoLinkResponse(final String templateId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KakaoTalkLinkProtocol.TEMPLATE_ID, templateId);
            jsonObject.put(KakaoTalkLinkProtocol.TEMPLATE_ARGS, new JSONObject());
            JSONObject templateJson = new JSONObject();
            templateJson.put(KakaoTalkLinkProtocol.P, new JSONObject());
            templateJson.put(KakaoTalkLinkProtocol.C, new JSONObject());
            jsonObject.put(KakaoTalkLinkProtocol.TEMPLATE_MSG, templateJson);
        } catch (JSONException ignored) {
        }
        return jsonObject;
    }

    public static JSONObject createLongKakaoLinkResponse(final String templateId) {
        JSONObject jsonObject = createKakaoLinkResponse(templateId);
        JSONObject templateArgs = new JSONObject();
        String value = "1234567890";
        for (int i = 0; i < 10; i++) {
            value += value;
        }
        try {
            templateArgs.put("key1", value);
            jsonObject.put(KakaoTalkLinkProtocol.TEMPLATE_ARGS, templateArgs);
        } catch (JSONException ignored) {
        }

        return jsonObject;
    }
}
