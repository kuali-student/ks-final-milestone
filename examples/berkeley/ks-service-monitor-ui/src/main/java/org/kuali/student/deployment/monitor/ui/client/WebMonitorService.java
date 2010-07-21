package org.kuali.student.deployment.monitor.ui.client;

import com.google.gwt.user.client.rpc.RemoteService;


public interface WebMonitorService extends RemoteService {

    //~ Methods ----------------------------------------------------------------

    /**
     * @see org.kuali.student.deployment.monitor.ui.client.WebMonitorData
     * @return the WebMonitorData for a deployed web application
     */
    WebMonitorData fetchWebMonitorData();


}
