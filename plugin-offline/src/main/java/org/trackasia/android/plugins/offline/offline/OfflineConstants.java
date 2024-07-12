package org.trackasia.android.plugins.offline.offline;

public class OfflineConstants {

    private OfflineConstants() {
        // No Instances
    }

    static final String ACTION_START_DOWNLOAD = "org.trackasia.android.plugins.offline.download.start";
    static final String ACTION_CANCEL_DOWNLOAD = "org.trackasia.android.plugins.offline.download.cancel";
    static final String ACTION_OFFLINE = "org.trackasia.android.plugins.offline";
    static final String KEY_STATE = "org.trackasia.android.plugins.offline.state";
    static final String STATE_STARTED = "org.trackasia.android.plugins.offline.state.started";
    static final String STATE_FINISHED = "org.trackasia.android.plugins.offline.state.complete";
    static final String STATE_ERROR = "org.trackasia.android.plugins.offline.state.error";
    static final String STATE_CANCEL = "org.trackasia.android.plugins.offline.state.cancel";
    static final String STATE_PROGRESS = "org.trackasia.android.plugins.offline.state.progress";
    static final String KEY_BUNDLE_OFFLINE_REGION = "org.trackasia.android.plugins.offline.region";
    static final String KEY_BUNDLE_ERROR = "org.trackasia.android.plugins.offline.error";
    static final String KEY_BUNDLE_MESSAGE = "org.trackasia.android.plugins.offline.error";
    static final String KEY_PROGRESS = "org.trackasia.android.plugins.offline.progress";
    public static final String NOTIFICATION_CHANNEL = "offline";

    public static final String RETURNING_DEFINITION = "org.trackasia.android.plugins.offline.returning.definition";
    public static final String RETURNING_REGION_NAME = "org.trackasia.android.plugins.offline.returing.region.name";

    public static final String KEY_BUNDLE = "org.trackasia.android.plugins.offline.download.object";
}
