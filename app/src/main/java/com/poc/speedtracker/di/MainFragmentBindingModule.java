package com.poc.speedtracker.di;

import com.poc.speedtracker.presentation.ui.AverageSpeedFragment;
import com.poc.speedtracker.presentation.ui.MovingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract MovingFragment provideMovingFragment();

    @ContributesAndroidInjector
    abstract AverageSpeedFragment provideAverageSpeedFragment();
}
