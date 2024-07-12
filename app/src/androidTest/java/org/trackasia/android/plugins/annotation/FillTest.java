
// This file is generated.

package org.trackasia.android.plugins.annotation;

import android.graphics.PointF;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.trackasia.android.geometry.LatLng;
import org.trackasia.android.plugins.BaseActivityTest;
import org.trackasia.android.plugins.testapp.activity.TestActivity;
import org.trackasia.android.utils.ColorUtils;

import timber.log.Timber;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.trackasia.android.plugins.annotation.TrackAsiaMapAction.invoke;
import static org.junit.Assert.*;
import static org.trackasia.android.style.layers.Property.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic smoke tests for Fill
 */
@RunWith(AndroidJUnit4.class)
public class FillTest extends BaseActivityTest {

    private Fill fill;

    @Override
    protected Class getActivityClass() {
        return TestActivity.class;
    }

    private void setupAnnotation() {
        Timber.i("Retrieving layer");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            FillManager fillManager = new FillManager(idlingResource.getMapView(), trackasiaMap, Objects.requireNonNull(trackasiaMap.getStyle()));
            List<LatLng> innerLatLngs = new ArrayList<>();
            innerLatLngs.add(new LatLng());
            innerLatLngs.add(new LatLng(1, 1));
            innerLatLngs.add(new LatLng(-1, -1));
            List<List<LatLng>> latLngs = new ArrayList<>();
            latLngs.add(innerLatLngs);
            fill = fillManager.create(new FillOptions().withLatLngs(latLngs));
        });
    }

    @Test
    public void testFillOpacity() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-opacity");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(fill);

            fill.setFillOpacity(2.0f);
            assertEquals((Float) fill.getFillOpacity(), (Float) 2.0f);
        });
    }

    @Test
    public void testFillColor() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-color");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(fill);

            fill.setFillColor("rgba(0, 0, 0, 1)");
            assertEquals(fill.getFillColor(), "rgba(0, 0, 0, 1)");
        });
    }

    @Test
    public void testFillColorAsInt() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-color");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(fill);
            fill.setFillColor(ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
            assertEquals(fill.getFillColorAsInt(), ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
        });
    }


    @Test
    public void testFillOutlineColor() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-outline-color");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(fill);

            fill.setFillOutlineColor("rgba(0, 0, 0, 1)");
            assertEquals(fill.getFillOutlineColor(), "rgba(0, 0, 0, 1)");
        });
    }

    @Test
    public void testFillOutlineColorAsInt() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-outline-color");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(fill);
            fill.setFillOutlineColor(ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
            assertEquals(fill.getFillOutlineColorAsInt(), ColorUtils.rgbaToColor("rgba(0, 0, 0, 1)"));
        });
    }


    @Test
    public void testFillPattern() {
        validateTestSetup();
        setupAnnotation();
        Timber.i("fill-pattern");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(fill);

            fill.setFillPattern("pedestrian-polygon");
            assertEquals((String) fill.getFillPattern(), (String) "pedestrian-polygon");
        });
    }
}
