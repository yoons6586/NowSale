package com.kakao.friends.request;

import com.kakao.auth.network.AuthorizedApiRequest;
import com.kakao.friends.FriendContext;
import com.kakao.test.common.KakaoTestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kevin.kang. Created on 2017. 12. 5..
 */

public class FriendsRequestTest extends KakaoTestCase {
    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void createFriendsRequest() {
        FriendContext friendContext = FriendContext.createContext(FriendsRequest.FriendType.KAKAO_TALK,
                FriendsRequest.FriendFilter.NONE, FriendsRequest.FriendOrder.NICKNAME,
                false, 0, 100, "asc");
        AuthorizedApiRequest request = new FriendsRequest(friendContext);
    }
}
