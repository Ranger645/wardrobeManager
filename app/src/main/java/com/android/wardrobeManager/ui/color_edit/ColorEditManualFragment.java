package com.android.wardrobeManager.ui.color_edit;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.wardrobeManager.R;

public class ColorEditManualFragment extends Fragment {

    public static ColorEditManualFragment getInstance() {
        return new ColorEditManualFragment();
    }

    public ColorEditManualFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConstraintLayout rootLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_color_edit_manual, container, false);

        FrameLayout colorWheelContainer = rootLayout.findViewById(R.id.manual_color_wheel_frame);
        ManualColorSelectorView colorWheel = new ManualColorSelectorView(getActivity());
        colorWheelContainer.addView(colorWheel);

        FrameLayout greyScaleSliderContainer = rootLayout.findViewById(R.id.manual_color_grayscale_frame);
        ManualColorGrayscaleView greyscaleSlider = new ManualColorGrayscaleView(getActivity());
        greyScaleSliderContainer.addView(greyscaleSlider);

        return rootLayout;
    }

}
