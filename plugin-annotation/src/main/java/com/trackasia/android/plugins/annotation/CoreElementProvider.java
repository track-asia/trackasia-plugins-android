package com.trackasia.android.plugins.annotation;

import com.trackasia.android.style.layers.Layer;
import com.trackasia.android.style.sources.GeoJsonOptions;
import com.trackasia.android.style.sources.GeoJsonSource;

import androidx.annotation.Nullable;

interface CoreElementProvider<L extends Layer> {

    String getLayerId();

    String getSourceId();

    L getLayer();

    GeoJsonSource getSource(@Nullable GeoJsonOptions geoJsonOptions);
}
