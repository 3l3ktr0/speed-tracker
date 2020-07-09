package com.poc.speedtracker.di;

import com.poc.speedtracker.data.repository.LocationRepositoryImpl;
import com.poc.speedtracker.di.viewmodel.ViewModelModule;
import com.poc.speedtracker.domain.repository.LocationRepository;
import com.poc.speedtracker.domain.services.LocationService;
import com.poc.speedtracker.domain.services.impl.LocationServiceImpl;

import dagger.Binds;
import dagger.Module;

@Module(includes = ViewModelModule.class)
public abstract class ApplicationModule {

    @Binds
    public abstract LocationService provideLocationService(LocationServiceImpl locationRepository);

    @Binds
    public abstract LocationRepository provideLocationRepository(LocationRepositoryImpl locationRepository);
}