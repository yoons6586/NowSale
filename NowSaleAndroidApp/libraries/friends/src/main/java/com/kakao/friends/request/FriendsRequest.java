/*
  Copyright 2014-2017 Kakao Corp.

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
package com.kakao.friends.request;

import android.net.Uri;

import com.kakao.auth.network.AuthorizedApiRequest;
import com.kakao.friends.AppFriendContext;
import com.kakao.friends.FriendContext;
import com.kakao.friends.StringSet;
import com.kakao.network.ServerProtocol;
import com.kakao.util.IConfiguration;

/**
 * 카카오 친구를 얻어오기 위해 사용되는 Request Model.a
 * @author leo.shin
 */
public class FriendsRequest extends AuthorizedApiRequest {
    /**
     * 가져올 친구의 타입
     * KAKAO_TALK 카카오톡 친구
     * KAKAO_STORY 카카오스토리 친구
     * KAKAO_TALK_AND_STORY 카카오톡과 카카오스토리의 친구
     */
    public enum FriendType {
        UNDEFINED("undefined", -1),
        KAKAO_TALK("talk", 0),
        KAKAO_STORY("story", 1),
        KAKAO_TALK_AND_STORY("talkstory", 2);

        final private String name;
        final private int value;
        FriendType(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * 친구리스트 필터 방법
     * NONE 전체친구
     * REGISTERED 앱 가입친구
     * INVITABLE 앱미가입친구
     */
    public enum FriendFilter {
        NONE("none", 0),
        REGISTERED("registered", 1),
        INVITABLE("invitable", 2), ;

        final private String name;
        final private int value;
        FriendFilter(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * 친구리스트 정렬 대상.
     * NICKNAME 이름으로 정렬.
     * LAST_CHAT_TIME 마지막 채팅시간 기준정렬.
     * TALK_CREATED_AT 카카오톡 userId 기준정렬.
     * AGE 나이순 정렬.
     * AFFINITY 친밀도.
     */
    public enum FriendOrder {
        UNDEFINED("undefined", -1),
        NICKNAME("nickname", 0),
        LAST_CHAT_TIME("last_chat_time", 1),
        TALK_CREATED_AT("talk_created_at", 2),
        AGE("age", 3),
        AFFINITY("affinity", 4);

        final private String name;
        final private int value;
        FriendOrder(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    private final FriendType friendType;
    private final FriendFilter friendFilter;
    private final FriendOrder friendOrder;
    private final boolean secureResource;
    private final int offset;
    private final int limit;
    private final String order;
    private final String url;


    public FriendsRequest(FriendContext context) {
        this.friendType = context.getFriendType();
        this.friendFilter = context.getFriendFilter();
        this.friendOrder = context.getFriendOrder();
        this.secureResource = context.isSecureResource();
        this.offset = context.getOffset();
        this.limit = context.getLimit();
        this.order = context.getOrder();
        this.url = context.getAfterUrl();
    }

    public FriendsRequest(AppFriendContext context) {
        this.friendType = null;
        this.friendFilter = null;
        this.friendOrder = null;
        this.secureResource = context.isSecureResource();
        this.offset = context.getOffset();
        this.limit = context.getLimit();
        this.order = context.getOrder();
        this.url = context.getAfterUrl();
    }

    @Override
    public String getMethod() {
        return GET;
    }

    @Override
    public Uri.Builder getUriBuilder() {
        Uri.Builder builder = super.getUriBuilder().path(ServerProtocol.GET_FRIENDS_PATH)
                .appendQueryParameter(StringSet.offset, String.valueOf(offset))
                .appendQueryParameter(StringSet.limit, String.valueOf(limit))
                .appendQueryParameter(StringSet.secure_resource, String.valueOf(secureResource));
        if (friendType != null) builder.appendQueryParameter(StringSet.friend_type, friendType.name);
        if (friendFilter != null) builder.appendQueryParameter(StringSet.friend_filter, friendFilter.name);
        if (friendOrder != null) builder.appendQueryParameter(StringSet.friend_order, friendOrder.name);
        if (order != null) builder.appendQueryParameter(StringSet.order, order);
        return builder;
    }

    @Override
    public String getUrl() {
        if (url != null && url.length() > 0) {
            return url;
        }
        return super.getUrl();
    }
}
