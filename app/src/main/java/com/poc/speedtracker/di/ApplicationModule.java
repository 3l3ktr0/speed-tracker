package com.poc.speedtracker.di;

import com.poc.speedtracker.data.repository.LocationRepository;
import com.poc.speedtracker.data.repository.impl.LocationRepositoryImpl;
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