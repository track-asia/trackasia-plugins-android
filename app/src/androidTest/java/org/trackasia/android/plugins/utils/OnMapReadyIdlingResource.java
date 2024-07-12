package org.trackasia.android.plugins.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.test.espresso.IdlingResource;
import org.trackasia.android.maps.TrackAsiaMap;
import org.trackasia.android.maps.MapView;
import org.trackasia.android.maps.OnMapReadyCallback;
import org.trackasia.android.maps.Style;
import org.trackasia.android.plugins.testapp.R;

public class OnMapReadyIdlingResource implements IdlingResource, OnMapReadyCallback {

    private TrackAsiaMap trackAsiaMap;
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
        return trackAsiaMap != null && trackAsiaMap.getStyle() != null && trackAsiaMap.getStyle().isFullyLoaded();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    public MapView getMapView() {
        return mapView;
    }

    public TrackAsiaMap getTrackAsiaMap() {
        return trackAsiaMap;
    }

    @Override
    public void onMapReady(@NonNull TrackAsiaMap trackAsiaMap) {
        this.trackAsiaMap = trackAsiaMap;
        trackAsiaMap.setStyle(Style.getPredefinedStyle("Streets"), style -> {
            if (resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
        });
    }
}