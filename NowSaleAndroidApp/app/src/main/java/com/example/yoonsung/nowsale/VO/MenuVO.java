package com.example.yoonsung.nowsale.VO;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter

public class MenuVO implements Parcelable{
    private String menu_img_name,menu_name,menu_money;

    protected MenuVO(Parcel in) {
        menu_img_name = in.readString();
        menu_name = in.readString();
        menu_money = in.readString();
    }

    public static final Creator<MenuVO> CREATOR = new Creator<MenuVO>() {
        @Override
        public MenuVO createFromParcel(Parcel in) {
            return new MenuVO(in);
        }

        @Override
        public MenuVO[] newArray(int size) {
            return new MenuVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menu_img_name);
        dest.writeString(menu_name);
        dest.writeString(menu_money);
    }
}
