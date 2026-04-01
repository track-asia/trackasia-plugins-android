package com.trackasia.android.plugins.offline.offline;

public class OfflineConstants {

    private OfflineConstants() {
        // No Instances
    }

    static final String ACTION_START_DOWNLOAD = "com.trackasia.android.plugins.offline.download.start";
    static final String ACTION_CANCEL_DOWNLOAD = "com.trackasia.android.plugins.offline.download.cancel";
    static final String ACTION_OFFLINE = "com.trackasia.android.plugins.offline";
    static final String KEY_STATE = "com.trackasia.android.plugins.offline.state";
    static final String STATE_STARTED = "com.trackasia.android.plugins.offline.state.started";
    static final String STATE_FINISHED = "com.trackasia.android.plugins.offline.state.complete";
    static final String STATE_ERROR = "com.trackasia.android.plugins.offline.state.error";
    static final String STATE_CANCEL = "com.trackasia.android.plugins.offline.state.cancel";
    static final String STATE_PROGRESS = "com.trackasia.android.plugins.offline.state.progress";
    static final String KEY_BUNDLE_OFFLINE_REGION = "com.trackasia.android.plugins.offline.region";
    static final String KEY_BUNDLE_ERROR = "com.trackasia.android.plugins.offline.error";
    static final String KEY_BUNDLE_MESSAGE = "com.trackasia.android.plugins.offline.error";
    static final String KEY_PROGRESS = "com.trackasia.android.plugins.offline.progress";
    public static final String NOTIFICATION_CHANNEL = "offline";

    public static final String RETURNING_DEFINITION = "com.trackasia.android.plugins.offline.returning.definition";
    public static final String RETURNING_REGION_NAME = "com.trackasia.android.plugins.offline.returing.region.name";

    public static final String KEY_BUNDLE = "com.trackasia.android.plugins.offline.download.object";
}
