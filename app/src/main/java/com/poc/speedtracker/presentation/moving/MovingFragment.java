package com.poc.speedtracker.presentation.moving;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poc.speedtracker.R;
import com.poc.speedtracker.databinding.MovingFragmentBinding;
import com.poc.speedtracker.presentation.views.MainActivity;

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
        viewModel = new ViewModelProvider(requireActivity()).get(MovingViewModel.class);

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

}