package com.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import static android.graphics.Typeface.BOLD_ITALIC;

public class T {

    public static void error(Context context, String msg) {
        Toasty.error(context, msg, Toasty.LENGTH_SHORT, true).show();
    }

    public static void success(Context context, String msg) {
        Toasty.success(context, msg, Toasty.LENGTH_SHORT, true).show();
    }

    public static void info(Context context, String msg) {
        Toasty.info(context, msg, Toasty.LENGTH_SHORT, true).show();
    }

    public static void warning(Context context, String msg) {
        Toasty.warning(context, msg, Toasty.LENGTH_SHORT, true).show();
    }

    public static void normal(Context context, String msg) {
        Toasty.normal(context, msg, Toasty.LENGTH_SHORT).show();
    }

    public static void icon(Context context, String msg) {
        Drawable icon = context.getResources().getDrawable(R.drawable.ic_pets_white_48dp);
        Toasty.normal(context, msg, icon).show();
    }

    public static void toCharSequence(Context context, String msg) {
        Toasty.info(context, getFormattedMessage()).show();
    }

    @SuppressLint("ResourceAsColor")
    public static void custom(Context context, String msg) {
        Toasty.Config.getInstance()
                .setToastTypeface(Typeface.createFromAsset(context.getAssets(), "PCap Terminal.otf"))
                .allowQueue(false)
                .apply();
        Toasty.custom(context, msg, context.getResources().getDrawable(R.drawable.ic_pets_white_48dp),
                android.R.color.black, android.R.color.holo_green_light, Toasty.LENGTH_SHORT, true, true).show();
        Toasty.Config.reset(); // Use this if you want to use the configuration above only once
    }

    private static CharSequence getFormattedMessage() {
        final String prefix = "Formatted ";
        final String highlight = "bold italic";
        final String suffix = " text";
        SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
        int prefixLen = prefix.length();
        ssb.setSpan(new StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
