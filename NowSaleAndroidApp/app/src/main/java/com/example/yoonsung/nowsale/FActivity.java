package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yoonsung.nowsale.VO.ClientCouponVO;
import com.example.yoonsung.nowsale.VO.ClientSaleVO;
import com.example.yoonsung.nowsale.VO.CouponVO;
import com.example.yoonsung.nowsale.VO.IsFavoriteGetCountVO;
import com.example.yoonsung.nowsale.VO.OwnerCouponVO;
import com.example.yoonsung.nowsale.VO.OwnerSaleVO;
import com.example.yoonsung.nowsale.http.AllService;
import com.example.yoonsung.nowsale.http.ClientService;
import com.example.yoonsung.nowsale.http.OwnerService;

import java.util.ArrayList;
import java.util.List;

import activity.OwnerInfoActivity;
import cz.msebera.android.httpclient.HttpStatus;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    List<CouponVO> couponDatas = new ArrayList<CouponVO>();
    List<CouponVO> saleDatas = new ArrayList<CouponVO>();
    List<CouponVO> marketDatas = new ArrayList<CouponVO>();
    private SectionedRecyclerViewAdapter sectionAdapter;
    private String category;
    private Intent marketInfo_intent;
    private Context context;
    private int what;
    private ClientService clientService;
    private OwnerService ownerService;
    private AllService allService;
    private View view;
    private int ownerInfoOwnerKey;
    private SwipeRefreshLayout swipeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ex4, container, false);

        what = getArguments().getInt("what");

        sectionAdapter = new SectionedRecyclerViewAdapter();
        marketInfo_intent = new Intent(getActivity(), OwnerInfoActivity.class);

        context = container.getContext();
        Log.e("what","FActivity = "+what);
        allService = Config.retrofit.create(AllService.class);
        clientService = Config.retrofit.create(ClientService.class);
        ownerService = Config.retrofit.create(OwnerService.class);


        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);


        LoadingAnimationApplication.getInstance().progressON(getActivity(), Config.loadingContext);
        switch(what){
            case 1 : // category coupon에서 들어오는 것
                //안드로이드 로딩 애니메이션



                category=getArguments().getString("category");

                Call<List<CouponVO>> couponRequest = allService.getCategoryCoupon(category);
                couponRequest.enqueue(new Callback<List<CouponVO>>() {
                    @Override
                    public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                        List<CouponVO> couponVOList = response.body();
                        couponDatas = couponVOList;

                        sectionAdapter.addSection(new ExpandableContactsSection("다운가능한 쿠폰", couponDatas,marketInfo_intent,1));

                        Call<List<CouponVO>> saleRequset = allService.getCategorySale(category);
                        saleRequset.enqueue(new Callback<List<CouponVO>>() {
                            @Override
                            public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                                List<CouponVO> couponVOList = response.body();
                                saleDatas = couponVOList;

                                sectionAdapter.addSection(new ExpandableContactsSection("진행중인 할인행사", saleDatas,marketInfo_intent,2));

                                Call<List<CouponVO>> marketRequest = allService.getCategoryMarket(category);
                                marketRequest.enqueue(new Callback<List<CouponVO>>() {
                                    @Override
                                    public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                                        //로딩 애니메이션 종료
                                        LoadingAnimationApplication.getInstance().progressOFF();

                                        List<CouponVO> marketVOList = response.body();
                                        marketDatas = marketVOList;

                                        sectionAdapter.addSection(new ExpandableContactsSection("등록된 가게", marketDatas,marketInfo_intent,3));
                                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                        recyclerView.setAdapter(sectionAdapter);

                                    }

                                    @Override
                                    public void onFailure(Call<List<CouponVO>> call, Throwable t) {

                                    }
                                });


                            }

                            @Override
                            public void onFailure(Call<List<CouponVO>> call, Throwable t) {

                            }

                        });

                    }

                    @Override
                    public void onFailure(Call<List<CouponVO>> call, Throwable t) {

                    }

                });

                break;
            case 2 : // client coupon list

                Call<List<CouponVO>> clientCouponRequest = clientService.getClientCouponList(Config.clientVO.getClient_key());
                clientCouponRequest.enqueue(new Callback<List<CouponVO>>() {
                    @Override
                    public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                        LoadingAnimationApplication.getInstance().progressOFF();
                        List<CouponVO> couponVOList = response.body();
                        couponDatas = couponVOList;

                        sectionAdapter.addSection(new FActivity.ExpandableContactsSection("쿠폰 목록", couponDatas,marketInfo_intent,1));


                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(sectionAdapter);
//                        LoadingAnimationApplication.getInstance().progressOFF();

                    }

                    @Override
                    public void onFailure(Call<List<CouponVO>> call, Throwable t) {

                    }

                });

                break;
            case 3 : // client sale list
                Call<List<CouponVO>> clientSaleRequest = clientService.getClientSaleList(Config.clientVO.getClient_key());
                clientSaleRequest.enqueue(new Callback<List<CouponVO>>() {
                    @Override
                    public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                        LoadingAnimationApplication.getInstance().progressOFF();
                        List<CouponVO> saleVOList = response.body();
                        saleDatas = saleVOList;

                        sectionAdapter.addSection(new FActivity.ExpandableContactsSection("할인 정보", saleDatas,marketInfo_intent,2));


                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(sectionAdapter);
//                        LoadingAnimationApplication.getInstance().progressOFF();

                    }

                    @Override
                    public void onFailure(Call<List<CouponVO>> call, Throwable t) {

                    }

                });

                break;
            case 4 : // 단골 가게 목록
                Call<List<CouponVO>> clientFavoriteMarketRequest = clientService.getClientFavoriteMarket(Config.clientVO.getClient_key());
                clientFavoriteMarketRequest.enqueue(new Callback<List<CouponVO>>() {
                    @Override
                    public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                        LoadingAnimationApplication.getInstance().progressOFF();
                        List<CouponVO> marketVOList = response.body();
                        marketDatas = marketVOList;

                        sectionAdapter.addSection(new FActivity.ExpandableContactsSection("단골 매장", marketDatas,marketInfo_intent,3));

                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(sectionAdapter);
//                        LoadingAnimationApplication.getInstance().progressOFF();

                    }

                    @Override
                    public void onFailure(Call<List<CouponVO>> call, Throwable t) {

                    }

                });
                break;
            case 5: // 점주가 등록한 쿠폰 및 행사
                Call<List<CouponVO>> ownerCouponRequest = ownerService.getRegisteredCoupon(Config.ownerVO.getOwner_key());
                ownerCouponRequest.enqueue(new Callback<List<CouponVO>>() {
                    @Override
                    public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                        Log.e("response","coupon : "+response.code());
                        List<CouponVO> couponVOList = response.body();
                        couponDatas = couponVOList;
//                        for(int i=0;i<couponDatas.size();i++){
//                            Log.e("coupon",couponDatas.get(i).getQualification());
//                        }
                        sectionAdapter.addSection(new FActivity.ExpandableContactsSection("다운가능한 쿠폰",couponDatas,marketInfo_intent,1));

                        Call<List<CouponVO>> ownerSaleRequest = ownerService.getRegisteredSale(Config.ownerVO.getOwner_key());
                        ownerSaleRequest.enqueue(new Callback<List<CouponVO>>() {
                            @Override
                            public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                                LoadingAnimationApplication.getInstance().progressOFF();
                                Log.e("response","sale : "+response.code());
                                List<CouponVO> saleVOList = response.body();
                                saleDatas = saleVOList;

                                sectionAdapter.addSection(new FActivity.ExpandableContactsSection("진행중인 할인행사",saleDatas,marketInfo_intent,2));
                                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setAdapter(sectionAdapter);
//                                LoadingAnimationApplication.getInstance().progressOFF();

                            }

                            @Override
                            public void onFailure(Call<List<CouponVO>> call, Throwable t) {

                            }
                        });



                    }

                    @Override
                    public void onFailure(Call<List<CouponVO>> call, Throwable t) {
                        Log.e("response","failure");

                    }

                });
                break;
            case 6: // 매장 정보로 들어가서 한 매장의 쿠폰 및 할인소식을 보는 것
                ownerInfoOwnerKey = getArguments().getInt("ownerInfoOwnerKey");
                Call<List<CouponVO>> ownerInfoCouponRequest = ownerService.getRegisteredCoupon(ownerInfoOwnerKey);
                ownerInfoCouponRequest.enqueue(new Callback<List<CouponVO>>() {
                    @Override
                    public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                        Log.e("response","coupon : "+response.code());
                        List<CouponVO> couponVOList = response.body();
                        couponDatas = couponVOList;
//                        for(int i=0;i<couponDatas.size();i++){
//                            Log.e("coupon",couponDatas.get(i).getQualification());
//                        }
                        sectionAdapter.addSection(new FActivity.ExpandableContactsSection("다운가능한 쿠폰",couponDatas,marketInfo_intent,1));

                        Call<List<CouponVO>> ownerInfoSaleRequest = ownerService.getRegisteredSale(ownerInfoOwnerKey);
                        ownerInfoSaleRequest.enqueue(new Callback<List<CouponVO>>() {
                            @Override
                            public void onResponse(Call<List<CouponVO>> call, Response<List<CouponVO>> response) {
                                LoadingAnimationApplication.getInstance().progressOFF();
                                Log.e("response","sale : "+response.code());
                                List<CouponVO> saleVOList = response.body();
                                saleDatas = saleVOList;

                                sectionAdapter.addSection(new FActivity.ExpandableContactsSection("진행중인 할인행사",saleDatas,marketInfo_intent,2));
                                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setAdapter(sectionAdapter);

                            }

                            @Override
                            public void onFailure(Call<List<CouponVO>> call, Throwable t) {

                            }
                        });



                    }

                    @Override
                    public void onFailure(Call<List<CouponVO>> call, Throwable t) {
                        Log.e("response","failure");

                    }

                });
                break;
        }



        return view;
    }

    @Override
    public void onRefresh() {
        Log.e("FACtivity","onRefresh()");
        swipeLayout.setRefreshing(true);
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
//        ((FMainActivity)getActivity()).refresh();


        swipeLayout.setRefreshing(false);
    }

    private class ExpandableContactsSection extends StatelessSection {

        String title;
        List<CouponVO> list;
        boolean expanded = true;
        Intent intent;
        int coupon_sale_normal;

        ExpandableContactsSection(String title, List<CouponVO> list,Intent intent,int coupon_sale_normal) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.list_row_coupon_wallet)
                    .headerResourceId(R.layout.section_ex4_header)
                    .build());
            this.title = title;
            this.list = list;
            this.intent = intent;
            this.coupon_sale_normal=coupon_sale_normal;
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

            String content = list.get(position).getContent();
            String marketName = list.get(position).getMarket_name();
            final int coupon_key = list.get(position).getCoupon_key();
            final int sale_key = list.get(position).getSale_key();
            String startDate = list.get(position).getStart_date();
            String expireDate = list.get(position).getExpire_date();
            String qualfication = list.get(position).getQualification();
            final int remainCount = list.get(position).getRemain_count();
            final int startCount = list.get(position).getStart_count();

            if(coupon_sale_normal==3) {
                itemHolder.contentItem.setText(marketName);
                itemHolder.dateItem.setVisibility(View.GONE);
                itemHolder.remainCountItem.setVisibility(View.GONE);
                itemHolder.qualificationItem.setVisibility(View.GONE);
            }
            else if(coupon_sale_normal==2){
                itemHolder.contentItem.setText(content);
                itemHolder.dateItem.setText(startDate+" ~ "+expireDate);
                itemHolder.qualificationItem.setText(qualfication);
                itemHolder.remainCountItem.setVisibility(View.GONE);
            }
            else if(coupon_sale_normal==1) {
                itemHolder.contentItem.setText(content);
                itemHolder.dateItem.setText(startDate+" ~ "+expireDate+" / ");
                itemHolder.remainCountItem.setText(remainCount+"개 남음");
                itemHolder.qualificationItem.setText(qualfication);
            }
            if(what==1 || what==6){
                switch (coupon_sale_normal){
                    case 1:
                        itemHolder.imgDownload.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        itemHolder.imgHeart.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        itemHolder.imgGo.setVisibility(View.VISIBLE);
                        break;
                }
            }
            else if(what==5){ // 점주가 등록한 쿠폰 및 행사
                switch (coupon_sale_normal){
                    case 1:
                        itemHolder.deleteOwnerCouponLayout.setVisibility(View.VISIBLE);
                        itemHolder.remainOwnerRegisteredCouponCount.setText(""+remainCount);
                        itemHolder.startOwnerRegisteredCouponCount.setText(""+startCount);
                        break;
                    case 2:
                        itemHolder.deleteLayout.setVisibility(View.VISIBLE);
                        break;
                }

            }
