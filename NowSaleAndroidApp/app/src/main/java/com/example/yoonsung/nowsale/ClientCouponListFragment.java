package com.example.yoonsung.nowsale;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yoonsung.nowsale.VO.ClientCouponVO;
import com.example.yoonsung.nowsale.VO.CouponVO;
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

import static android.app.Activity.RESULT_OK;

public class ClientCouponListFragment extends Fragment {
    List<CouponVO>datas = new ArrayList<CouponVO>();
    private SectionedRecyclerViewAdapter sectionAdapter;

    private Intent marketInfo_intent;
    private View view;
    private ClientService service;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ex4, container, false);

        sectionAdapter = new SectionedRecyclerViewAdapter();
        marketInfo_intent = new Intent(getActivity(),OwnerInfoActivity.class);

        service = Config.retrofit.create(ClientService.class);
        Call<List<CouponVO>> request = service.getClientCouponList(Config.clientVO.getClient_key());
        request.enqueue(new Callback<List<CouponVO>>() {
            @Override
            public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                List<CouponVO> couponVOList = response.body();
                datas = couponVOList;

                sectionAdapter.addSection(new ExpandableContactsSection("쿠폰 목록", datas,marketInfo_intent));


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
                    .itemResourceId(R.layout.list_row_coupon_wallet)
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
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String name = list.get(position).getContent();

            itemHolder.tvItem.setText(name);
            itemHolder.imgLogo.setImageResource(name.hashCode() % 2 == 0 ? R.drawable.logo1 : R.drawable.logo2);
            intent.putExtra("CouponVO",list.get(position));
            intent.putExtra("category",list.get(position).getCategory());

            Glide.with(getActivity()).load(Config.url+"/drawable/owner/"+list.get(position).getLogo_img()).into(itemHolder.imgLogo);



            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    startActivity(intent);
                }
            });
            itemHolder.useItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ClientCouponListPopupActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("what",0); // 사용
//                    startActivityForResult(new Intent(getActivity(),ClientCouponListPopupActivity.class),1);
                    startActivityForResult(intent,1);
                }
            });
            itemHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ClientCouponListPopupActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("what",1); // 삭제
                    startActivityForResult(intent,1);
                   /* Toast.makeText(getContext(),
                            String.format("쿠폰이 삭제되었습니다.",
                                    sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition()),
                                    title),
                            Toast.LENGTH_SHORT).show();*/
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
        private final TextView tvItem,useItem,deleteItem;


        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgLogo = (ImageView) view.findViewById(R.id.logo_img);
            tvItem = (TextView) view.findViewById(R.id.content_text);
            useItem = view.findViewById(R.id.useItem);
            deleteItem = view.findViewById(R.id.deleteItem);
//            imgDownload=(ImageView) view.findViewById(R.id.haveBtn);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1){ // 쿠폰 사용 팝업
            if(resultCode==RESULT_OK){ // client
                //데이터 받기
                int p = data.getIntExtra("position",-1);
                int coupon_key = datas.get(p).getCoupon_key();
                int client_key = Config.clientVO.getClient_key();
                Log.e("position","position = "+client_key);
                datas.remove(p);

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(sectionAdapter);

                ClientService service = Config.retrofit.create(ClientService.class);
                Call<List<ClientCouponVO>> request = service.deleteClientCouponList(new ClientCouponVO(client_key,coupon_key));
                request.enqueue(new Callback<List<ClientCouponVO>>() {
                    @Override
                    public void onResponse(Call<List<ClientCouponVO>> call, Response<List<ClientCouponVO>> response) {
                        if(response.code()== HttpStatus.SC_OK){
                            Toast.makeText(getContext(),
                                    String.format("쿠폰 사용 완료"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClientCouponVO>> call, Throwable t) {

                    }

                });



            }
        }
    }
}
