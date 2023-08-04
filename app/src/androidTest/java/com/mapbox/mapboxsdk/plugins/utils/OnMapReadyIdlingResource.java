package com.trackasia.android.plugins.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.trackasia.android.maps.MapView;
import com.trackasia.android.maps.TrackasiaMap;
import com.trackasia.android.maps.OnMapReadyCallback;
import com.trackasia.android.maps.Style;
import com.trackasia.android.plugins.testapp.R;

import androidx.annotation.NonNull;
import androidx.test.espresso.IdlingResource;

public class OnMapReadyIdlingResource implements IdlingResource, OnMapReadyCallback {

    private TrackasiaMap mapboxMap;
    private MapView mapView;
    private IdlingResource.ResourceCallback resourceCallback;

    public OnMapReadyIdlingResource(Activity activity) {
        new Handler(Looper.getMainLooper()).post(() -> {
            mapView = activity.findViewById(R.id.mapView);
            if (mapView != null) {
                mapView.getMapAsync(OnMapReadyIdlingResource.this);
            }
        });
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        return mapboxMap != null && mapboxMap.getStyle() != null && mapboxMap.getStyle().isFullyLoaded();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    public MapView getMapView() {
        return mapView;
    }

    public TrackasiaMap getMapboxMap() {
        return mapboxMap;
    }

    @Override
    public void onMapReady(@NonNull TrackasiaMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.getPredefinedStyle("Streets"), style -> {
            if (resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
        });
    }
}