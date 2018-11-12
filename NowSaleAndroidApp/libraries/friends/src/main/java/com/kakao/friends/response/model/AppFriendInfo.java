/*
  Copyright 2018 Kakao Corp.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.kakao.friends.response.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.kakao.friends.StringSet;
import com.kakao.network.response.JSONObjectConverter;
import com.kakao.network.response.ResponseBody;

import org.json.JSONObject;

/**
 * Minimal friend information containing:
 *  1) user id for distinguishing users
 *  2) nickname and profile image for displaying on UI
 *
 * @since 1.11.1
 * @author kevin.kang. Created on 2018. 5. 18..
 */
public class AppFriendInfo implements Parcelable {
    final private long userId;
    final private String profileNickname;
    final private String profileThumbnailImage;

    /**
     * Constructor for parsing app friend info inside {@link com.kakao.friends.response.AppFriendsResponse}.
     *
     * @param body json object corresponding to app friend info
     */
    AppFriendInfo(JSONObject body) {
        this.userId = body.optLong(StringSet.id, 0);
        this.profileNickname = body.optString(StringSet.profile_nickname, null);
        this.profileThumbnailImage = body.optString(StringSet.profile_thumbnail_image, null);
    }

    AppFriendInfo(final long userId, final String profileNickname, final String profileThumbnailImage) {
        this.userId = userId;
        this.profileNickname = profileNickname;
        this.profileThumbnailImage = profileThumbnailImage;
    }

    /**
     * Constructor for possible extension of this class, which implements {@link Parcelable}.
     * @param parcel Parcel
     */
    AppFriendInfo(Parcel parcel) {
        userId = parcel.readLong();
        profileNickname = parcel.readString();
        profileThumbnailImage = parcel.readString();
    }

    /**
     * App user id
     *
     * @return 사용자 ID
     */
    public long getId() {
        return userId;
    }

    /**
     * 친구의 대표 프로필 닉네임. 앱 가입친구의 경우 앱에서 설정한 닉네임. 미가입친구의 경우 톡 또는 스토리의 닉네임
     * 요청한 friend_type에 따라 달라짐
     * @return 대표 프로필 닉네임
     */
    public String getProfileNickname() {
        return profileNickname;
    }

    /**
     * 친구의 썸네일 이미지
     * @return 친구의 썸네일 이미지 url
     */
    public String getProfileThumbnailImage() {
        return profileThumbnailImage;
    }

    @Override
    public String toString() {
        return "++ userId : " + getId() +
                ", profileNickname : " + getProfileNickname() +
                ", profileThumbnailImage : " + getProfileThumbnailImage();
    }

    public static final JSONObjectConverter<AppFriendInfo> CONVERTER = new JSONObjectConverter<AppFriendInfo>() {
        @Override
        public AppFriendInfo convert(JSONObject body) throws ResponseBody.ResponseBodyException {
            return new AppFriendInfo(body);
        }
    };

    /**
     * Below are codes related to Parcelable implementation.
     */
    public static final Parcelable.Creator<AppFriendInfo> CREATOR = new Parcelable.Creator<AppFriendInfo>() {
        @Override
        public AppFriendInfo createFromParcel(Parcel source) {
            return new AppFriendInfo(source);
        }

        @Override
        public AppFriendInfo[] newArray(int size) {
            return new AppFriendInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(userId);
        dest.writeString(profileNickname);
        dest.writeString(profileThumbnailImage);
    }
}
