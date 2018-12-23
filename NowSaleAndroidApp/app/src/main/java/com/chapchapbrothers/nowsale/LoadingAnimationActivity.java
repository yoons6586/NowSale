package com.chapchapbrothers.nowsale;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by TedPark on 2017. 3. 18..
 */

public class LoadingAnimationActivity extends AppCompatActivity {


    public void progressON() {
        LoadingAnimationApplication.getInstance().progressON(this, null);
    }

    public void progressON(String message) {
        LoadingAnimationApplication.getInstance().progressON(this, message);
    }

    public void progressOFF() {
        LoadingAnimationApplication.getInstance().progressOFF();
    }

}