//            itemHolder.imgLogo.setImageResource(name.hashCode() % 2 == 0 ? R.drawable.logo1 : R.drawable.logo2);
            Glide.with(getActivity()).load(Config.url+"/drawable/owner/"+list.get(position).getLogo_img()).into(itemHolder.imgLogo);

            if(what!=6) {
                itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        itemHolder.rootView.setBackground(getResources().getDrawable(R.color.cursorColor));
                    }*/
                        category = list.get(position).getCategory();
                        intent.putExtra("category", category);
                        intent.putExtra("CouponVO", list.get(position));
                        intent.putExtra("position",position);
                        Log.e("owner_key", "" + list.get(position).getOwner_key());
                        Log.e("client_key", "" + Config.clientVO.getClient_key());
                        AllService service = Config.retrofit.create(AllService.class);
                        Call<IsFavoriteGetCountVO> request = service.isFavoriteGetCount(list.get(position).getOwner_key(), Config.clientVO.getClient_key());
                        request.enqueue(new Callback<IsFavoriteGetCountVO>() {
                            @Override
                            public void onResponse(Call<IsFavoriteGetCountVO> call, Response<IsFavoriteGetCountVO> response) {
//                            response.body();
                                Log.e("response", "count : " + response);
//                            List<IsFavoriteGetCountVO> list = response.body();
                                IsFavoriteGetCountVO isFavoriteGetCountVO = response.body();
                                Log.e("count", "count : " + isFavoriteGetCountVO.getDangol_count());
                                intent.putExtra("dangol", isFavoriteGetCountVO);
                                if(what!=4) {
                                    Log.e("Factivity","startactivity 실행");
                                    startActivity(intent);
                                }
                                else {
                                    Log.e("Factivity","startactivityForResult 실행");
                                    intent.putExtra("what", 4);
                                    startActivityForResult(intent, 4);
                                }
                            }

                            @Override
                            public void onFailure(Call<IsFavoriteGetCountVO> call, Throwable t) {

                            }
                        });


                    }
                });
            }
            switch(what){ // 카테고리에 따른 리스트를 보여주는 것 / 쿠폰 지갑을 보여주는 것 / 찜한 할인목록을 보여주는 것
                case 6 :
                case 1 :
                    itemHolder.imgDownload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),LoginCancelPopupActivity.class);
                            if(Config.clientVO.getClient_key() == 0){
                                if(Config.ownerVO.getOwner_key()!=0)
                                    intent.putExtra("down",1);
                                else
                                    intent.putExtra("down",0);
                                startActivity(intent);
                            }
                            else {
                                ClientService service = Config.retrofit.create(ClientService.class);
                                Call<ClientCouponVO> request = service.insertClientCouponList(new ClientCouponVO(Config.clientVO.getClient_key(), coupon_key));
                                request.enqueue(new Callback<ClientCouponVO>() {
                                    @Override
                                    public void onResponse(Call<ClientCouponVO> call, Response<ClientCouponVO> response) {
                                        Log.e("responseCode",""+response.code());
                                        if (response.code() == HttpStatus.SC_OK) {
                                            Log.e("클라이언트 쿠폰 중복", "중복 아니다");
                                            Toast.makeText(getContext(),
                                                    String.format("발급되었습니다.",
                                                            sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition()),
                                                            title),
                                                    Toast.LENGTH_SHORT).show();
                                            itemHolder.remainCountItem.setText(remainCount-1+"개 남음");
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
                                    public void onFailure(Call<ClientCouponVO> call, Throwable t) {
                                        Log.e("responseCode","failure");
                                    }

                                });
                            }


                        }
                    });
                    itemHolder.imgHeart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),LoginCancelPopupActivity.class);
                            if(Config.clientVO.getClient_key() == 0){
                                if(Config.ownerVO.getOwner_key()!=0)
                                    intent.putExtra("down",1);
                                else
                                    intent.putExtra("down",0);
                                startActivity(intent);
                            }
                            else {
                                ClientService service = Config.retrofit.create(ClientService.class);
                                Call<String> request = service.insertClientSaleList(new ClientSaleVO(Config.clientVO.getClient_key(), sale_key));
                                request.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Log.e("responseCode",""+response.code());
                                        if (response.code() == HttpStatus.SC_OK) {
                                            Log.e("클라이언트 할인제품 중복", "중복 아니다");
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
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.e("responseCode","failure");
                                    }

                                });
                            }
                        }
                    });
                    break;

                case 2 :
                    itemHolder.useItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),ClientCouponListPopupActivity.class);
                            intent.putExtra("position",position);
                            intent.putExtra("what",11); // 쿠폰 사용
