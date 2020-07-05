package com.poc.speedtracker.presentation.moving;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poc.speedtracker.R;
import com.poc.speedtracker.data.LocationModel;

public class MovingFragment extends Fragment {

    public static final String TAG = "MovingFragment";

    private MovingViewModel viewModel;

    public static MovingFragment newInstance() {
        return new MovingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.moving_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MovingViewModel.class);

        viewModel.getCurrentSpeed().observe(getViewLifecycleOwner(), new Observer<LocationModel>() {
            @Override
            public void onChanged(LocationModel locationModel) {
                Log.d("Location", locationModel.longitude+" "+locationModel.latitude);
            }
        });
    }

}