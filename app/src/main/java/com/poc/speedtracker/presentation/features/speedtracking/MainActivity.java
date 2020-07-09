package com.poc.speedtracker.presentation.features.speedtracking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;

import com.poc.speedtracker.R;
import com.poc.speedtracker.di.viewmodel.ViewModelFactory;
import com.poc.speedtracker.presentation.base.BaseActivity;
import com.poc.speedtracker.presentation.features.speedtracking.ui.AskingLocationFragment;
import com.poc.speedtracker.presentation.features.speedtracking.ui.AverageSpeedFragment;
import com.poc.speedtracker.presentation.features.speedtracking.ui.MovingFragment;
import com.poc.speedtracker.presentation.features.speedtracking.viewmodels.MovingViewModel;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    public static final int PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocationPermission();
    }

    private void start() {
        MovingViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(MovingViewModel.class);

        Transformations.distinctUntilChanged(viewModel.userStartMoving())
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean userMoving) {
                        if (userMoving) {
                            showMovingFragment();
                        } else {
                            showAverageSpeedFragment();
                        }
                    }
                });
    }

    public void checkLocationPermission() {
        if (checkLocationPermissionGranted()) {
            start();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkLocationPermissionGranted()) {
                    start();
                }
            } else {
                showAskingLocationFragment();
            }
        }
    }

    private boolean checkLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void showAskingLocationFragment() {
        AskingLocationFragment fragment = AskingLocationFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, AskingLocationFragment.TAG).commit();
    }

    private void showMovingFragment() {
        MovingFragment fragment = MovingFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, MovingFragment.TAG).commit();
    }

    public void showAverageSpeedFragment() {
        AverageSpeedFragment projectFragment = AverageSpeedFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, projectFragment, AverageSpeedFragment.TAG).commit();
    }
}