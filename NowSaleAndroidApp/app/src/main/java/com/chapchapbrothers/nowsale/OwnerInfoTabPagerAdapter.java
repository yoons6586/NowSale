package com.chapchapbrothers.nowsale;

/**
 * Created by yoonsung on 2018. 10. 24..
 */


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.chapchapbrothers.nowsale.VO.CouponVO;
import com.chapchapbrothers.nowsale.VO.DangolWithMarketMenuImg;
import com.chapchapbrothers.nowsale.VO.MenuVO;

import java.util.ArrayList;
import java.util.List;

public class OwnerInfoTabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;
    private CouponVO couponVO;
    private DangolWithMarketMenuImg dangolWithMarketMenuImg;
    private List<MenuVO> list;



    public OwnerInfoTabPagerAdapter(FragmentManager fm, int tabCount, CouponVO couponVO, DangolWithMarketMenuImg dangolWithMarketMenuImg, List<MenuVO> list) {
        super(fm);

        this.tabCount = tabCount;
        this.couponVO = couponVO;
        this.dangolWithMarketMenuImg = dangolWithMarketMenuImg;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                OwnerInfoTabFragment1 tabFragment1 = new OwnerInfoTabFragment1();

                Bundle bundle1 = new Bundle(3); // 파라미터는 전달할 데이터 개수
                bundle1.putSerializable("CouponVO",couponVO);
                Log.e("check","확인");
                bundle1.putParcelable("IsFavoriteGetCountVO",dangolWithMarketMenuImg);
//                bundle1.putSerializable("menuDatas",list);
                bundle1.putParcelableArrayList("menuDatas", (ArrayList<? extends Parcelable>) list);
                tabFragment1.setArguments(bundle1);

                return tabFragment1;
            case 1:
                FActivity tabFragment2 = new FActivity();

                Bundle bundle2 = new Bundle(2); // 파라미터는 전달할 데이터 개수
                bundle2.putInt("what",6);
                bundle2.putInt("ownerInfoOwnerKey",couponVO.getOwner_key());
                tabFragment2.setArguments(bundle2);
                return tabFragment2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
