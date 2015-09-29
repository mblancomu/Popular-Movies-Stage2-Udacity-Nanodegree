package com.example.blancomm.popularmoviesstage1.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextPaint;

import com.example.blancomm.popularmoviesstage1.R;

import java.lang.reflect.Field;

public class UtilsView {

    public static void makeCollapsingToolbarLayoutTypeface(CollapsingToolbarLayout collapsingToolbarLayout, Context context) {
        try {
            final Field field = collapsingToolbarLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            field.setAccessible(true);

            final Object object = field.get(collapsingToolbarLayout);
            final Field tpf = object.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            ((TextPaint) tpf.get(object)).setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Bold.ttf"));

        } catch (Exception ignored) {
        }
    }
}
