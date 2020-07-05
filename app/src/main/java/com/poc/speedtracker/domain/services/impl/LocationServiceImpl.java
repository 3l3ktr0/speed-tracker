package com.poc.speedtracker.domain.services.impl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.poc.speedtracker.domain.services.LocationService;

public class LocationServiceImpl implements LocationService {
    @Override
    public LiveData<Float> getCurrentSpeed() {
        final MutableLiveData<Float> data = new MutableLiveData<>();

//        gitHubService.getProjectList(userId).enqueue(new Callback<List<Project>>() {
//            @Override
//            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
//                data.setValue(response.body());
//            }
//
//            // Error handling will be explained in the next article …
//        });

        return data;
    }
}
