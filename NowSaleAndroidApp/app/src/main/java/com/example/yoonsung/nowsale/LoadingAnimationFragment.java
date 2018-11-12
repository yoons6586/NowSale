package com.example.yoonsung.nowsale;

import android.app.Fragment;

/**
 * Created by TedPark on 2017. 3. 18..
 */

public class LoadingAnimationFragment extends Fragment {


    public void progressON() {
        LoadingAnimationApplication.getInstance().progressON(getActivity(), null);
    }

    public void progressON(String message) {
        LoadingAnimationApplication.getInstance().progressON(getActivity(), message);
    }

    public void progressOFF() {
        LoadingAnimationApplication.getInstance().progressOFF();
    }

}
