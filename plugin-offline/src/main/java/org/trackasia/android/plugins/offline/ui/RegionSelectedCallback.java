package org.trackasia.android.plugins.offline.ui;

import org.trackasia.android.offline.OfflineRegionDefinition;

public interface RegionSelectedCallback {

    void onSelected(OfflineRegionDefinition definition, String regionName);

    void onCancel();
}
