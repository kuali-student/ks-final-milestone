package org.kuali.student.deployment.monitor.ui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public final class Application implements EntryPoint {

    //~ Instance fields --------------------------------------------------------

    WebMonitorComponent monitor;
    WebMonitorServiceAsync serviceProxy;
    ServiceStatusCallback statusCallback;

    //~ Methods ----------------------------------------------------------------

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        monitor = new WebMonitorComponent(this);
        RootPanel.get().add(monitor);
        initRPC();
        statusCallback = new ServiceStatusCallback();
        fetchWebMonitorData();
        
    }

    public void fetchWebMonitorData() {
        serviceProxy.fetchWebMonitorData(statusCallback);
    }

    private void initRPC() {
        serviceProxy = (WebMonitorServiceAsync) GWT.create(
                WebMonitorService.class);

        ServiceDefTarget target = (ServiceDefTarget) serviceProxy;
        String entryPoint = GWT.getModuleBaseURL() +
            Constants.SERVICE_ENTRY_POINT_NAME;
        GWT.log("entry point is: " + entryPoint, null);
        target.setServiceEntryPoint(entryPoint);

    }


    //~ Inner Classes ----------------------------------------------------------

    public abstract class AbstractAsyncCallback implements AsyncCallback {

        //~ Methods ------------------------------------------------------------

        public void onFailure(Throwable caught) {
            GWT.log("RPC Error: ", caught);
        }

        public void onSuccess(Object result) {
            GWT.log("RPC success", null);
        }
    }

    public class ServiceStatusCallback extends AbstractAsyncCallback {

        //~ Methods ------------------------------------------------------------

        public void onSuccess(Object result) {
            WebMonitorData data = (WebMonitorData) result;
            GWT.log(data.toString(), null);
            monitor.update(data);
        }
    }


}
