package org.trackasia.android.plugins.testapp.activity.scalebar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.trackasia.android.maps.TrackAsiaMap
import org.trackasia.android.maps.MapView
import org.trackasia.android.maps.Style
import org.trackasia.android.plugins.scalebar.ScaleBarOptions
import org.trackasia.android.plugins.scalebar.ScaleBarPlugin
import org.trackasia.android.plugins.testapp.databinding.ActivityScalebarBinding
import org.trackasia.android.style.layers.LineLayer
import org.trackasia.android.style.sources.GeoJsonSource
import org.trackasia.geojson.LineString
import org.trackasia.geojson.Point
import org.trackasia.turf.TurfConstants
import org.trackasia.turf.TurfMeasurement

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
            trackasiaMap.setStyle("https://api.maptiler.com/maps/basic-v2/style.json?key=FTNrjsa7Nahw874tmMi7") {
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
