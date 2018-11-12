/**
 * Copyright 2014-2015 Kakao Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kakao.usermgmt;

/**
 * @author leoshin, created at 15. 8. 6..
 */
public class StringSet {
    public static final String properties = "properties";
    public static final String id = "id";
    public static final String kaccount_email = "kaccount_email";
    public static final String email_verified = "kaccount_email_verified";
    public static final String propertyKeys = "propertyKeys";
    public static final String property_keys = "property_keys";
    public static final String secure_resource = "secure_resource";
    public static final String has_signed_up = "has_signed_up";
    public static final String kakao_account = "kakao_account";
    public static final String for_partner = "for_partner";

    /**
     * Policy.RESERVED_PROPERTY_NAMES 와 match
     */
    public static final String nickname = "nickname";
    public static final String thumbnail_image = "thumbnail_image";
    public static final String profile_image = "profile_image";


    public static final String authenticated_at = "authenticated_at";
    public static final String ci = "ci";
    public static final String auth_level = "auth_level";
    public static final String auth_level_code = "auth_level_code";
    public static final String bypass_age_limit = "bypass_age_limit";

    // ForPartner keys
    public static final String uuid = "uuid";
    public static final String remaining_invite_count = "remaining_invite_count";
    public static final String remaining_group_msg_count = "remaining_group_msg_count";

    // UserAccount keys
    public static final String has_email = "has_email";
    public static final String is_email_verified = "is_email_verified";
    public static final String email = "email";
    public static final String is_kakaotalk_user = "is_kakaotalk_user";
    public static final String has_phone_number = "has_phone_number";
    public static final String phone_number = "phone_number";
    public static final String has_age_range = "has_age_range";
    public static final String age_range = "age_range";
    public static final String has_birthday = "has_birthday";
    public static final String birthday = "birthday";
    public static final String has_gender = "has_gender";
    public static final String gender = "gender";
    public static final String display_id = "display_id";
    public static final String account_id = "account_id";
    public static final String talk_user_id = "talk_user_id";
    public static final String service_user_id = "service_user_id";
}
