package com.poc.speedtracker.presentation.ui;

import android.os.Bundle;

import com.poc.speedtracker.R;
import com.poc.speedtracker.presentation.base.BaseActivity;
import com.poc.speedtracker.presentation.ui.AverageSpeedFragment;
import com.poc.speedtracker.presentation.ui.MovingFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovingFragment fragment = MovingFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, MovingFragment.TAG).commit();
    }

    public void showAverageSpeedFragment() {
        AverageSpeedFragment projectFragment = AverageSpeedFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, projectFragment, null).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}