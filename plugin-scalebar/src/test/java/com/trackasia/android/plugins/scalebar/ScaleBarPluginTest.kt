package com.trackasia.android.plugins.scalebar

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.trackasia.android.camera.CameraPosition
import com.trackasia.android.maps.TrackAsiaMap
import com.trackasia.android.maps.MapView
import com.trackasia.android.maps.Projection

class ScaleBarPluginTest {
    @MockK
    lateinit var mapView: MapView

    @MockK
    lateinit var projection: Projection

    @MockK
    lateinit var trackasiaMap: TrackAsiaMap

    @MockK
    lateinit var scaleBarOptions: ScaleBarOptions

    @MockK
    lateinit var scaleBarWidget: ScaleBarWidget

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var resources: Resources

    @MockK
    lateinit var displayMetrics: DisplayMetrics

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        displayMetrics.density = 2f
        every { mapView.width } returns 1000
        every { CameraPosition.DEFAULT.target?.let { projection.getMetersPerPixelAtLatitude(it.latitude) } } returns 100_000.0
        every { trackasiaMap.projection } returns projection
        every { trackasiaMap.cameraPosition } returns CameraPosition.DEFAULT
        every { scaleBarOptions.build() } returns scaleBarWidget
        every { mapView.context } returns context
        every { mapView.pixelRatio } returns 2f
        every { context.resources } returns resources
        every { resources.displayMetrics } returns displayMetrics
    }

    @Test
    fun sanity() {
        assertNotNull(mapView)
        assertNotNull(trackasiaMap)
        assertNotNull(scaleBarOptions)
        assertNotNull(scaleBarWidget)
    }

    @Test
    fun create_isEnabled() {
        val scaleBarPlugin =
            ScaleBarPlugin(mapView, trackasiaMap)
        scaleBarPlugin.create(scaleBarOptions)

        assertTrue(scaleBarPlugin.isEnabled)
        verify { scaleBarWidget.visibility = View.VISIBLE }
        verify(exactly = 1) { trackasiaMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 1) { trackasiaMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun disable() {
        val scaleBarPlugin =
            ScaleBarPlugin(mapView, trackasiaMap)
        scaleBarPlugin.create(scaleBarOptions)
        scaleBarPlugin.isEnabled = false

        assertFalse(scaleBarPlugin.isEnabled)
        verify { scaleBarWidget.visibility = View.GONE }
        verify { trackasiaMap.removeOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify { trackasiaMap.removeOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun enable() {
        val scaleBarPlugin =
            ScaleBarPlugin(mapView, trackasiaMap)
        scaleBarPlugin.create(scaleBarOptions)
        verify(exactly = 1) { trackasiaMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 1) { trackasiaMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
        verify(exactly = 1) { scaleBarWidget.visibility = View.VISIBLE }
        scaleBarPlugin.isEnabled = false
        scaleBarPlugin.isEnabled = true

        assertTrue(scaleBarPlugin.isEnabled)
        verify(exactly = 2) { scaleBarWidget.visibility = View.VISIBLE }
        verify(exactly = 2) { trackasiaMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 2) { trackasiaMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun disable_enable_widgetIsNull() {
        val scaleBarPlugin =
            ScaleBarPlugin(mapView, trackasiaMap)
        scaleBarPlugin.isEnabled = false
        scaleBarPlugin.isEnabled = true

        verify(exactly = 0) { trackasiaMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 0) { trackasiaMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun disableBeforeCreate_ignoreResults() {
        val scaleBarPlugin =
            ScaleBarPlugin(mapView, trackasiaMap)
        scaleBarPlugin.isEnabled = false
        scaleBarPlugin.create(scaleBarOptions)

        assertTrue(scaleBarPlugin.isEnabled)
        verify { scaleBarWidget.visibility = View.VISIBLE }
        verify(exactly = 1) { trackasiaMap.addOnCameraMoveListener(scaleBarPlugin.cameraMoveListener) }
        verify(exactly = 1) { trackasiaMap.addOnCameraIdleListener(scaleBarPlugin.cameraIdleListener) }
    }

    @Test
    fun toggled_invalidateWidget() {
        val scaleBarPlugin =
            ScaleBarPlugin(mapView, trackasiaMap)
        scaleBarPlugin.create(scaleBarOptions)
        verify(exactly = 1) { trackasiaMap.cameraPosition }
        verify(exactly = 1) { scaleBarWidget.setDistancePerPixel(50_000.0) }
        scaleBarPlugin.isEnabled = false
        scaleBarPlugin.isEnabled = true

        verify(exactly = 2) { trackasiaMap.cameraPosition }
        verify(exactly = 2) { scaleBarWidget.setDistancePerPixel(50_000.0) }
    }
}
