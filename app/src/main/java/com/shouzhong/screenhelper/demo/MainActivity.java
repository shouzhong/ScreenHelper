package com.shouzhong.screenhelper.demo;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
            return ScreenHelper.adaptHeight(super.getResources(), 750);
        else return ScreenHelper.adaptWidth(super.getResources(), 750);
    }
}