//                    startActivityForResult(new Intent(getActivity(),ClientCouponListPopupActivity.class),1);
                            startActivityForResult(intent,11);
                        }
                    });
                    itemHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),ClientCouponListPopupActivity.class);
                            intent.putExtra("position",position);
                            intent.putExtra("what",12); // 쿠폰 삭제
                            startActivityForResult(intent,12);
                   /* Toast.makeText(getContext(),
                            String.format("쿠폰이 삭제되었습니다.",
                                    sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition()),
                                    title),
                            Toast.LENGTH_SHORT).show();*/
                        }
                    });
                    break;

                case 3 : // client 할인 삭제
                    itemHolder.deleteSale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),ClientCouponListPopupActivity.class);
                            intent.putExtra("position",position);
                            intent.putExtra("what",13);
                            startActivityForResult(intent,13);
                        }
                    });
                    break;
                case 4: // 단골 가게 목
                   break;
                case 5: // 점주가 등록한 할인 및 쿠폰 목록
                    itemHolder.deleteOwnerCoupon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),ClientCouponListPopupActivity.class);
                            intent.putExtra("position",position);
                            intent.putExtra("what",51); // 삭제
                            startActivityForResult(intent,51);
                        }
                    });
                    itemHolder.deleteSale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),ClientCouponListPopupActivity.class);
                            intent.putExtra("position",position);
                            intent.putExtra("what",52); // 삭제
                            startActivityForResult(intent,52);
                        }
                    });
                    break;
            }


        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);
            switch(coupon_sale_normal){
                case 1:
                    headerHolder.tvImage.setImageDrawable(getResources().getDrawable(R.drawable.coupon));
                    break;
                case 2:
                    headerHolder.tvImage.setImageDrawable(getResources().getDrawable(R.drawable.sale));
                    break;
                case 3:
                    headerHolder.tvImage.setImageDrawable(getResources().getDrawable(R.drawable.market));
                    break;
            }
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
        private final ImageView tvImage;

        HeaderViewHolder(View view) {
            super(view);

            rootView = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            imgArrow = (ImageView) view.findViewById(R.id.imgArrow);
            tvImage = (ImageView) view.findViewById(R.id.tvImage);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgLogo;
        private final TextView contentItem,useItem,deleteItem,dateItem,remainCountItem,qualificationItem,remainOwnerRegisteredCouponCount,deleteOwnerCoupon,startOwnerRegisteredCouponCount,deleteSale;
        private final LinearLayout useCancelLayout,deleteLayout,deleteOwnerCouponLayout;
        private final RelativeLayout imgDownload,imgHeart,imgGo;

        ItemViewHolder(View view) {
            super(view);

            useItem = view.findViewById(R.id.useItem);
            deleteItem = view.findViewById(R.id.deleteItem);
            rootView = view;
            imgLogo = (ImageView) view.findViewById(R.id.logo_img);
            imgGo = view.findViewById(R.id.goBtn);
            contentItem = (TextView) view.findViewById(R.id.content_text);
            imgHeart=view.findViewById(R.id.heartBtn);
            dateItem = view.findViewById(R.id.date_text);
            imgDownload= view.findViewById(R.id.haveBtn);
            useCancelLayout = view.findViewById(R.id.useCancelLayout);
            deleteLayout = view.findViewById(R.id.deleteLayout);
            deleteOwnerCouponLayout = view.findViewById(R.id.deleteOwnerCouponLayout);
            remainCountItem = view.findViewById(R.id.count_text);
            qualificationItem =view.findViewById(R.id.qualification_text);
            remainOwnerRegisteredCouponCount=view.findViewById(R.id.remainOwnerRegisteredCouponCount);
            startOwnerRegisteredCouponCount=view.findViewById(R.id.startOwnerRegisteredCouponCount);
            deleteOwnerCoupon = view.findViewById(R.id.deleteOwnerCoupon);
            deleteSale = view.findViewById(R.id.deleteSale);

            switch (what){
                case 1:

                    break;
                case 2:
                    useCancelLayout.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    deleteLayout.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    imgGo.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==11){ // 고객이 쿠폰 사용
                int p = data.getIntExtra("position",-1);
                int coupon_key = couponDatas.get(p).getCoupon_key();
                int client_key = Config.clientVO.getClient_key();
                Log.e("position","position = "+client_key);
                couponDatas.remove(p);

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(sectionAdapter);

                ClientService service = Config.retrofit.create(ClientService.class);
                Call<ClientCouponVO> request = service.useClientCouponList(new ClientCouponVO(client_key,coupon_key));
                request.enqueue(new Callback<ClientCouponVO>() {
                    @Override
                    public void onResponse(Call<ClientCouponVO> call, Response<ClientCouponVO> response) {
                        if(response.code()== HttpStatus.SC_OK){
                            /*Toast.makeText(getContext(),
                                    String.format("쿠폰 사용 완료"),
                                    Toast.LENGTH_SHORT).show();*/
                        }
                    }

                    @Override
                    public void onFailure(Call<ClientCouponVO> call, Throwable t) {

                    }

                });
            }
            else if(requestCode==12){  // 고객이 쿠폰을 사용하지 않고 삭제함
                int p = data.getIntExtra("position",-1);
                int coupon_key = couponDatas.get(p).getCoupon_key();
                int client_key = Config.clientVO.getClient_key();
                Log.e("position","position = "+client_key);
                couponDatas.remove(p);

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(sectionAdapter);

                ClientService service = Config.retrofit.create(ClientService.class);
                Call<ClientCouponVO> request = service.deleteClientCouponList(new ClientCouponVO(client_key,coupon_key));
                request.enqueue(new Callback<ClientCouponVO>() {
                    @Override
                    public void onResponse(Call<ClientCouponVO> call, Response<ClientCouponVO> response) {
                        if(response.code()== HttpStatus.SC_OK){
                            /*Toast.makeText(getContext(),
                                    String.format("쿠폰 사용 완료"),
                                    Toast.LENGTH_SHORT).show();*/
                        }
                    }

                    @Override
                    public void onFailure(Call<ClientCouponVO> call, Throwable t) {

                    }

                });
            }
            else if(requestCode==13){ // 고객이 할인 소식 삭제
                int p = data.getIntExtra("position",-1);
                int sale_key = saleDatas.get(p).getSale_key();
                saleDatas.remove(p);

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(sectionAdapter);

                ClientService service = Config.retrofit.create(ClientService.class);
                Call<ClientSaleVO> request = service.deleteClientSaleList(new ClientSaleVO(Config.clientVO.getClient_key(),sale_key));
                request.enqueue(new Callback<ClientSaleVO>() {
                    @Override
                    public void onResponse(Call<ClientSaleVO> call, Response<ClientSaleVO> response) {
                        if(response.code()== HttpStatus.SC_OK){
                            Log.e("delete","고객 할인정보 삭제");
                        }
                    }

                    @Override
                    public void onFailure(Call<ClientSaleVO> call, Throwable t) {

                    }
                });


            }
            else if(requestCode==51){ // 점주가 쿠폰 삭제 명령을 내린 것
                int p = data.getIntExtra("position",-1);
                int coupon_key = couponDatas.get(p).getCoupon_key();
                couponDatas.remove(p);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(sectionAdapter);

                OwnerService ownerService = Config.retrofit.create(OwnerService.class);
                Call<OwnerCouponVO> request = ownerService.deleteOwnerCouponList(new OwnerCouponVO(Config.ownerVO.getOwner_key(),coupon_key));
                request.enqueue(new Callback<OwnerCouponVO>() {
                    @Override
                    public void onResponse(Call<OwnerCouponVO> call, Response<OwnerCouponVO> response) {
                        if(response.code()== HttpStatus.SC_OK){
                            Log.e("delete","쿠폰삭제");
                        }
                    }

                    @Override
                    public void onFailure(Call<OwnerCouponVO> call, Throwable t) {

                    }
                });
            }
            else if(requestCode==52){ // 점주가 할인 정보 취소 명령을 내린 것
                int p = data.getIntExtra("position",-1);
                int sale_key = saleDatas.get(p).getSale_key();
                saleDatas.remove(p);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(sectionAdapter);

                OwnerService ownerService = Config.retrofit.create(OwnerService.class);
                Call<OwnerSaleVO> requset = ownerService.deleteOwnerSaleList(new OwnerSaleVO(Config.ownerVO.getOwner_key(),sale_key));
                requset.enqueue(new Callback<OwnerSaleVO>() {
                    @Override
                    public void onResponse(Call<OwnerSaleVO> call, Response<OwnerSaleVO> response) {
                        if(response.code()== HttpStatus.SC_OK){
                            Log.e("delete","쿠폰삭제");
                        }
                    }

                    @Override
                    public void onFailure(Call<OwnerSaleVO> call, Throwable t) {

                    }
                });
            }
            else if(requestCode==4){ // 고객이 단골가게에 들어갔다가 갱신하는 것!!
                Log.e("FACtivity","단골가게 새로 갱신");
//                LoadingAnimationApplication.getInstance().progressON(getActivity(), Config.loadingContext);
                int p = data.getIntExtra("position",-1);
                boolean fav = data.getBooleanExtra("mFav",false);
                Log.e("FActivity","position : "+p);

                if(fav) {
                    Log.e("FActivity", "mFav : true");
                    marketDatas.remove(p);
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(sectionAdapter);
                }
                else
                    Log.e("FActivity","mFav : false");
//                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.detach(FActivity.this).attach(FActivity.this).commit();

//                onRefresh();

            }
        }
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        LoadingAnimationApplication.getInstance().progressOFF();
    }
    @Override
    public void onResume(){
        super.onResume();

//        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();

    }
}
