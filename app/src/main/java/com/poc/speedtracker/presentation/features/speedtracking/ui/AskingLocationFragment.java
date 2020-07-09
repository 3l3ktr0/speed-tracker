package com.poc.speedtracker.presentation.features.speedtracking.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.poc.speedtracker.R;
import com.poc.speedtracker.databinding.AskingLocationFragmentBinding;
import com.poc.speedtracker.presentation.features.speedtracking.MainActivity;

public class AskingLocationFragment extends Fragment {

    public static final String TAG = "AskingLocationFragment";

    public static AskingLocationFragment newInstance() {
        return new AskingLocationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AskingLocationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.asking_location_fragment, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    public void onClick(View view) {
        MainActivity activity = ((MainActivity) getActivity());
        if (activity != null) {
            activity.checkLocationPermission();
        }
    }
}