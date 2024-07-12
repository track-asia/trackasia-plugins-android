// This file is generated.

package org.trackasia.android.plugins.annotation;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.trackasia.android.geometry.LatLng;
import org.trackasia.android.plugins.testapp.activity.TestActivity;
import org.trackasia.android.plugins.BaseActivityTest;

import timber.log.Timber;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.trackasia.android.plugins.annotation.TrackAsiaMapAction.invoke;
import static org.junit.Assert.*;
import static org.trackasia.android.style.layers.Property.*;

/**
 * Basic smoke tests for LineManager
 */
@RunWith(AndroidJUnit4.class)
public class LineManagerTest extends BaseActivityTest {

    private LineManager lineManager;

    @Override
    protected Class getActivityClass() {
        return TestActivity.class;
    }

    private void setupLineManager() {
        Timber.i("Retrieving layer");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            lineManager = new LineManager(idlingResource.getMapView(), trackasiaMap, Objects.requireNonNull(trackasiaMap.getStyle()));
        });
    }

    @Test
    public void testLineCapAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-cap");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineCap(LINE_CAP_BUTT);
            assertEquals((String) lineManager.getLineCap(), (String) LINE_CAP_BUTT);
        });
    }

    @Test
    public void testLineMiterLimitAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-miter-limit");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineMiterLimit(2.0f);
            assertEquals((Float) lineManager.getLineMiterLimit(), (Float) 2.0f);
        });
    }

    @Test
    public void testLineRoundLimitAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-round-limit");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineRoundLimit(2.0f);
            assertEquals((Float) lineManager.getLineRoundLimit(), (Float) 2.0f);
        });
    }

    @Test
    public void testLineTranslateAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-translate");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineTranslate(new Float[]{0f, 0f});
            assertEquals((Float[]) lineManager.getLineTranslate(), (Float[]) new Float[]{0f, 0f});
        });
    }

    @Test
    public void testLineTranslateAnchorAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-translate-anchor");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineTranslateAnchor(LINE_TRANSLATE_ANCHOR_MAP);
            assertEquals((String) lineManager.getLineTranslateAnchor(), (String) LINE_TRANSLATE_ANCHOR_MAP);
        });
    }

    @Test
    public void testLineDasharrayAsConstant() {
        validateTestSetup();
        setupLineManager();
        Timber.i("line-dasharray");
        invoke(trackasiaMap, (uiController, trackasiaMap) -> {
            assertNotNull(lineManager);

            lineManager.setLineDasharray(new Float[]{});
            assertEquals((Float[]) lineManager.getLineDasharray(), (Float[]) new Float[]{});
        });
    }
}
