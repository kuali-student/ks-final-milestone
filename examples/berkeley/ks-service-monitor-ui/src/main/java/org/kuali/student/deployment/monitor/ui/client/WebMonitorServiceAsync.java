package org.kuali.student.deployment.monitor.ui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface WebMonitorServiceAsync {

    //~ Methods ----------------------------------------------------------------

    /**
     * @see org.kuali.student.deployment.monitor.ui.client.WebMonitorData
     * @return the WebMonitorData for a deployed web application
     */
    void fetchWebMonitorData(AsyncCallback callback);


}
