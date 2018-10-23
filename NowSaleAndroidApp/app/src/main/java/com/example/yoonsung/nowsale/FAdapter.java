package com.example.yoonsung.nowsale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Junyoung on 2016-06-23.
 */

public class FAdapter extends FragmentStatePagerAdapter {
    private FActivity tabFragment;
    private Bundle bundle;
    // Count number of tabs
    private int tabCount;

    public FAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        bundle = new Bundle(2); // 파라미터는 전달할 데이터 개수
        // Returning the current tabs
        bundle.putInt("what",1);
        switch (position) {

            case 0:
                tabFragment = new FActivity();

                bundle.putString("category", "food"); // key , value

                tabFragment.setArguments(bundle);

                return tabFragment;
            case 1:
                tabFragment = new FActivity();
                bundle.putString("category", "food"); // key , value
                tabFragment.setArguments(bundle);

                return tabFragment;
            case 2:
                tabFragment = new FActivity();
                bundle.putString("category", "food"); // key , value
                tabFragment.setArguments(bundle);

                return tabFragment;
            case 3:
                tabFragment = new FActivity();
                bundle.putString("category", "food"); // key , value
                tabFragment.setArguments(bundle);

                return tabFragment;
            case 4:
                tabFragment = new FActivity();
                bundle.putString("category", "food"); // key , value
                tabFragment.setArguments(bundle);

                return tabFragment;
            case 5:
                tabFragment = new FActivity();
                bundle.putString("category", "food"); // key , value
                tabFragment.setArguments(bundle);

                return tabFragment;
            case 6:
                tabFragment = new FActivity();
                bundle.putString("category", "food"); // key , value
                tabFragment.setArguments(bundle);

                return tabFragment;
            case 7:
                tabFragment = new FActivity();
                bundle.putString("category", "food"); // key , value
                tabFragment.setArguments(bundle);

                return tabFragment;
            case 8:
                tabFragment = new FActivity();
                bundle.putString("category", "food"); // key , value
                tabFragment.setArguments(bundle);

                return tabFragment;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}