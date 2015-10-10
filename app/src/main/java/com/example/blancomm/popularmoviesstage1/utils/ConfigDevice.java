package com.example.blancomm.popularmoviesstage1.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.blancomm.popularmoviesstage1.R;

/**
 * Created by manuel on 10/10/15.
 */
public class ConfigDevice {

    public static int getDensityDevice(Context context) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        return (int) (metrics.density * 160f);
    }

    public static int getNumberColumnsGrid(Context context) {

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        boolean tabletSize = context.getResources().getBoolean(R.bool.isTablet);
        boolean hightDensity = context.getResources().getBoolean(R.bool.isHightDensity);
        int orientation = display.getRotation();
        int numberColums = 0;


        if (orientation == 0 ) {

            numberColums = !tabletSize?context.getResources().getInteger(R.integer.columns_grid_portait):
                    context.getResources().getInteger(R.integer.columns_grid_portrait_hight_density);
        }else {

            numberColums = !hightDensity ? context.getResources().getInteger(R.integer.columns_grid_landscape_low_density):
                    context.getResources().getInteger(R.integer.columns_grid_landscape);
        }

        return numberColums;
    }
}
