package com.android.wardrobeManager.ui.closet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.android.wardrobeManager.R;

public class ClosetSortByFragment extends Fragment {

    private boolean fragmentIsOpen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentIsOpen = true;
        View returnInflater = inflater.inflate(R.layout.fragment_sort_by, container, false);

        View laundryButton = returnInflater.findViewById(R.id.laundryStatusButton);
        View alphabeticalButton = returnInflater.findViewById(R.id.alphabeticalButton);
        View colorButton = returnInflater.findViewById(R.id.colorButton);
        View sizeButton = returnInflater.findViewById(R.id.sizeButton);

        laundryButton.setBackground(getContext().getDrawable(R.drawable.binary_button_deselected));
        alphabeticalButton.setBackground(getContext().getDrawable(R.drawable.binary_button_deselected));
        colorButton.setBackground(getContext().getDrawable(R.drawable.binary_button_deselected));
        sizeButton.setBackground(getContext().getDrawable(R.drawable.binary_button_deselected));

        int selectedButton = ((ClosetActivity)getActivity()).getSortingType();

        switch (selectedButton) {
            case 1:
                laundryButton.setBackground(getContext().getDrawable(R.drawable.binary_button_selected));
                break;
            case 2:
                alphabeticalButton.setBackground(getContext().getDrawable(R.drawable.binary_button_selected));
                break;
            case 3:
                colorButton.setBackground(getContext().getDrawable(R.drawable.binary_button_selected));
                break;
            case 4:
                sizeButton.setBackground(getContext().getDrawable(R.drawable.binary_button_selected));
                break;
        }

        return returnInflater;
    }

    @Override
    public void onDetach() {
        fragmentIsOpen = false;
        super.onDetach();
    }
}
