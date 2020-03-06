package com.android.wardrobeManager.ui.add_item;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;


public class MainManualColor extends Fragment {

    private ViewManualColor viewManualColor = null;

    public MainManualColor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_manual_color, container, false);

        AddItemViewModel viewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);

        viewManualColor = new ViewManualColor(getContext());
        viewManualColor.setColorWheelRotation(viewModel.getManualColorViewColorRotation());
        viewManualColor.setGradientWheelRotation(viewModel.getManualColorViewGradientRotation());

        SquareFrameLayout holder = view.findViewById(R.id.main_manual_color_holder);
        holder.addView(viewManualColor);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        AddItemViewModel viewModel = ViewModelProviders.of(getActivity()).get(AddItemViewModel.class);
        viewModel.setManualColorViewColorRotation(viewManualColor.getColorWheelRotation());
        viewModel.setManualColorViewGradientRotation(viewManualColor.getGradientWheelRotation());
    }
}
