package com.trackasia.android.plugins.scalebar;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;
import com.trackasia.android.camera.CameraPosition;
import com.trackasia.android.log.Logger;
import com.trackasia.android.maps.TrackAsiaMap;
import com.trackasia.android.maps.MapView;
import com.trackasia.android.maps.Projection;

/**
 * Plugin class that shows a scale bar on MapView and changes the scale corresponding to the MapView's scale.
 */
public class ScaleBarPlugin {
    private static final String TAG = "Mbgl-ScaleBarPlugin";

    private final MapView mapView;
    private final TrackAsiaMap trackAsiaMap;
    private final Projection projection;
    private boolean enabled = true;
    private ScaleBarWidget scaleBarWidget;

    @VisibleForTesting
    final TrackAsiaMap.OnCameraMoveListener cameraMoveListener = new TrackAsiaMap.OnCameraMoveListener() {
        @Override
        public void onCameraMove() {
            invalidateScaleBar();
        }
    };

    @VisibleForTesting
    final TrackAsiaMap.OnCameraIdleListener cameraIdleListener = new TrackAsiaMap.OnCameraIdleListener() {
        @Override
        public void onCameraIdle() {
            invalidateScaleBar();
        }
    };

    public ScaleBarPlugin(@NonNull MapView mapView, @NonNull TrackAsiaMap trackasiaMap) {
        this.mapView = mapView;
        this.trackAsiaMap = trackasiaMap;
        this.projection = trackasiaMap.getProjection();
    }

    /**
     * Create a scale bar widget on mapView.
     *
     * @param option The scale bar widget options that used to build scale bar widget.
     * @return The created ScaleBarWidget instance.
     */
    public ScaleBarWidget create(@NonNull ScaleBarOptions option) {
        if (scaleBarWidget != null) {
            mapView.removeView(scaleBarWidget);
        }
        scaleBarWidget = option.build();
        scaleBarWidget.setMapViewWidth(mapView.getWidth());
        mapView.addView(scaleBarWidget);

        scaleBarWidget.setVisibility(enabled ? View.VISIBLE : View.GONE);
        if (enabled) {
            addCameraListeners();
            invalidateScaleBar();
        }
        return scaleBarWidget;
    }

    /**
     * Returns true if the scale plugin is currently enabled and visible.
     *
     * @return true if enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Toggles the scale plugin state.
     * <p>
     * If the scale plugin wasn enabled, a {@link com.trackasia.android.maps.TrackAsiaMap.OnCameraMoveListener}
     * will be added to the {@link MapView} to listen to scale change events to update the state of this plugin. If the
     * plugin was disabled the {@link com.trackasia.android.maps.TrackAsiaMap.OnCameraMoveListener}
     * will be removed from the map.
     * </p>
     */
    @UiThread
    public void setEnabled(boolean enabled) {
        if (scaleBarWidget == null) {
            Logger.w(TAG, "Create a widget before changing ScalebBarPlugin's state. Ignoring.");
            return;
        }
        if (this.enabled == enabled) {
            // already in correct state
            return;
        }
        this.enabled = enabled;
        scaleBarWidget.setVisibility(enabled ? View.VISIBLE : View.GONE);
        if (enabled) {
            addCameraListeners();
            invalidateScaleBar();
        } else {
            removeCameraListeners();
        }
    }

    private void invalidateScaleBar() {
        CameraPosition cameraPosition = trackAsiaMap.getCameraPosition();
        scaleBarWidget.setDistancePerPixel((projection.getMetersPerPixelAtLatitude(cameraPosition.target.getLatitude()))
            / mapView.getPixelRatio());
    }

    private void addCameraListeners() {
        trackAsiaMap.addOnCameraMoveListener(cameraMoveListener);
        trackAsiaMap.addOnCameraIdleListener(cameraIdleListener);
    }

    private void removeCameraListeners() {
        trackAsiaMap.removeOnCameraMoveListener(cameraMoveListener);
        trackAsiaMap.removeOnCameraIdleListener(cameraIdleListener);
    }
}
