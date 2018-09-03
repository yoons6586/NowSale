package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
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

public class OwnerInfoActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent intent;
    private int couponKey;
    private TextView nameText;
    private TextView phoneText;
    private TextView introduceText,addressText;
    private ImageView heart;
    private int owner_key=0;
    private Boolean favBool = false; // 즐겨찾기면 true 즐겨찾기 아니면 false
    private SliderOwnerImageAdapter adapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);

        viewPager = (ViewPager) findViewById(R.id.view);
        adapter = new SliderOwnerImageAdapter(this);
        viewPager.setAdapter(adapter);

        nameText=(TextView)findViewById(R.id.marketName);
        phoneText=(TextView)findViewById(R.id.marketPhone);
        introduceText=findViewById(R.id.marketIntroduce);
        addressText=findViewById(R.id.marketAddress);
//        heart = findViewById(R.id.heart);
        intent=getIntent();
        couponKey=intent.getIntExtra("CouponKey",0);
//        Log.e("ownerInfoCouponKey",""+couponKey);
        new OwnerInfoActivity.JSONTask().execute(Config.url+"/showMarketInfo?couponKey="+couponKey);

        /*
        즐겨찾기 등록했는지 안했는 지 확인해야됨
         */

        /*heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favBool){ // 즐겨찾기 등록된거
                    favBool=false;
//                    heart.setImageResource(R.drawable.blackheart);
                    new OwnerInfoActivity.SendPostJSON().execute(Config.url+"/favoriteDelete");
                }
                else{ // 즐겨찾기 안되어 있음
                    favBool=true;
//                    heart.setImageResource(R.drawable.colorheart);
                    new OwnerInfoActivity.SendPostJSON().execute(Config.url+"/favoriteRegister");
                }
            }
        });*/
    }
    public class SendPostJSON extends AsyncTask<String, String, String> { // register coupon
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
//                Log.e("윤성",coupon1_content);
                jsonObject.accumulate("client_key", Config.clientInfoData.getUser_key());
                jsonObject.accumulate("owner_key", owner_key);


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
                    Log.e("서버",buffer.toString());
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



    public class JSONTask extends AsyncTask<String, String, String> {
        //AsyncTask이용하는 이유는 UI변경을 위해서이다. 안드로이드는 UI를 담당하는 메인 스레드가 존재하는데 우리가 만든 스레드에서는 화면을 바꾸는 일이 불가능하기 때
        //이러한 이유로 안드로이드는 Background 작업을 할 수 있도록 AsyncTask를 지원한다.

        @Override
        protected String doInBackground(String... urls) {
            try{
                JSONObject jsonObject = new JSONObject();

//                jsonObject.accumulate("user_key",1);

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
                Log.e("윤성","result : "+jarray);

                JSONObject jObject = jarray.getJSONObject(0);
                owner_key = jObject.getInt("user_key");
                Log.e("즐겨찾기","int : "+owner_key);
//                OwnerInfoActivity.this.owner_key = Integer.toString(owner_key);
                String market_name = jObject.getString("market_name");
                String address = jObject.getString("address");
                String introduce = jObject.getString("introduce");
                String phone =jObject.getString("phone");
                Log.e("OnwerInfoActivity","market_name : "+market_name);
                Log.e("OnwerInfoActivity","owner_key : "+Config.clientInfoData.getWho_key());

                nameText.setText(market_name);
                addressText.setText(address);
                introduceText.setText(introduce);
                phoneText.setText(phone);



            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}

