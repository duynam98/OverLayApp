package com.example.demooverlay.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.demooverlay.R;
import com.example.demooverlay.utils.Constant;
import com.example.demooverlay.view.fragment.home.HomeFragment;

public class SplashActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        homeFragment = new HomeFragment();
        addFragmentHome();
    }

    private void addFragmentHome() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentTransaction.add(R.id.container, homeFragment);
                fragmentTransaction.commit();
            }
        }, 1000);
    }

    public void addFragmentEditImage(String patch){
        Intent intent = new Intent(this, EditImageActivity.class);
        intent.putExtra(Constant.PATCH_IMAGE, patch);
        startActivity(intent);
    }



}