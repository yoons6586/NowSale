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
package com.kakao.usermgmt.response;

import com.kakao.network.response.ApiResponseStatusError;
import com.kakao.network.response.JSONObjectResponse;
import com.kakao.network.response.ResponseBody;
import com.kakao.network.response.ResponseData;
import com.kakao.network.response.ResponseStringConverter;
import com.kakao.usermgmt.response.model.UserProfile;

/**
 * @author leoshin, created at 15. 8. 6..
 */
public class MeResponse extends JSONObjectResponse {
    private final UserProfile userProfile;

    MeResponse(String stringData) {
        super(stringData);
        this.userProfile = new UserProfile(getBody());
    }

    @Deprecated
    MeResponse(ResponseData responseData) throws ResponseBody.ResponseBodyException, ApiResponseStatusError {
        super(responseData);
        this.userProfile = new UserProfile(getBody());
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public static final ResponseStringConverter<UserProfile> CONVERTER = new ResponseStringConverter<UserProfile>() {
        @Override
        public UserProfile convert(String o) throws ResponseBody.ResponseBodyException, ApiResponseStatusError {
            return new MeResponse(o).getUserProfile();
        }
    };
}
