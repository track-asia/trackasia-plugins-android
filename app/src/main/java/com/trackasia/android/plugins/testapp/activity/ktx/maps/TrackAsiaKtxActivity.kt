package com.trackasia.android.plugins.testapp.activity.ktx.maps

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.trackasia.android.geometry.LatLng
import com.trackasia.android.maps.TrackAsiaMap
import com.trackasia.android.maps.MapView
import com.trackasia.android.maps.OnMapReadyCallback
import com.trackasia.android.maps.queryRenderedFeatures
import com.trackasia.android.plugins.testapp.TestStyles
import com.trackasia.android.plugins.testapp.databinding.ActivityMapsKtxBinding

class TrackAsiaKtxActivity : AppCompatActivity(), OnMapReadyCallback, TrackAsiaMap.OnMapClickListener {

    private var trackasiaMap: TrackAsiaMap? = null

    private lateinit var binding: ActivityMapsKtxBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsKtxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(trackasiaMap: TrackAsiaMap) {
        this.trackasiaMap = trackasiaMap
        trackasiaMap.setStyle(TestStyles.BRIGHT.url) {
            trackasiaMap.addOnMapClickListener(this)
            Toast.makeText(this, "Click on the map", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapClick(point: LatLng): Boolean {
        val features = trackasiaMap?.queryRenderedFeatures(point)
        features?.first().let {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        return true
    }

    public override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    public override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        trackasiaMap?.removeOnMapClickListener(this)
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
