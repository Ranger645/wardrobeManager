package com.android.wardrobeManager.ui.add_item;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wardrobeManager.R;

public class MiscColorBarFragment extends Fragment {


    public MiscColorBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_misc_color_bar, container, false);
        return view;
    }

}
