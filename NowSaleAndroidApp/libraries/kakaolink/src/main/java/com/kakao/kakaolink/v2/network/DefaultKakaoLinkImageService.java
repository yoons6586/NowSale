package com.kakao.kakaolink.v2.network;

import android.content.Context;

import com.kakao.network.IRequest;
import com.kakao.util.KakaoUtilService;

import java.io.File;

/**
 * @author kevin.kang. Created on 2017. 11. 20..
 */

class DefaultKakaoLinkImageService implements KakaoLinkImageService {
    private KakaoUtilService utilService;
    DefaultKakaoLinkImageService(final KakaoUtilService utilService) {
        this.utilService = utilService;
    }

    @Override
    public IRequest imageUploadRequest(final Context context, File imageFile, boolean secureResource) {
        return new LinkImageUploadRequest(utilService.getAppConfiguration(context), secureResource, imageFile);
    }

    @Override
    public IRequest imageScrapRequest(final Context context, String url, boolean secureResource) {
        return new LinkImageScrapRequest(utilService.getAppConfiguration(context), url, secureResource);
    }

    @Override
    public IRequest imageDeleteRequestWithUrl(final Context context, String imageUrl) {
        return new LinkImageDeleteRequest(utilService.getAppConfiguration(context), imageUrl, null);
    }

    @Override
    public IRequest imageDeleteRequestWithToken(Context context, String imageToken) {
        return new LinkImageDeleteRequest(utilService.getAppConfiguration(context), null, imageToken);
    }
}
