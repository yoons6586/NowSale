package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

public class ClientMenuActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Intent get_intent;
    private int whatWallet=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        get_intent = getIntent();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        whatWallet=get_intent.getIntExtra("wallet",-1);
        if(whatWallet==0) {
            toolbarTitle.setText("쿠폰지갑");

            FActivity homeFragment = new FActivity();

            Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
            bundle.putInt("what", 2); // key , value

            homeFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment).commit();
        }
        else if(whatWallet==1) {
            toolbarTitle.setText("찜한 할인소식");
            FActivity homeFragment = new FActivity();

            Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
            bundle.putInt("what", 3); // key , value

            homeFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment).commit();
        }
        else if(whatWallet==2){
            toolbarTitle.setText("단골 가게");
            FActivity homeFragment = new FActivity();

            Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
            bundle.putInt("what", 4); // key , value

            homeFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment).commit();

        }
        else if(whatWallet==5){
            toolbarTitle.setText("내가 등록한 쿠폰 및 행사");
            FActivity homeFragment = new FActivity();

            Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
            bundle.putInt("what", 5); // key , value

            homeFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment).commit();
        }
        else{
            toolbarTitle.setText("오류");
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_example1:
                replaceFragment(new Example1Fragment());
                break;
            case R.id.nav_example2:
                replaceFragment(new Example2Fragment());
                break;
            case R.id.nav_example3:
                replaceFragment(new Example3Fragment());
                break;
            case R.id.nav_example4:
                replaceFragment(new Example4Fragment());
                break;
            case R.id.nav_example5:
                replaceFragment(new Example5Fragment());
                break;
            case R.id.nav_example6:
                replaceFragment(new Example6Fragment());
                break;
            case R.id.nav_example7:
                replaceFragment(new Example7Fragment());
                break;
            case R.id.nav_example8:
                replaceFragment(new Example8Fragment());
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
*/
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
