package com.android.wardrobeManager.ui.closet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.wardrobeManager.R;

public class ClosetMenuFragment extends Fragment {

    private boolean fragmentIsOpen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentIsOpen = true;
        return inflater.inflate(R.layout.fragment_closet_menu, container, false);
    }

    @Override
    public void onDetach() {
        fragmentIsOpen = false;
        super.onDetach();
    }

    public boolean isFragmentOpen() { return fragmentIsOpen; }

}
