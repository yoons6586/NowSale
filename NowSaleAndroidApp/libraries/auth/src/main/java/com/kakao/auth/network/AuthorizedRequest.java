package com.kakao.auth.network;

import com.kakao.network.IRequest;
import com.kakao.util.IConfiguration;

/**
 * @author kevin.kang. Created on 2017. 11. 30..
 */

public interface AuthorizedRequest extends IRequest {
    void setAccessToken(final String accessToken);
    void setConfiguration(final IConfiguration configuration);
}
