package com.trackasia.android.plugins.testapp.activity.markerview

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.trackasia.android.camera.CameraUpdateFactory
import com.trackasia.android.geometry.LatLng
import com.trackasia.android.maps.MapView
import com.trackasia.android.maps.TrackasiaMap
import com.trackasia.android.maps.Style
import com.trackasia.android.plugins.markerview.MarkerView
import com.trackasia.android.plugins.markerview.MarkerViewManager
import com.trackasia.android.plugins.testapp.R
import com.trackasia.android.plugins.testapp.Utils
import java.util.Random

class MarkerViewActivity :
    AppCompatActivity(),
    TrackasiaMap.OnMapLongClickListener,
    TrackasiaMap.OnMapClickListener {

    private val random = Random()
    private var markerViewManager: MarkerViewManager? = null
    private var marker: MarkerView? = null
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)
        mapView = findViewById<View>(R.id.mapView) as MapView

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.getPredefinedStyle("Streets")) { _ ->
                findViewById<View>(R.id.fabStyles).setOnClickListener { mapboxMap.setStyle(Utils.nextStyle) }

                mapboxMap.moveCamera(CameraUpdateFactory.zoomTo(2.0))

                markerViewManager = MarkerViewManager(mapView, mapboxMap)
                createCustomMarker()
                createRandomMarkers()

                mapboxMap.addOnMapLongClickListener(this@MarkerViewActivity)
                mapboxMap.addOnMapClickListener(this@MarkerViewActivity)
            }
        }
    }

    private fun createCustomMarker() {
        // create a custom animation marker view
        val customView = createCustomAnimationView()
        marker = MarkerView(LatLng(), customView)
        marker?.let {
            markerViewManager?.addMarker(it)
        }
    }

    private fun createCustomAnimationView(): View {
        val customView = LayoutInflater.from(this).inflate(R.layout.marker_view, null)
        customView.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        val icon = customView.findViewById<View>(R.id.imageview)
        val animationView = customView.findViewById<View>(R.id.animation_layout)
        icon.setOnClickListener { view ->
            val anim = ValueAnimator.ofInt(animationView.measuredWidth, 350)
            anim.interpolator = AccelerateDecelerateInterpolator()
            anim.addUpdateListener { valueAnimator ->
                val `val` = valueAnimator.animatedValue as Int
                val layoutParams = animationView.layoutParams
                layoutParams.width = `val`
                animationView.layoutParams = layoutParams
            }
            anim.duration = 1250
            anim.start()
        }
        return customView
    }

    private fun createRandomMarkers() {
        markerViewManager?.let {
            for (i in 0..19) {
                val imageView = ImageView(this@MarkerViewActivity)
                imageView.setImageResource(R.drawable.ic_car)
                imageView.layoutParams = FrameLayout.LayoutParams(56, 56)
                val markerView = MarkerView(createRandomLatLng(), imageView)
                it.addMarker(markerView)
            }
        }
    }

    private fun createRandomLatLng(): LatLng {
        return LatLng(
            random.nextDouble() * -180.0 + 90.0,
            random.nextDouble() * -360.0 + 180.0,
        )
    }

    override fun onMapLongClick(point: LatLng): Boolean {
        marker?.let {
            markerViewManager?.removeMarker(it)
        }
        return true
    }

    override fun onMapClick(point: LatLng): Boolean {
        marker?.setLatLng(point)
        return true
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
        markerViewManager?.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}
