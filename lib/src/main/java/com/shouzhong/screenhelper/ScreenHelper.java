package com.shouzhong.screenhelper;

import android.app.Application;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ScreenHelper {

    private static final int TYPE_CLOSE = 0;
    private static final int TYPE_WIDTH = 1;
    private static final int TYPE_HEIGHT = 2;

    private static List<Field> sMetricsFields;
    private static Application app;

    public static Resources adaptWidth(Resources resources, int designWidth) {
        applyDisplayMetrics(resources, TYPE_WIDTH, designWidth);
        return resources;
    }

    public static Resources adaptHeight(Resources resources, int designHeight) {
        applyDisplayMetrics(resources, TYPE_HEIGHT, designHeight);
        return resources;
    }

    public static Resources closeAdapt(Resources resources) {
        applyDisplayMetrics(resources, TYPE_CLOSE, 0);
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

    private static void applyDisplayMetrics(Resources resources, int type, int designSize) {
        try {
            DisplayMetrics dm = resources.getDisplayMetrics();
            dm.xdpi = getXdpi(dm, type, designSize);
        } catch (Exception e) {}
        if (sMetricsFields == null) {
            try {
                sMetricsFields = new ArrayList<>();
                Class resCls = resources.getClass();
                Field[] declaredFields = resCls.getDeclaredFields();
                while (declaredFields != null && declaredFields.length > 0) {
                    for (Field field : declaredFields) {
                        if (field.getType().isAssignableFrom(DisplayMetrics.class)) {
                            try {
                                field.setAccessible(true);
                                DisplayMetrics tmpDm = (DisplayMetrics) field.get(resources);
                                if (tmpDm != null) sMetricsFields.add(field);
                            } catch (Exception e) {}
                        }
                    }
                    resCls = resCls.getSuperclass();
                    if (resCls != null) {
                        declaredFields = resCls.getDeclaredFields();
                    } else {
                        break;
                    }
                }
            } catch (Exception e) {}
        }
        for (Field field : sMetricsFields) {
            try {
                DisplayMetrics dm = (DisplayMetrics) field.get(resources);
                dm.xdpi = getXdpi(dm, type, designSize);
            } catch (Exception e) { }
        }
    }

//    private static DisplayMetrics getDisplayMetrics(Resources resources) {
//        try {
//            Field mTmpMetrics = Resources.class.getDeclaredField("mTmpMetrics");
//            mTmpMetrics.setAccessible(true);
//            return (DisplayMetrics) mTmpMetrics.get(resources);
//        } catch (Exception e) {
//            return resources.getDisplayMetrics();
//        }
//    }

    private static float getXdpi(DisplayMetrics dm, int type, int designSize) {
        return type == TYPE_WIDTH ? (dm.widthPixels * 72f) / designSize : type == TYPE_HEIGHT ? (dm.heightPixels * 72f) / designSize : dm.density * 72;
    }

    private static Application getApp() {
        if (app != null) return app;
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app != null) ScreenHelper.app = (Application) app;
        } catch (Exception e) { }
        return app;
    }
}
