package org.kuali.student.deployment.monitor.ui.client;

import java.io.Serializable;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;


public class VersionData implements Serializable, IsSerializable {

    //~ Static fields/initializers ---------------------------------------------

    static final long serialVersionUID = -1217853688730600842L;
    static final String NL = "\n";

    //~ Instance fields --------------------------------------------------------

    public String buildDate;
    public String version;
    public String buildNumber;

    //~ Methods ----------------------------------------------------------------

    @Override public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Version: ").append(version).append(NL);
        buf.append("Build Number: ").append(buildNumber).append(NL);
        buf.append("Build Date: ").append(buildDate);

        return buf.toString();

    }


}
