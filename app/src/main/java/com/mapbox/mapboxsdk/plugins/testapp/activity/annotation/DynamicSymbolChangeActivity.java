package com.trackasia.android.plugins.testapp.activity.annotation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trackasia.android.camera.CameraPosition;
import com.trackasia.android.camera.CameraUpdateFactory;
import com.trackasia.android.geometry.LatLng;
import com.trackasia.android.maps.MapView;
import com.trackasia.android.maps.TrackasiaMap;
import com.trackasia.android.maps.Style;
import com.trackasia.android.plugins.annotation.Symbol;
import com.trackasia.android.plugins.annotation.SymbolManager;
import com.trackasia.android.plugins.annotation.SymbolOptions;
import com.trackasia.android.plugins.testapp.R;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Test activity showcasing updating a Marker position, title, icon and snippet.
 */
public class DynamicSymbolChangeActivity extends AppCompatActivity {

    private static final LatLng LAT_LNG_CHELSEA = new LatLng(51.481670, -0.190849);
    private static final LatLng LAT_LNG_ARSENAL = new LatLng(51.555062, -0.108417);

    private static final String ID_ICON_1 = "com.mapbox.annotationplugin.icon.1";
    private static final String ID_ICON_2 = "com.mapbox.annotationplugin.icon.2";

    private SymbolManager symbolManager;
    private MapView mapView;
    private TrackasiaMap mapboxMap;
    private Symbol symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);

        mapView = findViewById(R.id.mapView);
        mapView.setTag(false);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap -> {
            DynamicSymbolChangeActivity.this.mapboxMap = mapboxMap;

            LatLng target = new LatLng(51.506675, -0.128699);

            mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                    .bearing(90)
                    .tilt(40)
                    .zoom(10)
                    .target(target)
                    .build()
            ));

            mapboxMap.setStyle(new Style.Builder()
                    .fromUri(Style.getPredefinedStyle("Streets"))
                //.withImage(ID_ICON_1, generateBitmap(R.drawable.mapbox_ic_place), true)
                //.withImage(ID_ICON_2, generateBitmap(R.drawable.mapbox_ic_offline), true)
                , style -> {
                    symbolManager = new SymbolManager(mapView, mapboxMap, style);
                    symbolManager.setIconAllowOverlap(true);
                    symbolManager.setTextAllowOverlap(true);

                    // Create Symbol
                    SymbolOptions SymbolOptions = new SymbolOptions()
                        .withLatLng(LAT_LNG_CHELSEA)
                        .withIconImage(ID_ICON_1);

                    symbol = symbolManager.create(SymbolOptions);
                });


        });

        FloatingActionButton fab = findViewById(R.id.fabStyles);
        fab.setVisibility(MapView.VISIBLE);
        fab.setOnClickListener(view -> {
            if (mapboxMap != null) {
                updateSymbol();
            }
        });
    }

    private void updateSymbol() {
        // update model
        boolean first = (boolean) mapView.getTag();
        mapView.setTag(!first);

        // update symbol
        symbol.setLatLng(first ? LAT_LNG_CHELSEA : LAT_LNG_ARSENAL);
        symbol.setIconImage(first ? ID_ICON_1 : ID_ICON_2);
        symbolManager.update(symbol);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (symbolManager != null) {
            symbolManager.onDestroy();
        }

        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private Bitmap generateBitmap(@DrawableRes int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        return getBitmapFromDrawable(drawable);
    }

    static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            // width and height are equal for all assets since they are ovals.
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }
}
