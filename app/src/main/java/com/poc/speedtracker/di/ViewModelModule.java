package com.poc.speedtracker.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.poc.speedtracker.presentation.viewmodels.MovingViewModel;
import com.poc.speedtracker.presentation.viewmodels.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovingViewModel.class)
    abstract ViewModel bindMovingViewModel(MovingViewModel movingViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}