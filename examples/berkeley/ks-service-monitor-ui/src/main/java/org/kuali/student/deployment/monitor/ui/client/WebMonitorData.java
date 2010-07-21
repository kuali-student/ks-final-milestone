package org.kuali.student.deployment.monitor.ui.client;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;


public class WebMonitorData implements Serializable, IsSerializable {

    //~ Static fields/initializers ---------------------------------------------

    static final long serialVersionUID = -8073889277917318191L;
    static final String NL = "\n";

    //~ Instance fields --------------------------------------------------------

    public ServerData serverData;
    public VersionData versionData;
    public SessionData sessionData;
    public RequestData requestData;

    //~ Methods ----------------------------------------------------------------

    @Override public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("SERVER: ").append(serverData).append(NL);
        buf.append("SESSION:  ").append(sessionData).append(NL);
        buf.append("REQUEST: ").append(requestData).append(NL);
        buf.append("VERSION: ").append(versionData);

        return buf.toString();
    }


}
