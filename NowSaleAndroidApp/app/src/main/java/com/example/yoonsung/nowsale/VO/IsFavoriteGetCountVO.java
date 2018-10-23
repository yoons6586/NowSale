package com.example.yoonsung.nowsale.VO;

import java.io.Serializable;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

public class IsFavoriteGetCountVO implements Serializable{
    private int dangol_count;
    private boolean dangol;



    public boolean isDangol() {
        return dangol;
    }

    public void setDangol(boolean dangol) {
        this.dangol = dangol;
    }

    public int getDangol_count() {
        return dangol_count;
    }

    public void setDangol_count(int dangol_count) {
        this.dangol_count = dangol_count;
    }
}
