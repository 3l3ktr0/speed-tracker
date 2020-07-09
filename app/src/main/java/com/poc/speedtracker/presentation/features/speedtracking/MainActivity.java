package com.poc.speedtracker.presentation.features.speedtracking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.poc.speedtracker.R;
import com.poc.speedtracker.presentation.base.BaseActivity;
import com.poc.speedtracker.presentation.features.speedtracking.ui.AskingLocationFragment;
import com.poc.speedtracker.presentation.features.speedtracking.ui.AverageSpeedFragment;
import com.poc.speedtracker.presentation.features.speedtracking.ui.MovingFragment;

public class MainActivity extends BaseActivity {

    public static final int PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocationPermission();
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            showMovingFragment();
        }  else {
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
                // TODO: Refacto
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    showMovingFragment();
                }
            } else {
                showAskingLocationFragment();
            }
        }
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
                .addToBackStack(AverageSpeedFragment.TAG)
                .replace(R.id.fragment_container, projectFragment, AverageSpeedFragment.TAG).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}