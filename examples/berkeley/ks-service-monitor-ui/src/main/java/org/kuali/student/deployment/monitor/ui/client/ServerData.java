package org.kuali.student.deployment.monitor.ui.client;

import java.io.Serializable;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ServerData implements Serializable, IsSerializable {

    //~ Static fields/initializers ---------------------------------------------

    static final long serialVersionUID = -2646813418138159526L;
    static final String NL = "\n";

    //~ Instance fields --------------------------------------------------------

    public String hostName;
    public String serviceName;
    public int threadCount;
    public int availableProcessors;
    public Date startDate;

    //~ Methods ----------------------------------------------------------------

    @Override public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Host: ").append(hostName).append(NL);
        buf.append("Service: ").append(serviceName).append(NL);
        buf.append("Service started at: ").append(startDate).append(NL);
        buf.append("Active Threads: ").append(threadCount).append(NL);
        buf.append("Available processors: ").append(availableProcessors);

        return buf.toString();
    }


}
