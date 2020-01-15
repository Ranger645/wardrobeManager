package com.android.wardrobeManager.ui.color_edit;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return inflater.inflate(R.layout.fragment_color_edit_manual, container, false);
    }

}
