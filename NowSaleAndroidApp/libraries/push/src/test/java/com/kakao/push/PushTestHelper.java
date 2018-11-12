package com.kakao.push;

import com.kakao.util.IConfiguration;

import org.json.JSONObject;

/**
 * @author kevin.kang. Created on 2017. 12. 7..
 */

public class PushTestHelper {
    public static IConfiguration getConfiguration() {
        return new IConfiguration() {
            @Override
            public String getAppKey() {
                return "app_key";
            }

            @Override
            public String getClientSecret() {
                return "client_secret";
            }

            @Override
            public String getKeyHash() {
                return "key_hash";
            }

            @Override
            public String getKAHeader() {
                return "ka_header";
            }

            @Override
            public String getPackageName() {
                return "package_name";
            }

            @Override
            public String getAppVer() {
                return "app_ver";
            }

            @Override
            public String getExtras() {
                return getExtrasJson().toString();
            }

            @Override
            public JSONObject getExtrasJson() {
                return new JSONObject();
            }
        };
    }
}
