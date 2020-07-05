package com.poc.speedtracker.presentation.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poc.speedtracker.R;
import com.poc.speedtracker.presentation.moving.MovingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovingFragment fragment = MovingFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, MovingFragment.TAG).commit();
    }
}