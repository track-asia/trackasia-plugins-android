package com.trackasia.android.plugins.testapp.activity.scalebar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trackasia.android.maps.TrackAsiaMap
import com.trackasia.android.maps.MapView
import com.trackasia.android.maps.Style
import com.trackasia.android.plugins.scalebar.ScaleBarOptions
import com.trackasia.android.plugins.scalebar.ScaleBarPlugin
import com.trackasia.android.plugins.testapp.TestStyles
import com.trackasia.android.plugins.testapp.databinding.ActivityScalebarBinding
import com.trackasia.android.style.layers.LineLayer
import com.trackasia.android.style.sources.GeoJsonSource
import com.trackasia.geojson.LineString
import com.trackasia.geojson.Point
import com.trackasia.turf.TurfConstants
import com.trackasia.turf.TurfMeasurement

/**
 * Activity showing a scalebar used on a MapView.
 */
class ScalebarActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var binding: ActivityScalebarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScalebarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { trackasiaMap ->
            trackasiaMap.setStyle(TestStyles.BRIGHT.url) {
                addScalebar(trackasiaMap)
                setupTestLine(it)
            }
        }
    }

    private fun addScalebar(trackasiaMap: TrackAsiaMap) {
        val scaleBarPlugin =
            ScaleBarPlugin(mapView, trackasiaMap)
        val scaleBarOptions = ScaleBarOptions(this)
        scaleBarOptions
            .setTextColor(android.R.color.black)
            .setTextSize(40f)
            .setBarHeight(5f)
            .setBorderWidth(2f)
            .setRefreshInterval(15)
            .setMarginTop(15f)
            .setMarginLeft(16f)
            .setTextBarMargin(15f)
            .setMaxWidthRatio(0.5f)
            .setShowTextBorder(true)
            .setTextBorderWidth(5f)

        scaleBarPlugin.create(scaleBarOptions)
        binding.fabScaleWidget.setOnClickListener {
            scaleBarPlugin.isEnabled = !scaleBarPlugin.isEnabled
        }
    }

    private fun setupTestLine(style: Style) {
        val source = GeoJsonSource("source-id")
        val lineLayer = LineLayer("layer-id", source.id)
        val startPoint: Point = Point.fromLngLat(-122.447244, 37.769145)
        val endPoint: Point =
            TurfMeasurement.destination(startPoint, 200.0, 90.0, TurfConstants.UNIT_METERS)
        val pointList: List<Point> = listOf(startPoint, endPoint)
        source.setGeoJson(LineString.fromLngLats(pointList))
        style.addSource(source)
        style.addLayer(lineLayer)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
