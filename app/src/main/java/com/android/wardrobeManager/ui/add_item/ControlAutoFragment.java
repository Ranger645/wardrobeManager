package com.android.wardrobeManager.ui.add_item;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.wardrobeManager.R;

public class ControlAutoFragment extends Fragment {

    public ControlAutoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_auto, container, false);

        ImageView goToEditButton = view.findViewById(R.id.add_edit_button);
        goToEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemActivity activity = (AddItemActivity) getActivity();
                activity.showEditScreen();
            }
        });

        return view;
    }

}
