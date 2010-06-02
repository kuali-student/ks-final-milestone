package org.kuali.student.deployment.monitor.ui.client;

import java.io.Serializable;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;


public class RequestData implements Serializable, IsSerializable {

    //~ Static fields/initializers ---------------------------------------------

	private static final long serialVersionUID = -6895846249377782761L;
	/**
	 * 
	 */
	
	private static final String format =
        "Total Requests:  %s\nAverage Request Time: %s ms.\n" +
        "Max Request Time: %s\n Start Time:  %s\n";
    static final String NL = "\n";

    //~ Instance fields --------------------------------------------------------

    public String totalRequests;
    public String averageRequestTime;
    public String maxRequestTime;
    public String startTime;

    //~ Methods ----------------------------------------------------------------

    @Override public String toString() {

        //  return String.format(format, totalRequests, averageRequestTime,
        //       maxRequestTime, startTime);
        StringBuilder buf = new StringBuilder();
        buf.append("Total Requests: ").append(totalRequests).append(NL);
        buf.append("Average Request Time: ").append(averageRequestTime).append(
            NL);
        buf.append("Max Request Time: ").append(maxRequestTime).append(NL);
        buf.append("Start Time: ").append(startTime).append(NL);

        return buf.toString();


    }


}
