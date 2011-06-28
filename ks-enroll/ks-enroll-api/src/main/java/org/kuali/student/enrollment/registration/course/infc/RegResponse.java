package org.kuali.student.enrollment.registration.course.infc;


import java.util.List;


import org.kuali.student.enrollment.registration.course.dto.RegResponseItemInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.kuali.student.r2.common.infc.IdEntity;

public interface RegResponse extends IdEntity {
    /**
     * This method gets the item operation status info for the registration response
     * 
     * @return
     */
    public OperationStatusInfo getOperationStatusInfo();

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
    public List<RegResponseItem> getRegResponseItemInfos();
   
}
