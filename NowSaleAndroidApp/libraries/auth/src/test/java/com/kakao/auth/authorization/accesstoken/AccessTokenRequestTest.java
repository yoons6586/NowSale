package com.kakao.auth.authorization.accesstoken;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.mocks.TestAppConfig;
import com.kakao.auth.StringSet;
import com.kakao.util.IConfiguration;
import com.kakao.util.helper.CommonProtocol;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
/**
 * @author kevin.kang. Created on 2018. 4. 16..
 */
@DisplayName("AccessTokenRequest's")
class AccessTokenRequestTest {
    @Test
    @DisplayName("Constructor should populate every header and parameter")
    void create() {
        IConfiguration configuration = TestAppConfig.createTestAppConfig();
        AccessTokenRequest request = new AccessTokenRequest(
                configuration,
                "sample_auth_code",
                null,
                ApprovalType.INDIVIDUAL.toString()
        );

        Map<String, String> headers = request.getHeaders();
        Map<String, String> params = request.getParams();


        assertEquals("POST", request.getMethod());

        assertTrue(headers.containsKey(CommonProtocol.KA_HEADER_KEY));

        assertEquals(configuration.getKeyHash(), params.get(StringSet.android_key_hash));
        assertEquals(ApprovalType.INDIVIDUAL.toString(), params.get(StringSet.approval_type));
        assertEquals(configuration.getAppKey(), params.get(StringSet.client_id));
        assertEquals(configuration.getClientSecret(), params.get(StringSet.client_secret));
        assertEquals("sample_auth_code", params.get(StringSet.code));
        assertEquals(StringSet.authorization_code, params.get(StringSet.grant_type));
        assertEquals(String.format("kakao%s://oauth", configuration.getAppKey()), params.get(StringSet.redirect_uri));
    }
}
