package com.android.wardrobeManager.ui.color_edit;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
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
        final AddItemViewModel viewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);

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
        greyscaleSlider.setColorGrayscaleListener(new ManualColorGrayscaleView.ManualColorGrayscaleListener() {
            @Override
            public void onNewColorSelect(double colorPercentage) {
                Log.d("COLOR_CHANGE", "New grayscale percent");
            }
        });
        colorWheel.setColorSelectListener(new ManualColorSelectorView.ManualColorSelectorUpdateListener() {
            @Override
            public void onNewColorSelect(int newColor) {
                Log.d("COLOR_SELECT", "New color selected: " + newColor);
                viewModel.addClothingItemColor(Utility.convertColorPercentageToColor(newColor, greyscaleSlider.getColorPercentage()));
                getActivity().findViewById(R.id.color_edit_button_display).invalidate();
            }
        });

        return rootLayout;
    }

}
