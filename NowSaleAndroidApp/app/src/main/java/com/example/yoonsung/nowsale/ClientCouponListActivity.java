package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
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


public class ClientCouponListActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    ArrayList<ClientCouponData> datas = new ArrayList<ClientCouponData>();
    private ListView listview;
    private TextView textview;
    private Intent get_intent;
    private Context context;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_have_coupon);
        context = getApplicationContext();

        new ClientCouponListActivity.JSONTask().execute(url+"/showClientHaveCoupon?user_key="+Config.clientInfoData.getUser_key());
    }
    public class JSONTask extends AsyncTask<String, String, String> {
        //AsyncTask이용하는 이유는 UI변경을 위해서이다. 안드로이드는 UI를 담당하는 메인 스레드가 존재하는데 우리가 만든 스레드에서는 화면을 바꾸는 일이 불가능하기 때
        //이러한 이유로 안드로이드는 Background 작업을 할 수 있도록 AsyncTask를 지원한다.

        @Override
        protected String doInBackground(String... urls) {
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("market_name","androidTest");
                jsonObject.accumulate("coupon_content","yun");
                jsonObject.accumulate("coupon_key",0);
                jsonObject.accumulate("on_off",'T');
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
                for(int i=0;i<jarray.length();i++){ // json데이터를 뽑기 위함
                    JSONObject jObject = jarray.getJSONObject(i);
                    String name = jObject.getString("market_name");
                    String content = jObject.getString("coupon_content");
                    String on_off =jObject.getString("on_off");
                    int couponKey = jObject.getInt("coupon_key");
//                    String resName="@drawable/"+name;
//                    int resID=getResources().getIdentifier(resName,"drawable",getPackageName());
//                    Log.e("윤성","name : "+name+", content : "+content);
                    if(on_off.charAt(0)=='T')
                        datas.add(new ClientCouponData(name,content,couponKey));
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
//            Log.e("윤성",""+result); // 여기서 json데이터를 뽑는 수밖에 없을듯
            listview = (ListView)findViewById(R.id.client_coupon_list);
            ClientCouponDataAdapter adapter = new ClientCouponDataAdapter(getLayoutInflater(),datas);
            listview.setAdapter(adapter);
//            textview.setText(result);
        }
    }

}

