package com.trackasia.android.plugins.offline.ui;

import com.trackasia.android.offline.OfflineRegionDefinition;

public interface RegionSelectedCallback {

    void onSelected(OfflineRegionDefinition definition, String regionName);

    void onCancel();
}
