package org.trackasia.android.plugins.annotation;

import androidx.annotation.Nullable;
import org.trackasia.android.style.layers.Layer;
import org.trackasia.android.style.sources.GeoJsonOptions;
import org.trackasia.android.style.sources.GeoJsonSource;

interface CoreElementProvider<L extends Layer> {

    String getLayerId();

    String getSourceId();

    L getLayer();

    GeoJsonSource getSource(@Nullable GeoJsonOptions geoJsonOptions);
}
