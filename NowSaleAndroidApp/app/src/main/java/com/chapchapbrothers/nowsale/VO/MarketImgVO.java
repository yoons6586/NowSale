package com.chapchapbrothers.nowsale.VO;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter

public class MarketImgVO implements Parcelable{
    private String market_img;

    protected MarketImgVO(Parcel in) {
        market_img = in.readString();
    }

    public static final Creator<MarketImgVO> CREATOR = new Creator<MarketImgVO>() {
        @Override
        public MarketImgVO createFromParcel(Parcel in) {
            return new MarketImgVO(in);
        }

        @Override
        public MarketImgVO[] newArray(int size) {
            return new MarketImgVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(market_img);
    }
}
