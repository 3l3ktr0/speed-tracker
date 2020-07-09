package com.poc.speedtracker.di.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.poc.speedtracker.presentation.features.speedtracking.viewmodels.MovingViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovingViewModel.class)
    abstract ViewModel bindMovingViewModel(MovingViewModel movingViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}