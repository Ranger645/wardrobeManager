package com.android.wardrobeManager.ui.images;

import android.graphics.Bitmap;
import java.util.HashMap;
import java.util.Map;

public class DesignFilterManager {


    protected Map<String, DesignFilter> filters;


    private static DesignFilterManager designFilterManager = null;

    public static Bitmap filterDesign(String colorStyle, Bitmap ref, int[] colors) {
        if (designFilterManager == null)
            designFilterManager = new DesignFilterManager();
        Bitmap bitmap = Bitmap.createBitmap(ref.getWidth(), ref.getHeight(), Bitmap.Config.ARGB_8888);
        return designFilterManager.filter(colorStyle, bitmap, ref, colors);
    }

    public DesignFilterManager() {
        filters = new HashMap<>();

        filters.put("polka_dots", new PolkaDots());
        filters.put("primary_secondary", new PrimarySecondary());
        filters.put("thin_stripes", new HorizontalStripes(10));
        filters.put("wide_stripes", new HorizontalStripes(80));
        filters.put("thin_horizontal_stripes", new HorizontalStripes(10));
        filters.put("wide_horizontal_stripes", new HorizontalStripes(80));
        filters.put("thin_vertical_stripes", new VerticalStripes(10));
        filters.put("wide_vertical_stripes", new VerticalStripes(80));
        filters.put("default", new Default());

    }

    public Bitmap filter(String colorStyle, Bitmap bitmap, Bitmap ref, int[] colors) {
        DesignFilter designFilter = this.filters.get(colorStyle);
        if (designFilter == null)
            designFilter = this.filters.get("default");
        return designFilter.filter(bitmap, ref, colors);
    }

    protected interface DesignFilter {
        Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors);
    }

}
