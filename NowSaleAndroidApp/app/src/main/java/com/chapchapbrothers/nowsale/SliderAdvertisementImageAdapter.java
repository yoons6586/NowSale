package com.chapchapbrothers.nowsale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SliderAdvertisementImageAdapter extends PagerAdapter { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
//    private int[] images = {R.drawable.beauty,R.drawable.fashion,R.drawable.alcohol1};
    private LayoutInflater inflater;
    private Context context;
    private List<AdvImgVO> advImgVOList;


//    Glide.with(getActivity()).load(Config.url+"/drawable/owner/"+list.get(position).getLogo_img()).into(itemHolder.imgLogo);

    public SliderAdvertisementImageAdapter(Context context,List<AdvImgVO> advImgVOList){
        this.context = context;
        this.advImgVOList = advImgVOList;
    }


    @Override
    public int getCount() {
        return advImgVOList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((FrameLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider_owner_info, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);

        Glide.with(context).load(Config.url+advImgVOList.get(position).getAdv_img()).into(imageView);
        Log.e("SliderAdver",Config.url+advImgVOList.get(position).getAdv_img());

        container.addView(v);

        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}

