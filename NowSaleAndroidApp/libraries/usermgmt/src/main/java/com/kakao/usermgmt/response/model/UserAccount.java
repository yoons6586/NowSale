package com.kakao.usermgmt.response.model;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.kakao.auth.AccessTokenCallback;
import com.kakao.network.response.ResponseBody;
import com.kakao.usermgmt.StringSet;
import com.kakao.util.OptionalBoolean;

import org.json.JSONObject;

import java.util.List;

/**
 * Class for Kakao account user data, meaning these data are account-scoped, not app-scoped.
 *
 * @author kevin.kang. Created on 2018. 4. 4..
 */
public class UserAccount {
    private OptionalBoolean hasEmail;
    private OptionalBoolean isEmailVerified;
    private String email;

    private OptionalBoolean hasPhoneNumber;
    private String phoneNumber;

    private OptionalBoolean hasAgeRange;
    private AgeRange ageRange;

    private OptionalBoolean hasBirthday;
    private String birthday;

    private OptionalBoolean hasGender;
    private Gender gender;

    private OptionalBoolean isKakaoTalkUser;
    private String displayId;

    private JSONObject response;

    public UserAccount(ResponseBody body) {
        hasEmail = body.has(StringSet.has_email) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_email)) :
                OptionalBoolean.NONE;
        isEmailVerified = body.has(StringSet.is_email_verified) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.is_email_verified)) :
                OptionalBoolean.NONE;
        hasPhoneNumber = body.has(StringSet.has_phone_number) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_phone_number)) :
                OptionalBoolean.NONE;
        hasAgeRange = body.has(StringSet.has_age_range) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_age_range)) :
                OptionalBoolean.NONE;
        hasBirthday = body.has(StringSet.has_birthday) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_birthday)) :
                OptionalBoolean.NONE;
        hasGender = body.has(StringSet.has_gender) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_gender)) :
                OptionalBoolean.NONE;
        isKakaoTalkUser = body.has(StringSet.is_kakaotalk_user) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.is_kakaotalk_user)) :
                OptionalBoolean.NONE;


        if (body.has(StringSet.email)) email = body.getString(StringSet.email);
        if (body.has(StringSet.phone_number)) phoneNumber = body.getString(StringSet.phone_number);
        if (body.has(StringSet.age_range)) ageRange = AgeRange.getRange(body.getString(StringSet.age_range));
        if (body.has(StringSet.birthday)) birthday = body.getString(StringSet.birthday);
        if (body.has(StringSet.gender)) gender = Gender.getGender(body.getString(StringSet.gender));
        if (body.has(StringSet.display_id)) displayId = body.getString(StringSet.display_id);

        response = body.getJson();
    }

    /**
     * This method tells you whether user has email registered to her/his Kakao account.
     *
     * In combination with {@link #getEmail()}, you know whether you have to get agreement from
     * user or not.
     *
     * 1. if (getEmail() != null)
     *  - Use the email
     *
     * 2. if (hasEmail() == false)
     *  - User does not have email in Kakao account. There is nothing you can do to retrieve email.
     *
     * 3. if (hasEmail() == true && getEmail() == null)
     *  - User has email in Kakao account but you did not receive it because she/he did not agree
     *  with providing email. Use {@link com.kakao.auth.Session#updateScopes(Activity, List, AccessTokenCallback)}
     *  to update scopes like below:
     *
     *  <code>
     *      List<String> scopes = new ArrayList();
     *      scopes.add("account_email");
     *      Session.getCurrentSession().updateScopes(scopes, new AccessTokenCallback() {
     *        ...
     *      })
     *  </code>
     * @return {@link OptionalBoolean#TRUE} if user has email registered in her/his Kakao account.
     */
    public OptionalBoolean hasEmail() {
        return hasEmail;
    }

    /**
     *
     * @return {@link OptionalBoolean#TRUE} if email is verified,
     * {@link OptionalBoolean#FALSE} if not verified,
     * {@link OptionalBoolean#NONE} if this info cannot be provided.
     */
    @SuppressWarnings("unused")
    public OptionalBoolean isEmailVerified() {
        return isEmailVerified;
    }

    /**
     * Returns email of user's Kakao account. This method returns null under following cases:
     *  - when user does not have email (she/he registered with phone number)
     *  - when user did not agree to provide email to this service
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return {@link OptionalBoolean#TRUE} if this user is KakaoTalk user,
     * {@link OptionalBoolean#FALSE} if not,
     * {@link OptionalBoolean#NONE} if user did not agree to provide this info yet.
     */
    public OptionalBoolean isKakaoTalkUser() {
        return isKakaoTalkUser;
    }

    /**
     * Returns whether user's Kakao account has phone number registered or not.
     *
     * @return {@link OptionalBoolean#TRUE} if user hash phone number,
     * {@link OptionalBoolean#FALSE} if she/he doesn't,
     * {@link OptionalBoolean#NONE} if this info cannot be provided.
     */
    public OptionalBoolean hasPhoneNumber() {
        return hasPhoneNumber;
    }

    /**
     * Returns phone number of user's Kakao account. This method returns null under the following
     * cases:
     *  - when the account has no phone number
     *  - when user did not agree to provide phone number
     *
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns user's display id. This will be either email address or phone number.
     *
     * @return displayId
     */
    @SuppressWarnings("unused")
    public String getDisplayId() {
        return displayId;
    }

    /**
     *
     * @return {@link OptionalBoolean#TRUE}
     */
    public OptionalBoolean hasAgeRange() {
        return hasAgeRange;
    }

    /**
     *
     * Returns user's age range. This getter returns null if:
     *  - when the account has no age range info
     *  - when the user did not agree to provide age range info to this app
     *
     * @return {@link AgeRange} enum, null if not included in the API response.
     */
    @Nullable
    public AgeRange getAgeRange() {
        return ageRange;
    }

    /**
     * Returns whether user's Kakao account has her/his birthday info.
     *
     * @return {@link OptionalBoolean#TRUE} if the account has birthday info,
     * {@link OptionalBoolean#FALSE} if not,
     */
    public OptionalBoolean hasBirthday() {
        return hasBirthday;
    }

    /**
     * Returns user's birthday in mmdd format (0115, 0427, etc).
     * @return birthday in mmdd format, null if not included in the API response.
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Returns whether user's Kakao account has gender info.
     *
     * @return {@link OptionalBoolean#TRUE} if user's Kakao account has gender info,
     * {@link OptionalBoolean#FALSE} if not,
     * {@link OptionalBoolean#NONE} if app does not have permission to use this info.
     */
    public OptionalBoolean hasGender() {
        return hasGender;
    }

    /**
     * Return user's gender.
     *
     * @return {@link Gender} enum, null if not included in the API response.
     */
    @Nullable
    public Gender getGender() {
        return gender;
    }

    /**
     * check if user's email can be provided after scope update (account_email)
     * @return true if user's email can be provided after scope update, false otherwise
     */
    public boolean needsScopeAccountEmail() {
        return hasEmail == OptionalBoolean.TRUE && email == null;
    }

    /**
     * check if user's phone number can be provided after scope update (phone_number)
     * @return true if user's phone number can be provided after scope update, false otherwise
     */
    public boolean needsScopePhoneNumber() {
        return hasPhoneNumber == OptionalBoolean.TRUE && phoneNumber == null;
    }

    /**
     *
     * @return true if user's age range can be provided after scope update, false otherwise
     */
    public boolean needsScopeAgeRange() {
        return hasAgeRange == OptionalBoolean.TRUE && ageRange == null;
    }

    /**
     * check if user's birthday (without year) can be provided after scope update (birthday)
     * @return true if user's birthday (without year) can be provided after scope update, false otherwise
     */
    public boolean needsScopeBirthday() {
        return hasBirthday == OptionalBoolean.TRUE && birthday == null;
    }

    /**
     * check if user's gender can be provided after scope update (gender)
     * @return true if user's gender info can be provided after scope update, false otherwise
     */
    public boolean needsScopeGender() {
        return hasGender == OptionalBoolean.TRUE && gender == null;
    }

    public boolean needsScopeIsKakaotalkUser() {
        return isKakaoTalkUser == OptionalBoolean.NONE;
    }

    /**
     * Returns a raw json API response. If there is any data not defined as field in this class,
     * you can query with appropriate keys.
     *
     * @return raw API response
     */
    public JSONObject getResponse() {
        return response;
    }
}
