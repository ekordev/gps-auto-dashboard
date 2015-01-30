package com.zachrohde.gpsautodash.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zachrohde.gpsautodash.MainActivity;
import com.zachrohde.gpsautodash.R;
import com.zachrohde.gpsautodash.Services.AccelService;
import com.zachrohde.gpsautodash.Services.GPSService;

/**
 * TODO
 */
public class DashboardFragment extends Fragment {
    // The fragment argument representing the section number for this fragment.
    private static final String ARG_SECTION_NUMBER = "section_number";

    // Service instances.
    AccelService mAccelServiceInst;
    GPSService mGPSServiceInst;

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static DashboardFragment newInstance(int sectionNumber) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // See if we need to change the objects on the screen to the dark theme's colors.
        if (MainActivity.mThemeId == android.R.style.Theme_Holo) {
            rootView.findViewById(R.id.centerBar).setBackgroundColor(Color.WHITE);
            rootView.findViewById(R.id.acceleration_bar).setBackgroundDrawable(getResources().getDrawable(R.drawable.white_bg_progress));
        }

        // Create a new AccelService instance.
        mAccelServiceInst = new AccelService(getActivity(), rootView);

        // Create a new GPSService instance.
        mGPSServiceInst = new GPSService(getActivity(), rootView, mAccelServiceInst);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onResume() {
        super.onResume();

        mGPSServiceInst.startListener();
    }

    @Override
    public void onPause() {
        super.onPause();

        mGPSServiceInst.stopListener();
    }
}