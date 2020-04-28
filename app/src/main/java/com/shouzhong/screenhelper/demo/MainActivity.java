package com.shouzhong.screenhelper.demo;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.shouzhong.screenhelper.ScreenHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public Resources getResources() {
        int o = super.getResources().getConfiguration().orientation;
        if (o == android.content.res.Configuration.ORIENTATION_LANDSCAPE)
            return ScreenHelper.adaptHeight(super.getResources(), 1080);
        else return ScreenHelper.adaptWidth(super.getResources(), 1080);
    }
}
