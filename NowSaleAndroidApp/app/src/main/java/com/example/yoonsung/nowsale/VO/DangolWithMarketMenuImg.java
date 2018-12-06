package com.example.yoonsung.nowsale.VO;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter
public class DangolWithMarketMenuImg implements Parcelable {
    private Integer dangol_count;
    private boolean dangol;
    private List<MenuVO> menuVOList;
    private List<MarketImgVO> marketImgVOList;

    protected DangolWithMarketMenuImg(Parcel in) {
        if (in.readByte() == 0) {
            dangol_count = null;
        } else {
            dangol_count = in.readInt();
        }
        dangol = in.readByte() != 0;
        menuVOList = in.createTypedArrayList(MenuVO.CREATOR);
        marketImgVOList = in.createTypedArrayList(MarketImgVO.CREATOR);
    }

    public static final Creator<DangolWithMarketMenuImg> CREATOR = new Creator<DangolWithMarketMenuImg>() {
        @Override
        public DangolWithMarketMenuImg createFromParcel(Parcel in) {
            return new DangolWithMarketMenuImg(in);
        }

        @Override
        public DangolWithMarketMenuImg[] newArray(int size) {
            return new DangolWithMarketMenuImg[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dangol_count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(dangol_count);
        }
        dest.writeByte((byte) (dangol ? 1 : 0));
        dest.writeTypedList(menuVOList);
        dest.writeTypedList(marketImgVOList);
    }
}
