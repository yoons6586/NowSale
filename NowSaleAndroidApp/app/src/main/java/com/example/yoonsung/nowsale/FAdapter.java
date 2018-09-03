package com.example.yoonsung.nowsale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Junyoung on 2016-06-23.
 */

public class FAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public FAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                F1Activity tabFragment1 = new F1Activity();
                return tabFragment1;
            case 1:
                F2Activity tabFragment2 = new F2Activity();
                return tabFragment2;
            case 2:
                F3Activity tabFragment3 = new F3Activity();
                return tabFragment3;



            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}