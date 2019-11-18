package com.shouzhong.screenhelper.demo;

import android.app.Application;
import android.content.res.Resources;

import com.shouzhong.screenhelper.ScreenHelper;

public class App extends Application {

    @Override
    public Resources getResources() {
        int o = super.getResources().getConfiguration().orientation;
        if (o == android.content.res.Configuration.ORIENTATION_LANDSCAPE)
            return ScreenHelper.adaptHeight(super.getResources(), 750);
        else return ScreenHelper.adaptWidth(super.getResources(), 750);
    }
}
