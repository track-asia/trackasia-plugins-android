package com.trackasia.android.plugins.testapp.activity.annotation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.trackasia.android.camera.CameraPosition;
import com.trackasia.android.geometry.LatLng;
import com.trackasia.android.maps.TrackAsiaMap;
import com.trackasia.android.maps.MapView;
import com.trackasia.android.maps.Style;
import com.trackasia.android.plugins.annotation.SymbolManager;
import com.trackasia.android.plugins.annotation.SymbolOptions;
import com.trackasia.android.plugins.testapp.TestStyles;
import com.trackasia.android.plugins.testapp.R;
import com.trackasia.android.plugins.testapp.Utils;

import static com.trackasia.android.style.layers.Property.ICON_ANCHOR_BOTTOM;

/**
 * Test activity showcasing to add a Symbol on click.
 * <p>
 * Shows how to use a OnMapClickListener and a OnMapLongClickListener
 * </p>
 */
public class PressForSymbolActivity extends AppCompatActivity {

    public static final String ID_ICON = "id-icon";
    private SymbolManager symbolManager;
    private MapView mapView;
    private TrackAsiaMap trackasiaMap;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(map -> {
            trackasiaMap = map;
            trackasiaMap.setCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(60.169091, 24.939876))
                .zoom(12)
                .tilt(20)
                .bearing(90)
                .build()
            );
            trackasiaMap.addOnMapLongClickListener(this::addSymbol);
            trackasiaMap.addOnMapClickListener(this::addSymbol);
            trackasiaMap.setStyle(getStyleBuilder(TestStyles.BRIGHT.getUrl()), style -> {
                findViewById(R.id.fabStyles).setOnClickListener(v ->
                    trackasiaMap.setStyle(getStyleBuilder(Utils.INSTANCE.getNextStyle())));

                symbolManager = new SymbolManager(mapView, trackasiaMap, style);
                symbolManager.setIconAllowOverlap(true);
                symbolManager.setTextAllowOverlap(true);
            });
        });
    }

    private boolean addSymbol(LatLng point) {
        if (symbolManager == null) {
            return false;
        }

        symbolManager.create(new SymbolOptions()
            .withLatLng(point)
            .withIconImage(ID_ICON)
            .withIconAnchor(ICON_ANCHOR_BOTTOM)
        );
        return true;
    }

    private Style.Builder getStyleBuilder(@NonNull String styleUrl) {
        return new Style.Builder().fromUri(styleUrl);
        //.withImage(ID_ICON, generateBitmap(R.drawable.mapbox_ic_place));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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
    protected void onDestroy() {
        super.onDestroy();
        trackasiaMap.removeOnMapClickListener(this::addSymbol);
        trackasiaMap.removeOnMapLongClickListener(this::addSymbol);

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

