package com.poc.speedtracker.presentation.moving;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
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
import com.poc.speedtracker.databinding.MovingFragmentBinding;

public class MovingFragment extends Fragment {

    public static final String TAG = "MovingFragment";

    private MovingViewModel viewModel;
    private MovingFragmentBinding binding;

    public static MovingFragment newInstance() {
        return new MovingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.moving_fragment, container, false);
        View view = binding.getRoot();
        binding.setLifecycleOwner(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MovingViewModel.class);

        binding.setLocation(viewModel);

        viewModel.getCurrentSpeed().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float locationModel) {
            }
        });
    }

}