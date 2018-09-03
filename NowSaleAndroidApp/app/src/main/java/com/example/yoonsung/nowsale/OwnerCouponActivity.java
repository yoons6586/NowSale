package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class OwnerCouponActivity extends AppCompatActivity {
    TextView coupon1_owner;
    TextView coupon2_owner;
    TextView timeAttack_owner;
    private Intent get_intent;
    private String delete_key;
    private int user_key;
//    private int[] coupon_key = new int[3];
    private Button register_Btn,allCoupon_Btn,delete1_Btn,delete2_Btn,delete3_Btn;
    private String[] coupon_content = new String[3];
    private Intent allCoupon_Intent,registerCoupon_Intent,popup_Intent;
    private ArrayList<Integer> coupon_key = new ArrayList<Integer>();
    private ArrayList<String> on_off_list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_coupon);
        coupon1_owner = findViewById(R.id.coupon1_owner);
        coupon2_owner = findViewById(R.id.coupon2_owner);
        timeAttack_owner = findViewById(R.id.timeAttack_owner);
//        allCoupon_Intent = new Intent(this,ShowCouponActivity.class);
        registerCoupon_Intent = new Intent(this,RegisterCouponActivity.class);
        popup_Intent = new Intent(this,PopUpActivity.class);

        get_intent=getIntent();
        user_key = get_intent.getIntExtra("user_key",0);

        Log.e("윤성","user_key : "+user_key);
        new OwnerCouponActivity.JSONTask().execute(url+"/showOwnerRegisterCoupon?user_key="+user_key); // 액티비티 켜지면서 자신이 등록한 쿠폰목록 뽑기
        Log.e("윤성","coupon1 : "+coupon_content[0]);
        register_Btn = findViewById(R.id.register_Btn);
        register_Btn.setOnClickListener(new View.OnClickListener() { // 쿠폰등록하기
            @Override
            public void onClick(View view) { // coupon_key랑 editText에서 받아온 coupon_content서버로 보낼 것임.ㄴ
                /*
                만약 갯수가 3개 이하면 쿠폰을 등록할 수 있고 아니면 쿠폰을 등록할 수 없게끔 짜야됨
                3개 이하면 그 쿠폰으로 보내야됨 -> 쿠폰 삭제부터 먼저 짜야됨
                 */
                for(int i=on_off_list.size()-1;i>=0;i--){
                    Log.e("on_off",on_off_list.get(i));
                    if(on_off_list.get(i).equals("F")){
                        //registerCoupon_Intent.putIntegerArrayListExtra("coupon_key",(ArrayList<Integer>)coupon_key); // 쿠폰키 한개만 넣기
                        Log.e("쿠폰키",""+coupon_key.get(i));
                        registerCoupon_Intent.putExtra("couponKey",coupon_key.get(i));

                    }
                }
                startActivityForResult(registerCoupon_Intent,2);

            }
        });
        allCoupon_Btn = findViewById(R.id.allCoupon_Btn);
        allCoupon_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allCoupon_Intent.putExtra("user_key",user_key);
                startActivity(allCoupon_Intent);
            }
        });
        delete1_Btn = findViewById(R.id.delete1);
        delete2_Btn = findViewById(R.id.delete2);
        delete3_Btn = findViewById(R.id.delete3);

        delete1_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(on_off_list.get(0).equals("T")) {
                    popup_Intent.putExtra("couponKey", coupon_key.get(0));
                    startActivityForResult(popup_Intent,1);


                }
                else{
                    /*
                    등록된 쿠폰 없다는 팝업 띄우기
                     */
                }
            }
        });
        delete2_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(on_off_list.get(1).equals("T")) {
                    popup_Intent.putExtra("couponKey", coupon_key.get(1));
                    startActivityForResult(popup_Intent,1);
                }
                else{
                    /*
                    등록된 쿠폰 없다는 팝업 띄우기
                     */
                }
            }
        });
        delete3_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(on_off_list.get(2).equals("T")) {
                    popup_Intent.putExtra("couponKey", coupon_key.get(2));
                    startActivityForResult(popup_Intent,1);
                }
                else{
                    /*
                    등록된 쿠폰 없다는 팝업 띄우기
                     */
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1){ // 삭제 한 팝업에서 올때
            if(resultCode==RESULT_OK){
                //데이터 받기
                delete_key = data.getStringExtra("KEY");
                Log.e("delete","DELETE KEY : "+delete_key);
//                    txtResult.setText(result);
                new OwnerCouponActivity.SendPostJSON().execute(Config.url+"/deleteOwnerCoupon");
//                select_doProcess();
                new OwnerCouponActivity.JSONTask().execute(url+"/showOwnerRegisterCoupon?user_key="+user_key); // 액티비티 켜지면서 자신이 등록한 쿠폰목록 뽑기
            }
        }
        else if(requestCode==2){ // 등록에서 올대
            new OwnerCouponActivity.JSONTask().execute(url+"/showOwnerRegisterCoupon?user_key="+user_key); // 액티비티 켜지면서 자신이 등록한 쿠폰목록 뽑기
        }
    }




    public class SendPostJSON extends AsyncTask<String, String, String> { // register coupon
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                String coupon_key = Integer.toString(key);

                JSONObject jsonObject = new JSONObject();
//                Log.e("윤성",coupon1_content);
                jsonObject.accumulate("delete_key", ""+delete_key);


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
    public class JSONTask extends AsyncTask<String, String, String> {
        //AsyncTask이용하는 이유는 UI변경을 위해서이다. 안드로이드는 UI를 담당하는 메인 스레드가 존재하는데 우리가 만든 스레드에서는 화면을 바꾸는 일이 불가능하기 때
        //이러한 이유로 안드로이드는 Background 작업을 할 수 있도록 AsyncTask를 지원한다.

        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("coupon_content", "androidTest");
                jsonObject.accumulate("coupon_key", "yun");
                jsonObject.accumulate("who_key", "O");
                jsonObject.accumulate("on_off","D");
//                jsonObject.accumulate("user_key",1);

                HttpURLConnection con = null;
                BufferedReader reader = null;
                coupon_key.clear();
                on_off_list.clear();
                try {
//                    Log.e("윤성",""+urls[0]);
                    URL url = new URL(urls[0]);

                    con = (HttpURLConnection) url.openConnection();
                    con.connect();

                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
//                        Log.e("윤성",""+line); // json을 스트링으로 뽑는 수밖에 없을 듯
                        buffer.append(line);
                    }
                    return buffer.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null)
                        con.disconnect();
                    try {
                        if (reader != null)
                            reader.close();
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
            try {
                JSONArray jarray = new JSONArray(result);
                Log.e("윤성", "result : " + jarray);
                for (int i = 0; i < jarray.length(); i++) { // json데이터를 뽑기 위함
                    JSONObject jObject = jarray.getJSONObject(i);
                    coupon_content[i] = jObject.getString("coupon_content");
                    int key = jObject.getInt("coupon_key");
                    String on_off = jObject.getString("on_off");
                    coupon_key.add(key);
                    on_off_list.add(on_off); // on_off_list 초기화 해줘야됨
                }
                Log.e("onoff",""+on_off_list.get(0));
                Log.e("onoff",""+on_off_list.get(1));
                Log.e("onoff",""+on_off_list.get(2));
                if(on_off_list.get(0).equals("T"))
                    coupon1_owner.setText(coupon_content[0]);
                else
                    coupon1_owner.setText("");
                if(on_off_list.get(1).equals("T"))
                    coupon2_owner.setText(coupon_content[1]);
                else
                    coupon2_owner.setText("");
                if(on_off_list.get(2).equals("T"))
                    timeAttack_owner.setText(coupon_content[2]);
                else
                    timeAttack_owner.setText("");
                /*if(on_off_list.get(0).equals("F"))
                    coupon1_owner.setText("");
                if(on_off_list.get(1).equals("F"))
                    coupon2_owner.setText("");
                if(on_off_list.get(2).equals("F"))
                    timeAttack_owner.setText("");
                */
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
