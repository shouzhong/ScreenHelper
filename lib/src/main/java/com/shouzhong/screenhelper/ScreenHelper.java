package com.shouzhong.screenhelper;

import android.app.Application;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

public class ScreenHelper {

    public static Resources adaptWidth(Resources resources, int designWidth) {
        DisplayMetrics dm = getDisplayMetrics(resources);
        float newXdpi = dm.xdpi = (dm.widthPixels * 72f) / designWidth;
        setAppDmXdpi(newXdpi);
        return resources;
    }

    public static Resources adaptHeight(Resources resources, int designHeight) {
        DisplayMetrics dm = getDisplayMetrics(resources);
        float newXdpi = dm.xdpi = (dm.heightPixels * 72f) / designHeight;
        setAppDmXdpi(newXdpi);
        return resources;
    }

    public static Resources closeAdapt(Resources resources) {
        DisplayMetrics dm = getDisplayMetrics(resources);
        float newXdpi = dm.xdpi = dm.density * 72;
        setAppDmXdpi(newXdpi);
        return resources;
    }

    public static int pt2Px(float ptValue) {
        DisplayMetrics metrics = getApp().getResources().getDisplayMetrics();
        return (int) (ptValue * metrics.xdpi / 72f + 0.5);
    }

    public static int px2Pt(float pxValue) {
        DisplayMetrics metrics = getApp().getResources().getDisplayMetrics();
        return (int) (pxValue * 72 / metrics.xdpi + 0.5);
    }

    private static void setAppDmXdpi(final float xdpi) {
        getApp().getResources().getDisplayMetrics().xdpi = xdpi;
    }

    private static DisplayMetrics getDisplayMetrics(Resources resources) {
        try {
            Field mTmpMetrics = Resources.class.getDeclaredField("mTmpMetrics");
            mTmpMetrics.setAccessible(true);
            return (DisplayMetrics) mTmpMetrics.get(resources);
        } catch (Exception e) {
            return resources.getDisplayMetrics();
        }
    }

    private static Application getApp() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) throw new NullPointerException("error");
            return (Application) app;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}
