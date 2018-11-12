package com.kakao.kakaolink.v2.network;

import com.kakao.kakaolink.v2.KakaoLinkTestHelper;
import com.kakao.test.common.KakaoTestCase;
import com.kakao.util.RequestConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kevin.kang. Created on 2017. 3. 14..
 */
public class TemplateScrapRequestTest extends KakaoTestCase {
    private RequestConfiguration configuration;

    @Before
    public void setup() {
        configuration = KakaoLinkTestHelper.createMockRequestConfiguration();
    }

    @Test
    public void testMethodIsGet() {
        TemplateScrapRequest request = new TemplateScrapRequest(configuration, "url", null, null);
        assertEquals("GET", request.getMethod());
    }
}
