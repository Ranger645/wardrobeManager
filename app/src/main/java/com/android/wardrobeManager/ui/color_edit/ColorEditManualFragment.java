package com.android.wardrobeManager.ui.color_edit;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.backend.ColorEditViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;
import com.android.wardrobeManager.ui.util.Utility;

public class ColorEditManualFragment extends Fragment {

    private ManualColorSelectorView colorWheel;
    private ManualColorGrayscaleView greyscaleSlider;


    public static ColorEditManualFragment getInstance() {
        return new ColorEditManualFragment();
    }

    public ColorEditManualFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ColorEditViewModel viewModel = ViewModelProviders.of(getActivity()).get(ColorEditViewModel.class);

        // Inflate the layout for this fragment
        ConstraintLayout rootLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_color_edit_manual, container, false);

        FrameLayout colorWheelContainer = rootLayout.findViewById(R.id.manual_color_wheel_frame);
        colorWheel = new ManualColorSelectorView(getActivity());
        colorWheelContainer.addView(colorWheel);

        FrameLayout greyScaleSliderContainer = rootLayout.findViewById(R.id.manual_color_grayscale_frame);
        greyscaleSlider = new ManualColorGrayscaleView(getActivity());
        greyscaleSlider.setColor(colorWheel.getColor());
        greyScaleSliderContainer.addView(greyscaleSlider);

        colorWheel.setColorChangeListener(new ManualColorSelectorView.ManualColorSelectorUpdateListener() {
            @Override
            public void onNewColorSelect(int newColor) {
                Log.d("COLOR_CHANGE", "Color Changed");
                greyscaleSlider.setColor(newColor);
                greyscaleSlider.invalidate();
            }
        });
        colorWheel.setColorSelectListener(new ManualColorSelectorView.ManualColorSelectorUpdateListener() {
            @Override
            public void onNewColorSelect(int newColor) {
                Log.d("COLOR_SELECT", "New color selected: " + newColor);
                // TODO: Reconsider the use cases for added duplicate colors:
                viewModel.addUniqueClothingItemColor(Utility.convertColorPercentageToColor(newColor, greyscaleSlider.getColorPercentage()));
                getActivity().findViewById(R.id.color_edit_button_display).invalidate();
            }
        });

        final ImageView previewImageView = rootLayout.findViewById(R.id.color_edit_manual_small_preview);

        viewModel.getClothingItem().observe(this, new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem item) {
                previewImageView.setImageBitmap(ClothingItemImageManager.dynamicClothingItemLoad(
                        viewModel.getClothingItem().getValue()));
            }
        });

        return rootLayout;
    }

}
