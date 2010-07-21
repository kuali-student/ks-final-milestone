package org.kuali.student.deployment.monitor.ui.client;

import com.google.gwt.user.client.ui.Label;


public class Constants {

    //~ Static fields/initializers ---------------------------------------------

    public static final String MONITOR_URI = "sys/monitor/";
    public static final String SERVICE_ENTRY_POINT_NAME = MONITOR_URI +
        "service-status";
    public static final String MEMORY_SNAPSHOT_NAME = MONITOR_URI +
        "memory-snapshot";

    //~ Enums ------------------------------------------------------------------

    enum VersionInfo {
        VERSION, BUILD_NUMBER, BUILD_DATE
    }

    enum ServerInfo {
        HOST_NAME, SERVICE_NAME, START_DATE, THREAD_COUNT
    }

    enum SessionInfo {
        TOTAL_SESSION_COUNT, ACTIVE_SESSION_COUNT, MAX_CONCURRENT_SESSION_COUNT
    }

    //~ Constructors -----------------------------------------------------------

    // never instantiate this class
    private Constants() {

    }


}
