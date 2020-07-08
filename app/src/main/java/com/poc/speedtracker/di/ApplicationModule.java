package com.poc.speedtracker.di;

import android.content.Context;

import com.poc.speedtracker.data.repository.LocationRepository;
import com.poc.speedtracker.data.repository.impl.LocationRepositoryImpl;
import com.poc.speedtracker.domain.services.LocationService;
import com.poc.speedtracker.domain.services.impl.LocationServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    @Singleton
    @Provides
    static LocationService provideLocationService(LocationRepository locationRepository) {
        return new LocationServiceImpl(locationRepository);
    }

    @Singleton
    @Provides
    static LocationRepository provideLocationRepository(Context context) {
        return new LocationRepositoryImpl(context);
    }
}