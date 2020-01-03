package com.android.wardrobeManager.ui.add_item;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.wardrobeManager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemParamEditFragment extends Fragment {


    public ItemParamEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_param_edit, container, false);
        LinearLayout editList = view.findViewById(R.id.additem_edit_list);

        ItemParamButton nameEditButton = new ItemParamButton("Name","Default", inflater, editList);
        nameEditButton.setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
