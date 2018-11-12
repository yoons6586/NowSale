package activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yoonsung.nowsale.Config;
import com.example.yoonsung.nowsale.R;
import com.example.yoonsung.nowsale.RegisterCompleteDialog;

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

public class RegisterCouponActivity extends AppCompatActivity {
    private Button register_btn;
    private EditText edit_content,edit_date,edit_cnt;
    private Intent intent;
    private int coupon_key;
    private String coupon_content,coupon_expired_date,coupon_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_coupon);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        register_btn = findViewById(R.id.register_Btn);

        intent = getIntent();
//        coupon_key = intent.getStringArrayListExtra("coupon_key");
        coupon_key = intent.getIntExtra("couponKey",0);
        Log.e("쿠폰키","쿠폰키 : "+coupon_key);

        edit_content=findViewById(R.id.content);
        edit_cnt=findViewById(R.id.cnt);
        edit_date=findViewById(R.id.date);

        register_btn.setOnClickListener(new View.OnClickListener() { // 쿠폰등록하기
            @Override
            public void onClick(View view) { // coupon_key랑 editText에서 받아온 coupon_content서버로 보낼 것임.
                //coupon_key랑 editText에서 받아온 쿠폰 목록 때려야 됨.

                new RegisterCouponActivity.SendPostJSON().execute(Config.url+"/registerCoupon");
                startActivity(new Intent(RegisterCouponActivity.this,RegisterCompleteDialog.class));
                finish();
            }
        });
    }
    public class SendPostJSON extends AsyncTask<String, String, String> { // register coupon
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                coupon_content=edit_content.getText().toString();
                coupon_count=edit_cnt.getText().toString();
                coupon_expired_date=edit_date.getText().toString();


                JSONObject jsonObject = new JSONObject();
//                Log.e("윤성",coupon1_content);
                jsonObject.accumulate("coupon_key", coupon_key);
                jsonObject.accumulate("coupon_content", coupon_content);
                jsonObject.accumulate("coupon_count", coupon_count);
                jsonObject.accumulate("coupon_expired_date", coupon_expired_date);
//                jsonObject.accumulate("coupon1_key", coupon_key.get(0));
//                jsonObject.accumulate("coupon2_key", coupon_key.get(1));
//                jsonObject.accumulate("timeattack_key", coupon_key.get(2));


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
