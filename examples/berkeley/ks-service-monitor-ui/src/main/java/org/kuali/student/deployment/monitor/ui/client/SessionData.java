package org.kuali.student.deployment.monitor.ui.client;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;


public class SessionData implements Serializable, IsSerializable {

    //~ Static fields/initializers ---------------------------------------------

    static final long serialVersionUID = 107633178462022365L;
    static final String NL = "\n";

    //~ Instance fields --------------------------------------------------------

    public int totalSessionCount;
    public int activeSessionCount;
    public int maxConcurrentSessionCount;

    //~ Methods ----------------------------------------------------------------

    @Override public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Total Sessions Serviced: ").append(totalSessionCount)
        .append(NL);
        buf.append("Active Sessions: ").append(activeSessionCount).append(NL);
        buf.append("Maximum Concurrent Sessions: ").append(
            maxConcurrentSessionCount);

        return buf.toString();


    }

}
