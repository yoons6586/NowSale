package com.kakao.friends.response.model;

import android.os.Parcel;

import com.kakao.test.common.KakaoTestCase;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author kevin.kang. Created on 2018. 5. 21..
 */
public class AppFriendInfoTest extends KakaoTestCase {
    @Test
    public void testParcelable() {
        AppFriendInfo friendInfo =
                new AppFriendInfo(1234, "profile_nickname",
                        "profile_image");

        Parcel parcel = Parcel.obtain();
        friendInfo.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        AppFriendInfo retrieved = AppFriendInfo.CREATOR.createFromParcel(parcel);
        assertEquals(friendInfo.getId(), retrieved.getId());
        assertEquals(friendInfo.getProfileNickname(), retrieved.getProfileNickname());
        assertEquals(friendInfo.getProfileThumbnailImage(), retrieved.getProfileThumbnailImage());
    }
}
