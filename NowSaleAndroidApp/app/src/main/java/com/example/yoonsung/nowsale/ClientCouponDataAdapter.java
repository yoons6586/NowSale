package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.yoonsung.nowsale.Config.url;


public class ClientCouponDataAdapter extends BaseAdapter{
    ArrayList<ClientCouponData> datas;
    LayoutInflater inflater;
    private Intent deleteIntent;
    private Context context;

    private int coupon1,coupon2,time_attack,couponKey;
    public ClientCouponDataAdapter(LayoutInflater inflater, ArrayList<ClientCouponData> datas){
        this.datas = datas;
        this.inflater = inflater;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i); // datas의 특정 인덱스 위치 리턴
    }

    @Override
    public long getItemId(int i) {
        return i; // data의 위치 리턴.
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //NewView
        if(convertView == null){ // convertView가 null이면 새로 만들어야 됨. 왜냐하면 view가 보여지는 부분은 5개정도밖에 없기 때문에 나머지는 보여줄 필요x
            // null이라면 재활용할 View가 없으므로 새로운 객체 생
            convertView = inflater.inflate(R.layout.client_coupon_list_row,null);
        }

        //BindView
        //재활용을 하든 새로 만들었든 이제 convertView는 View객체 상태임.
        //이름을 표시하는 TextView, 내용을 표시하는 TextView
        TextView name_text=(TextView)convertView.findViewById(R.id.name_text);
        TextView content_text=(TextView)convertView.findViewById(R.id.content_text);
        TextView cancel_couponBtn=(TextView) convertView.findViewById(R.id.couponCancelBtn);

//        ImageView market_image= (ImageView)convertView.findViewById(R.id.market_image);

        //현재 포지션의 Data를 위 해당 View들에 연결
        name_text.setText(datas.get(position).getName());
        content_text.setText(datas.get(position).getContent());

        /*deleteIntent = new Intent(context,OkCancelPopUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        deleteIntent.putExtra("title","정말 삭제하시겠습니까?");
        deleteIntent.putExtra("change","확인");
        context.startActivity(deleteIntent);*/

        cancel_couponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // start activity해서 하면 안되나??
                //삭제하라는 명령 주면 됨.
                //디비에서 삭제하라고 명령해야됨.


                couponKey =datas.get(position).getCouponKey();
                Log.e("서민","쿠폰키 : "+couponKey);
                datas.remove(position);
                notifyDataSetChanged();
                //user_key랑 쿠폰키 받으면 삭제가능.
                new HaveClientCouponJSONforDelete().execute(url+"/clientKey?user_key="+Config.clientInfoData.getUser_key());

            }
        });

//        market_image.setImageResource(datas.get(position).getImgId());

        return convertView;
    }

    public class HaveClientCouponJSONforDelete extends AsyncTask<String, String, String> { // register coupon
        @Override
        protected String doInBackground(String... urls) {
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("coupon1",0);
                jsonObject.accumulate("coupon2",0);
                jsonObject.accumulate("time_attack",0);
                HttpURLConnection con=null;
                BufferedReader reader=null;
                try{
//                    Log.e("윤성",""+urls[0]);
                    URL url = new URL(urls[0]);

                    con = (HttpURLConnection)url.openConnection();
                    con.connect();

                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line="";
                    while((line=reader.readLine())!=null){
//                        Log.e("윤성",""+line); // json을 스트링으로 뽑는 수밖에 없을 듯
                        buffer.append(line);
                    }
                    return buffer.toString();
                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    if(con!=null)
                        con.disconnect();
                    try{
                        if(reader!=null)
                            reader.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            try{
                JSONArray jarray = new JSONArray(result);

                for (int i = 0; i < jarray.length(); i++) { // json데이터를 뽑기 위함
                    JSONObject jObject = jarray.getJSONObject(i);
                    coupon1 = jObject.getInt("coupon1");
                    coupon2 = jObject.getInt("coupon2");
                    time_attack = jObject.getInt("time_attack");
                    Log.e("윤성", "get_coupon1:" + coupon1);
                }


            }catch(JSONException e){
                e.printStackTrace();
            }
            Log.e("서민","couponKey : "+couponKey);
            Log.e("서민","coupon1 : "+coupon1);
            Log.e("서민","coupon2 : "+coupon2);
            Log.e("서민","time_attack : "+time_attack);
            if(couponKey == coupon1){
                coupon1=-1;
            }
            else if(couponKey ==coupon2){
                coupon2=-1;
            }
            else if(couponKey == time_attack){
                time_attack=-1;
            }

            new CouponDataAdapter.SendClientCouponJSON(coupon1,coupon2,time_attack).execute(url+"/getClientCoupon");

//            Log.e("윤성",""+result); // 여기서 json데이터를 뽑는 수밖에 없을듯

//            textview.setText(result);
        }
    }

}
