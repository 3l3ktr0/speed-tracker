package com.poc.speedtracker.di.viewmodel;

import androidx.lifecycle.ViewModel;

import dagger.MapKey;

@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
