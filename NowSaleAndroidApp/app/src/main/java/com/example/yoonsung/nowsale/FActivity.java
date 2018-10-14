package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yoonsung.nowsale.VO.ClientCouponVO;
import com.example.yoonsung.nowsale.VO.CouponVO;
import com.example.yoonsung.nowsale.http.AllService;
import com.example.yoonsung.nowsale.http.ClientService;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpStatus;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FActivity extends Fragment {
    List<CouponVO>datas = new ArrayList<CouponVO>();
    private SectionedRecyclerViewAdapter sectionAdapter;
    private String category;
    private Intent marketInfo_intent;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_ex4, container, false);
        category=getArguments().getString("category");
        sectionAdapter = new SectionedRecyclerViewAdapter();
        marketInfo_intent = new Intent(getActivity(),OwnerInfoActivity.class);

        context = container.getContext();

        AllService service = Config.retrofit.create(AllService.class);
        Call<List<CouponVO>> request = service.getCategoryCoupon(category);
        request.enqueue(new Callback<List<CouponVO>>() {
            @Override
            public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                List<CouponVO> couponVOList = response.body();
                datas = couponVOList;

                sectionAdapter.addSection(new ExpandableContactsSection("다운가능한 쿠폰", datas,marketInfo_intent));
                sectionAdapter.addSection(new ExpandableContactsSection("진행중인 할인행사", datas,marketInfo_intent));

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(sectionAdapter);

            }

            @Override
            public void onFailure(Call<List<CouponVO>> call, Throwable t) {

            }

        });

        return view;
    }

    private class ExpandableContactsSection extends StatelessSection {

        String title;
        List<CouponVO> list;
        boolean expanded = true;
        Intent intent;

        ExpandableContactsSection(String title, List<CouponVO> list,Intent intent) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.list_row)
                    .headerResourceId(R.layout.section_ex4_header)
                    .build());
            this.title = title;
            this.list = list;
            this.intent = intent;
        }

        @Override
        public int getContentItemsTotal() {
            return expanded ? list.size() : 0;
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String name = list.get(position).getContent();
            final int coupon_key = list.get(position).getCoupon_key();

            itemHolder.tvItem.setText(name);
//            itemHolder.imgLogo.setImageResource(name.hashCode() % 2 == 0 ? R.drawable.logo1 : R.drawable.logo2);
            Glide.with(getActivity()).load(Config.url+"/drawable/owner/"+list.get(position).getLogo_img()).into(itemHolder.imgLogo);
            intent.putExtra("CouponVO",list.get(position));
            intent.putExtra("category",category);
            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    startActivity(intent);
                }
            });
            itemHolder.imgDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),LoginCancelPopupActivity.class);
                    if(Config.clientVO.getClient_key() == 0){
                        if(Config.ownerVO.getOwner_key()!=0){
                            intent.putExtra("down",1);
                        }
                        else{
                            intent.putExtra("down",0);

                        }
                        startActivity(intent);
                    }
                    else {
                        ClientService service = Config.retrofit.create(ClientService.class);
                        Call<List<ClientCouponVO>> request = service.insertClientCouponList(new ClientCouponVO(Config.clientVO.getClient_key(), coupon_key));
                        request.enqueue(new Callback<List<ClientCouponVO>>() {
                            @Override
                            public void onResponse(Call<List<ClientCouponVO>> call, Response<List<ClientCouponVO>> response) {
                                Log.e("responseCode",""+response.code());
                                if (response.code() == HttpStatus.SC_OK) {
                                    Log.e("클라이언트 쿠폰 중복", "중복 아니다");
                                    Toast.makeText(getContext(),
                                            String.format("발급되었습니다.",
                                                    sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition()),
                                                    title),
                                            Toast.LENGTH_SHORT).show();
                                } else if (response.code() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                                    Toast.makeText(getContext(),
                                            String.format("중복된 쿠폰입니다.",
                                                    sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition()),
                                                    title),
                                            Toast.LENGTH_SHORT).show();

                                    Log.e("클라이언트 쿠폰 중복", "중복이다");


                                }
                            }

                            @Override
                            public void onFailure(Call<List<ClientCouponVO>> call, Throwable t) {
                                Log.e("responseCode","failure");
                            }

                        });
                    }


                }
            });

        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);

            headerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expanded = !expanded;
                    headerHolder.imgArrow.setImageResource(
                            expanded ? R.drawable.ic_keyboard_arrow_up_black_18dp : R.drawable.ic_keyboard_arrow_down_black_18dp
                    );
                    sectionAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView tvTitle;
        private final ImageView imgArrow;

        HeaderViewHolder(View view) {
            super(view);

            rootView = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            imgArrow = (ImageView) view.findViewById(R.id.imgArrow);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgLogo;
        private final TextView tvItem;
        private final LinearLayout imgDownload;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgLogo = (ImageView) view.findViewById(R.id.logo_img);
            tvItem = (TextView) view.findViewById(R.id.content_text);
            imgDownload= view.findViewById(R.id.haveBtn);
        }
    }
}
