package com.kakao.kakaolink.v2.network;


import android.net.Uri;

import com.kakao.network.ServerProtocol;
import com.kakao.network.storage.ImageUploadRequest;
import com.kakao.util.IConfiguration;

import java.io.File;

/**
 * @author kevin.kang. Created on 2017. 3. 20..
 */

class LinkImageUploadRequest extends ImageUploadRequest {
    LinkImageUploadRequest(final IConfiguration configuration, final Boolean secureResource, final File file) {
        super(configuration, secureResource, file);
    }

    @Override
    public Uri.Builder getUriBuilder() {
        Uri.Builder builder = super.getUriBuilder();
        builder.path(ServerProtocol.LINK_IMAGE_UPLOAD_PATH);
        return builder;
    }
}
