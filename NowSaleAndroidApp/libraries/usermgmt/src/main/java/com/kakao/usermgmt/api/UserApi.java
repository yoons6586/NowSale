/*
  Copyright 2014-2018 Kakao Corp.

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
package com.kakao.usermgmt.api;

import com.kakao.auth.AuthService.AgeLimit;
import com.kakao.auth.network.AuthNetworkService;
import com.kakao.auth.network.AuthorizedRequest;
import com.kakao.usermgmt.request.AgeAuthRequest;
import com.kakao.usermgmt.request.LogoutRequest;
import com.kakao.usermgmt.request.MeRequest;
import com.kakao.usermgmt.request.MeV2Request;
import com.kakao.usermgmt.request.SignupRequest;
import com.kakao.usermgmt.request.UnlinkRequest;
import com.kakao.usermgmt.request.UpdateProfileRequest;
import com.kakao.usermgmt.response.AgeAuthResponse;
import com.kakao.usermgmt.response.MeResponse;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.UserResponse;
import com.kakao.usermgmt.response.model.UserProfile;

import java.util.List;
import java.util.Map;

/**
 * Bloking으로 동작하며, 사용자관리에 관련된 내부 API콜을 한다.
 * @author leoshin, created at 15. 8. 10..
 */
public class UserApi {

    /**
     * 가입 요청
     * @param properties 가입시 받은 사용자 정보
     * @return UserResponse
     */
    public Long requestSignup(Map<String, String> properties) throws Exception {
        AuthorizedRequest request = new SignupRequest(properties);
        return networkService.request(request, UserResponse.CONVERTER);
    }

    /**
     * 로그아웃 요청
     */
    public Long requestLogout() throws Exception {
        AuthorizedRequest request = new LogoutRequest();
        return networkService.request(request, UserResponse.CONVERTER);
    }

    /**
     * 로그아웃 요청
     */
    public Long requestUnlink() throws Exception {
        AuthorizedRequest request = new UnlinkRequest();
        return networkService.request(request, UserResponse.CONVERTER);
    }

    /**
     * 사용자정보 저장 요청
     * @param properties 저장할 사용자 정보
     */
    public Long requestUpdateProfile(Map<String, String> properties) throws Exception {
        AuthorizedRequest request = new UpdateProfileRequest(properties);
        return networkService.request(request, UserResponse.CONVERTER);
    }

    /**
     * 사용자 정보 일부나 이미지 리소스를 https로 받고 싶은 경우의 사용자정보 요청
     * @param propertyKeys 사용자 정보의 키 리스트
     * @param secureResource 이미지 url을 https로 반환할지 여부
     */
    public UserProfile requestMe(List<String> propertyKeys, final Boolean secureResource) throws Exception {
        AuthorizedRequest request = new MeRequest(propertyKeys, secureResource);
        return networkService.request(request, MeResponse.CONVERTER);
    }

    /**
     *
     * @param propertyKeys List of user property keys to retrieve
     * @param secureResource whether image urls will have https scheme or not
     * @return a MeV2Response containing user data
     * @throws Exception when
     */
    public MeV2Response me(List<String> propertyKeys, final Boolean secureResource) throws Exception {
        AuthorizedRequest request = new MeV2Request(propertyKeys, secureResource);
        return networkService.request(request, MeV2Response.CONVERTER);
    }

    /**
     * 토큰으로 인증날짜와 CI값을 얻는다. 게임 사업부가 저장하고 있는 정보를 내려준다.
     */
    public AgeAuthResponse requestAgeAuthInfo(AgeLimit ageLimit, List<String> propertyKeyList) throws Exception {
        AuthorizedRequest request = new AgeAuthRequest(ageLimit != null ? ageLimit.getValue() : null, propertyKeyList);
        return networkService.request(request, AgeAuthResponse.CONVERTER);
    }

    private static UserApi instance = new UserApi(AuthNetworkService.Factory.getInstance());

    private AuthNetworkService networkService;

    /**
     *
     * @return a singleton instance of UserApi
     */
    public static UserApi getInstance() {
        return instance;
    }

    @SuppressWarnings("WeakerAccess")
    UserApi(final AuthNetworkService networkService) {
        this.networkService = networkService;
    }
}
