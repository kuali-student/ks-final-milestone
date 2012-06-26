package org.kuali.student.enrollment.courseregistration.infc;


import java.util.List;


import org.kuali.student.r2.common.infc.OperationStatus;

public interface RegResponse {
    /**
     * This method gets the item operation status info for the registration response
     * 
     * @return
     */
    public OperationStatus getOperationStatus ();

    /**
     * The status of registration request id associated with this registration request.
     * 
     * @return
     */
    public String getRegRequestId();
    
    /**
     * 
     * The individual registration response items for the registration response
     * 
     * @return
     */
    public List<? extends RegResponseItem> getRegResponseItems();
   
}
