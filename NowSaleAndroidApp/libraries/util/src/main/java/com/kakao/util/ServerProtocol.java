package com.kakao.util;

import com.kakao.util.helper.log.Logger.DeployPhase;

/**
 * @author kevin.kang. Created on 2018. 7. 4..
 */
public final class ServerProtocol {
    public static final DeployPhase DEPLOY_PHASE = DeployPhase.current();

    public static final String PLUS_FRIEND_AUTHORITY = initPlusFriendAuthority();
    public static final String PF_ADD_PATH = "friend";
    public static final String PF_CHAT_PATH = "chat";

    public static final String SCHEME = "https";

    private static String initPlusFriendAuthority() {
        switch (DEPLOY_PHASE) {
            case Local:
                return "localhost:";
            case Alpha:
            case Sandbox:
                return "sandbox-pf.kakao.com";
            case Beta:
                return "beta-pf.kakao.com";
            case Real:
            default:
                return "pf.kakao.com";

        }
    }
}
