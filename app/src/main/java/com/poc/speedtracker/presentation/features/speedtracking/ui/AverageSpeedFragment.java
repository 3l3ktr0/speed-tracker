package com.poc.speedtracker.presentation.features.speedtracking.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.poc.speedtracker.R;
import com.poc.speedtracker.databinding.AverageSpeedFragmentBinding;
import com.poc.speedtracker.di.viewmodel.ViewModelFactory;
import com.poc.speedtracker.presentation.base.BaseFragment;
import com.poc.speedtracker.presentation.features.speedtracking.viewmodels.MovingViewModel;

import javax.inject.Inject;

public class AverageSpeedFragment extends BaseFragment {

    public static final String TAG = "AverageSpeedFragment";

    @Inject
    ViewModelFactory viewModelFactory;

    private AverageSpeedFragmentBinding binding;

    public static AverageSpeedFragment newInstance() {
        return new AverageSpeedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.average_speed_fragment, container, false);
        View view = binding.getRoot();
        binding.setLifecycleOwner(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MovingViewModel viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(MovingViewModel.class);

        binding.setLocation(viewModel);

        viewModel.userStartMoving().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userStartMoving) {
                if (userStartMoving) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}