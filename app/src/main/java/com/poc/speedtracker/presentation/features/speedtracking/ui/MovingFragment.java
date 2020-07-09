package com.poc.speedtracker.presentation.features.speedtracking.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.poc.speedtracker.R;
import com.poc.speedtracker.databinding.MovingFragmentBinding;
import com.poc.speedtracker.di.viewmodel.ViewModelFactory;
import com.poc.speedtracker.presentation.base.BaseFragment;
import com.poc.speedtracker.presentation.features.speedtracking.MainActivity;
import com.poc.speedtracker.presentation.features.speedtracking.viewmodels.MovingViewModel;

import javax.inject.Inject;

public class MovingFragment extends BaseFragment {

    public static final String TAG = "MovingFragment";

    @Inject
    ViewModelFactory viewModelFactory;

    private MovingFragmentBinding binding;

    public static MovingFragment newInstance() {
        return new MovingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.moving_fragment, container, false);
        View view = binding.getRoot();
        binding.setLifecycleOwner(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MovingViewModel viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(MovingViewModel.class);

        binding.setLocation(viewModel);

        viewModel.userStopped().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userStopped) {
                if (userStopped) {
                    MainActivity activity = (MainActivity) getActivity();
                    if (activity != null) {
                        activity.showAverageSpeedFragment();
                    }
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