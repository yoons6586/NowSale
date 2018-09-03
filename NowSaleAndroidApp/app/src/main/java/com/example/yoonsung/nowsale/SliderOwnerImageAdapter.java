package com.example.yoonsung.nowsale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderOwnerImageAdapter extends PagerAdapter { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private int[] images = {R.drawable.beauty,R.drawable.fashion,R.drawable.alcohol};
    private LayoutInflater inflater;
    private Context context;

    public SliderOwnerImageAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider_owner_info, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
        TextView textView = (TextView)v.findViewById(R.id.textView);

        imageView.setImageResource(images[position]);

        String text = (position+1)+"번째 이미지";
        textView.setText(text);
        container.addView(v);

        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}

