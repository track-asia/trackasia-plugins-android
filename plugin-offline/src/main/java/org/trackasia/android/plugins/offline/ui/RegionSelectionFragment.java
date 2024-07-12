package org.trackasia.android.plugins.offline.ui;

import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.trackasia.android.camera.CameraUpdateFactory;
import org.trackasia.android.geometry.LatLng;
import org.trackasia.android.geometry.LatLngBounds;
import org.trackasia.android.maps.TrackAsiaMap;
import org.trackasia.android.maps.MapView;
import org.trackasia.android.maps.OnMapReadyCallback;
import org.trackasia.android.maps.Style;
import org.trackasia.android.offline.OfflineRegionDefinition;
import org.trackasia.android.offline.OfflineTilePyramidRegionDefinition;
import org.trackasia.android.plugins.offline.OfflinePluginConstants;
import org.trackasia.android.plugins.offline.R;
import org.trackasia.android.plugins.offline.model.RegionSelectionOptions;
import org.trackasia.android.style.sources.VectorSource;
import org.trackasia.geojson.Feature;
import timber.log.Timber;

import java.util.List;

public class RegionSelectionFragment extends Fragment implements OnMapReadyCallback,
    TrackAsiaMap.OnCameraIdleListener {

    public static final String TAG = "OfflineRegionSelectionFragment";
    private static final String[] LAYER_IDS = new String[]{
        "place-city-lg-n", "place-city-lg-s", "place-city-md-n", "place-city-md-s", "place-city-sm"
    };
    private static final String[] SOURCE_LAYER_IDS = new String[]{
        "place_label", "state_label", "country_label"
    };

    private RegionSelectionOptions options;
    private RegionSelectedCallback selectedCallback;
    private TextView regionNameTextView;
    private TrackAsiaMap trackAsiaMap;
    private String regionName;
    private RectF boundingBox;
    private MapView mapView;
    private View rootView;
    private Style style;

    public static RegionSelectionFragment newInstance() {
        return new RegionSelectionFragment();
    }

    public static RegionSelectionFragment newInstance(@Nullable RegionSelectionOptions regionSelectionOptions) {
        RegionSelectionFragment fragment = new RegionSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(OfflinePluginConstants.REGION_SELECTION_OPTIONS, regionSelectionOptions);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.trackasia_offline_region_selection_fragment, container, false);
        mapView = rootView.findViewById(R.id.trackasia_offline_region_selection_map_view);
        regionNameTextView = rootView.findViewById(R.id.trackasia_offline_region_name_text_view);

        Bundle bundle = getArguments();
        // Get the regionSelectionOptions
        options = bundle.getParcelable(OfflinePluginConstants.REGION_SELECTION_OPTIONS);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        bindClickListeners();
    }

    public RegionSelectedCallback getSelectedCallback() {
        return selectedCallback;
    }

    public void setSelectedCallback(@NonNull RegionSelectedCallback selectedCallback) {
        this.selectedCallback = selectedCallback;
    }

    @Override
    public void onMapReady(final TrackAsiaMap trackasiaMap) {
        this.trackAsiaMap = trackasiaMap;
        trackasiaMap.setStyle(Style.getPredefinedStyle("Streets"), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                RegionSelectionFragment.this.style = style;
                trackasiaMap.addOnCameraIdleListener(RegionSelectionFragment.this);
                if (options != null) {
                    if (options.startingBounds() != null) {
                        trackasiaMap.moveCamera(CameraUpdateFactory.newLatLngBounds(options.startingBounds(), 0));
                    } else if (options.statingCameraPosition() != null) {
                        trackasiaMap.moveCamera(CameraUpdateFactory.newCameraPosition(options.statingCameraPosition()));
                    }
                }
            }
        });
    }

    @Override
    public void onCameraIdle() {
        if (boundingBox == null) {
            boundingBox = getSelectionRegion();
        }
        Timber.v("Camera moved");
        regionName = getOfflineRegionName();
        regionNameTextView.setText(regionName);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        if (trackAsiaMap != null) {
            trackAsiaMap.addOnCameraIdleListener(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        if (trackAsiaMap != null) {
            trackAsiaMap.removeOnCameraIdleListener(this);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    private RectF getSelectionRegion() {
        View selectionBoxView = rootView.findViewById(R.id.trackasia_offline_scrim_view);
        int paddingInPixels = (int) getResources().getDimension(R.dimen.trackasia_offline_scrim_padding);

        float top = selectionBoxView.getY() + paddingInPixels;
        float left = selectionBoxView.getX() + paddingInPixels;
        return new RectF(left, top, selectionBoxView.getWidth() - paddingInPixels,
            selectionBoxView.getHeight() - paddingInPixels);
    }

    public String getOfflineRegionName() {
        List<Feature> featureList = trackAsiaMap.queryRenderedFeatures(boundingBox, LAYER_IDS);
        if (featureList.isEmpty() && style != null) {
            Timber.v("Rendered features empty, attempting to query vector source.");
            VectorSource source = style.getSourceAs("composite");
            if (source != null) {
                featureList = source.querySourceFeatures(SOURCE_LAYER_IDS, null);
            }
        }
        if (!featureList.isEmpty() && featureList.get(0).properties().has("name")) {
            return featureList.get(0).getStringProperty("name");
        }
        return getString(R.string.trackasia_offline_default_region_name);
    }

    OfflineRegionDefinition createRegion() {
        if (trackAsiaMap == null) {
            throw new NullPointerException("trackasiaMap is null and can't be used to create Offline region"
                + "definition.");
        }
        RectF rectF = getSelectionRegion();
        LatLng northEast = trackAsiaMap.getProjection().fromScreenLocation(new PointF(rectF.right, rectF.top));
        LatLng southWest = trackAsiaMap.getProjection().fromScreenLocation(new PointF(rectF.left, rectF.bottom));

        LatLngBounds bounds = new LatLngBounds.Builder().include(northEast).include(southWest).build();
        double cameraZoom = trackAsiaMap.getCameraPosition().zoom;
        float pixelRatio = getActivity().getResources().getDisplayMetrics().density;

        return new OfflineTilePyramidRegionDefinition(
            trackAsiaMap.getStyle().getUrl(), bounds, cameraZoom - 2, cameraZoom + 2, pixelRatio
        );
    }

    private void bindClickListeners() {
        FloatingActionButton button = rootView.findViewById(R.id.trackasia_offline_select_region_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSelectedCallback() != null) {
                    getSelectedCallback().onSelected(createRegion(), regionName);
                }
            }
        });
    }
}