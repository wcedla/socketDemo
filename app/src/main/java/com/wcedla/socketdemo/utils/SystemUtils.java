package com.wcedla.socketdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.ColorInt;


import java.util.regex.Pattern;

/**
 * @author wcedla
 */
public class SystemUtils {

    /**
     * 设置状态栏导航栏颜色等属性
     *
     * @param activity                 目标活动
     * @param darkStatusText           状态栏显示黑色字体和图标
     * @param transparentNavigationBar 是否透明导航栏
     */
    public static void setNavigationBarStatusBarTranslucent(Activity activity, boolean darkStatusText, boolean transparentNavigationBar) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (darkStatusText) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
            }
            decorView.setSystemUiVisibility(option);
            if (transparentNavigationBar) {
                activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            } else {
                activity.getWindow().setNavigationBarColor(Color.parseColor("#000000"));
            }
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置状态栏导航栏颜色等属性
     *
     * @param activity        目标活动
     * @param darkStatusText  状态栏显示黑色字体和图标
     * @param navigationColor 导航栏颜色
     */
    public static void setNavigationBarStatusBarTranslucent(Activity activity, boolean darkStatusText, @ColorInt int navigationColor) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (darkStatusText) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
            }
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setNavigationBarColor(navigationColor);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 弹出输入法
     *
     * @param targetView 目标控件
     */
    public static void showInputMethod(Context context,View targetView) {
        targetView.setFocusable(true);
        targetView.setFocusableInTouchMode(true);
        targetView.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(targetView, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏输入法
     *
     * @param targetView 需要隐藏输入法的edittext
     * @param editTexts  所有需要设置不可聚焦输入字符的edittext列表
     */
    public static void hideInputMethod(Context context,View targetView, EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setFocusable(false);
        }
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(targetView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }

    /**
     * 为edittext设置允许输入的字符的正则表达式约束和允许输入的最大长度
     *
     * @param editText  目标edittext
     * @param regEx     允许输入的字符的正则表达式
     * @param maxLength 允许输入的最大长度
     */
    public static void setInputFilter(EditText editText, final String regEx, int maxLength) {
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (!Pattern.matches(regEx, source.toString())) {
                    return "";
                }
                return null;
            }
        }, new InputFilter.LengthFilter(maxLength)});
    }
}
