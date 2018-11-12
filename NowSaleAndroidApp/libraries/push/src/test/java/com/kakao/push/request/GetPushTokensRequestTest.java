package com.kakao.push.request;

import android.net.Uri;

import com.kakao.auth.network.AuthorizedRequest;
import com.kakao.network.ServerProtocol;
import com.kakao.push.PushTestHelper;
import com.kakao.test.common.KakaoTestCase;
import com.kakao.util.IConfiguration;
import com.kakao.util.helper.CommonProtocol;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

/**
 * @author kevin.kang. Created on 2017. 12. 7..
 */

public class GetPushTokensRequestTest extends KakaoTestCase {
    @Test
    public void create() {
        AuthorizedRequest request = new GetPushTokensRequest();
        IConfiguration configuration = PushTestHelper.getConfiguration();

        request.setAccessToken("access_token");
        request.setConfiguration(configuration);

        Uri uri = Uri.parse(request.getUrl());
        assertEquals("GET", request.getMethod());
        assertEquals(ServerProtocol.API_AUTHORITY, uri.getHost());
        assertEquals("/" + ServerProtocol.PUSH_TOKENS_PATH, uri.getPath());

        Map<String, String> headers = request.getHeaders();
        assertEquals(String.format("%s %s", ServerProtocol.AUTHORIZATION_BEARER, "access_token"), headers.get(ServerProtocol.AUTHORIZATION_HEADER_KEY));
        assertEquals(configuration.getKAHeader(), headers.get(CommonProtocol.KA_HEADER_KEY));
    }

}
