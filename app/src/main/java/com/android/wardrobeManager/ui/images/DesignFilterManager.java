package com.android.wardrobeManager.ui.images;

import android.graphics.Bitmap;
import java.util.HashMap;
import java.util.Map;

public class DesignFilterManager {


    protected Map<String, DesignFilter> filters;

    public static Bitmap filterColorStyle(String colorStyle, Bitmap bitmap, Bitmap ref, int[] colors) {
        return new DesignFilterManager().filter(colorStyle, bitmap, ref, colors);
    }

    public DesignFilterManager() {
        filters = new HashMap<>();


        filters.put("primary_secondary", new PrimarySecondary());
        filters.put("thin_stripes", new ThinHorizontalStripes());
        filters.put("wide_stripes", new WideHorizontalStripes());
        filters.put("thin_horizontal_stripes", new ThinHorizontalStripes());
        filters.put("wide_horizontal_stripes", new WideHorizontalStripes());
        filters.put("thin_vertical_stripes", new ThinVerticalStripes());
        filters.put("wide_vertical_stripes", new WideVerticalStripes());
        filters.put("polka_dots", new PolkaDots());
        filters.put("gradient", new VerticalGradient());
        filters.put("vertical_gradient", new VerticalGradient());
        filters.put("horizontal_gradient", new HorizontalGradient());
        filters.put("birds_eye", new BirdsEye());
        filters.put("checker", new Checker());

    }

    public Bitmap filter(String colorStyle, Bitmap bitmap, Bitmap ref, int[] colors) {
        return this.filters.get(colorStyle).filter(bitmap, ref, colors);
    }

    protected interface DesignFilter {
        Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors);
    }

}
