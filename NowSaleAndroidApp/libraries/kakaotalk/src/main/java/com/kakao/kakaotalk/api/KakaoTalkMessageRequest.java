package com.kakao.kakaotalk.api;

import com.kakao.auth.network.AuthorizedApiRequest;
import com.kakao.kakaotalk.StringSet;
import com.kakao.util.IConfiguration;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin.kang. Created on 2017. 4. 26..
 */

public abstract class KakaoTalkMessageRequest extends AuthorizedApiRequest {

    protected final String templateId;
    protected final Map<String, String> templateArgs;

    public KakaoTalkMessageRequest(String templateId, Map<String, String> args) {
        this.templateId = templateId;
        this.templateArgs = args;
    }

    @Override
    public String getMethod() {
        return POST;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(StringSet.template_id, templateId);

        if (templateArgs != null && !templateArgs.isEmpty()) {
            params.put(StringSet.template_args, new JSONObject(templateArgs).toString());
        }
        return params;
    }
}
