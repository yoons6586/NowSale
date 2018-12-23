package com.chapchapbrothers.nowsale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chapchapbrothers.nowsale.VO.DangolWithMarketMenuImg;

public class SliderOwnerImageAdapter extends PagerAdapter { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
//    private int[] images = {R.drawable.beauty,R.drawable.fashion,R.drawable.alcohol1};
    private LayoutInflater inflater;
    private Context context;
    private int owner_key;
    private DangolWithMarketMenuImg dangolWithMarketMenuImg;


//    Glide.with(getActivity()).load(Config.url+"/drawable/owner/"+list.get(position).getLogo_img()).into(itemHolder.imgLogo);

    public SliderOwnerImageAdapter(Context context, int owner_key, DangolWithMarketMenuImg dangolWithMarketMenuImg){
        this.context = context;
        this.owner_key=owner_key;
        this.dangolWithMarketMenuImg = dangolWithMarketMenuImg;
    }


    @Override
    public int getCount() {
        if(dangolWithMarketMenuImg.getMarketImgVOList().size()==0)
            return 1;
        return dangolWithMarketMenuImg.getMarketImgVOList().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider_market_img, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
        if(dangolWithMarketMenuImg.getMarketImgVOList().size()!=0)
            Glide.with(context).load(Config.url+dangolWithMarketMenuImg.getMarketImgVOList().get(position).getMarket_img()).into(imageView);

        container.addView(v);

        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}

