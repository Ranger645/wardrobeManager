package com.android.wardrobeManager.ui.closet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.wardrobeManager.R;

public class ClosetShadeFragment extends Fragment {

    private boolean fragmentIsOpen;
    private boolean shadeIsClickable;

    public ClosetShadeFragment(boolean shadeIsClickable) {
        this.shadeIsClickable = shadeIsClickable;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentIsOpen = true;
        return inflater.inflate(R.layout.shade_screen, container, false);
    }

    @Override
    public void onDetach() {
        fragmentIsOpen = false;
        super.onDetach();
    }

    public boolean shadeIsClickable() { return shadeIsClickable; }

    public boolean fragmentIsOpen() { return fragmentIsOpen; }

}
