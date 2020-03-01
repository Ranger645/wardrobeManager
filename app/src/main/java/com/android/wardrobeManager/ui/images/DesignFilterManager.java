package com.android.wardrobeManager.ui.images;

import android.graphics.Bitmap;
import com.android.wardrobeManager.ui.images.filters.Checker;
import com.android.wardrobeManager.ui.images.filters.Default;
import com.android.wardrobeManager.ui.images.filters.HorizontalGradient;
import com.android.wardrobeManager.ui.images.filters.HorizontalStripes;
import com.android.wardrobeManager.ui.images.filters.PolkaDots;
import com.android.wardrobeManager.ui.images.filters.PrimarySecondary;
import com.android.wardrobeManager.ui.images.filters.VerticalGradient;
import com.android.wardrobeManager.ui.images.filters.VerticalStripes;
import java.util.HashMap;
import java.util.Map;

public class DesignFilterManager {


    protected Map<String, DesignFilter> filters;


    private static DesignFilterManager designFilterManager = null;

    public static Bitmap filterDesign(String colorStyle, Bitmap ref, int[] colors) {
        if (designFilterManager == null)
            designFilterManager = new DesignFilterManager();
        Bitmap bitmap = ref.copy(ref.getConfig(), true);
        return designFilterManager.filter(colorStyle, bitmap, ref, colors);
    }

    public DesignFilterManager() {
        filters = new HashMap<>();
        filters.put("checker", new Checker(40));
        filters.put("vertical_gradient", new VerticalGradient());
        filters.put("horizontal_gradient", new HorizontalGradient());
        filters.put("birds_eye", new Checker(4, true));
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

    public interface DesignFilter {
        Bitmap filter(Bitmap bitmap, Bitmap ref, int[] colors);
    }

}
