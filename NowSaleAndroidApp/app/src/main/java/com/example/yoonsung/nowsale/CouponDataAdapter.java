package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.yoonsung.nowsale.Config.url;

public class CouponDataAdapter extends BaseAdapter{
    ArrayList<CouponData> datas;
    LayoutInflater inflater;
    private int couponKey;
    private int coupon1,coupon2,time_attack;
    private Context context;
    public CouponDataAdapter(LayoutInflater inflater, ArrayList<CouponData> datas,Context context){
        this.context = context;
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
        Log.e("CouponDataAdater",""+datas.get(position).getName());
        //NewView
        if(convertView == null){ // convertView가 null이면 새로 만들어야 됨. 왜냐하면 view가 보여지는 부분은 5개정도밖에 없기 때문에 나머지는 보여줄 필요x
            // null이라면 재활용할 View가 없으므로 새로운 객체 생
            convertView = inflater.inflate(R.layout.list_row,null);
        }

        //BindView
        //재활용을 하든 새로 만들었든 이제 convertView는 View객체 상태임.
        //이름을 표시하는 TextView, 내용을 표시하는 TextView
        TextView name_text=(TextView)convertView.findViewById(R.id.name_text);
        TextView content_text=(TextView)convertView.findViewById(R.id.content_text);


        TextView have_couponBtn=(TextView)convertView.findViewById(R.id.haveBtn);
        LinearLayout linear_Btn = (LinearLayout)convertView.findViewById(R.id.linear_id);
//        ImageView market_image= (ImageView)convertView.findViewById(R.id.market_image);

        //현재 포지션의 Data를 위 해당 View들에 연결
        name_text.setText(datas.get(position).getName());
        content_text.setText(datas.get(position).getContent());
//        couponKey_text.setText(datas.get(position).getCouponKey());
//        Log.e("쿠폰키",""+datas.get(position).getCouponKey());

        linear_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 이 쿠폰을 발급한 매장의 정보를 알려주기
//                new ShowCouponActivity.StartAc().start_A();
//                ShowCouponActivity.this.startActivity(new Intent(ShowCouponActivity.this,OwnerInfoActivity.class));
                /*
                누른 것의 쿠폰키도 보내줘야됨
                 */
                Intent intent = new Intent(context,OwnerInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Log.e("쿠폰키",""+datas.get(position).getCouponKey());
                intent.putExtra("CouponKey", datas.get(position).getCouponKey());
                context.startActivity(intent);

            }
        });

        have_couponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                couponKey = datas.get(position).getCouponKey();
                Log.e("윤성",datas.get(position).getName());
//                Log.e("윤성","user_key : "+user_key);

                Log.e("윤성","coupon_key : "+couponKey);
//                Log.e("윤성","who_key:"+who_key);

                if(Config.clientInfoData.getWho_key().charAt(0)=='C'){ // 등록이 되게끔 설정.
                    //get으로 내 쿠폰 데이터 가져온다음 등록이 가능한지 알아보고 넣어야됨.
                    //취소일때랑 취소 아닐때랑 구분해야됨 -> 존나 복잡함 죽고싶음
                    //coupon_key를 가져와야 됨.
                    //디비에서 내가 가지고 있는 쿠폰 목록 가져오자
                    new HaveClientCouponJSON().execute(url+"/client/coupon/get/"+Config.clientInfoData.getUser_key());
                    /*
                    이 부분 HaveClientCouponJSON이 먼저 실행되야 되는데 그전에 coupon1이 실행되어버림 그래서 오류
                    */

                }
                else{
                    // 점주는 안된다고 띄어주어야 함.
                }
            }
        });
//        market_image.setImageResource(datas.get(position).getImgId());

        return convertView;
    }
    public class HaveClientCouponJSON extends AsyncTask<String, String, String>{ // register coupon
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
            Log.e("서민","coupon1 : "+couponKey);
            Log.e("서민","coupon1 : "+coupon1);
            Log.e("서민","coupon1 : "+coupon2);
            Log.e("서민","coupon1 : "+time_attack);
            if(couponKey == coupon1){ // 찬란한 하트
                coupon1=-1;
            }
            else if(couponKey ==coupon2){
                coupon2=-1;
            }
            else if(couponKey == time_attack){
                time_attack=-1;
            }
            else{ // 쿠폰에 등록되어있지 않음 -> 지금 블랙하트일 것임
                if(coupon1==-1){ // 컬러하트로 바꿔줘야됨
                    coupon1 = couponKey;
                }
                else if(coupon2==-1){
                    coupon2=couponKey;
                }
                else{
                    //꽉찼으니 지우고 해라~
                }
            }
            new SendClientCouponJSON(coupon1,coupon2,time_attack).execute(url+"/client/coupon/update/"+Config.clientInfoData.getUser_key());

//            Log.e("윤성",""+result); // 여기서 json데이터를 뽑는 수밖에 없을듯

//            textview.setText(result);
        }
    }

    public static class SendClientCouponJSON extends AsyncTask<String, String, String>{ // register coupon
        int coupon1,coupon2,time_attack;
        public SendClientCouponJSON(int coupon1,int coupon2,int time_attack){
            this.coupon1 = coupon1;
            this.coupon2 = coupon2;
            this.time_attack = time_attack;
        }
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.


                JSONObject jsonObject = new JSONObject();
//                Log.e("윤성",coupon1_content);
                jsonObject.accumulate("coupon1", coupon1);
                jsonObject.accumulate("coupon2", coupon2);
                jsonObject.accumulate("time_attack", time_attack);
//                jsonObject.accumulate("user_key",Config.clientInfoData.getUser_key());
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}
