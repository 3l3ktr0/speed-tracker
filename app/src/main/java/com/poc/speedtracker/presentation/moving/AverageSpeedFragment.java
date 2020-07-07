package com.poc.speedtracker.presentation.moving;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poc.speedtracker.R;
import com.poc.speedtracker.databinding.AverageSpeedFragmentBinding;

public class AverageSpeedFragment extends Fragment {

    private MovingViewModel viewModel;
    private AverageSpeedFragmentBinding binding;

    public AverageSpeedFragment() {
        // Required empty public constructor
    }

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
        viewModel = new ViewModelProvider(requireActivity()).get(MovingViewModel.class);

        binding.setLocation(viewModel);

        viewModel.userStartMoving().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userStartMoving) {
                if (userStartMoving) {
                    Log.d("STOP", "User started moving again");
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }
}