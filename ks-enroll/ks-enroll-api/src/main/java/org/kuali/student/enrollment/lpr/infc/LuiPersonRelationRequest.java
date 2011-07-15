package org.kuali.student.enrollment.lpr.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

public interface LuiPersonRelationRequest extends IdEntity {
    
    /**
     * 
     * This method ...
     * 
     * @return
     */
    public String getRequestingPersonId();

    /**
     * 
     * This method ...
     * 
     * @return
     */
    public String getPersonId();

    /**
     * Returns the id of the RegGroup for this item. This is populated for ADD,
     * DROP, UPDATE and SWAP types of RegRequestItem
     * 
     * @return
     */
    public String getNewLuiId();

    /**
     * Returns the existing reg group id. The existing reg group field is
     * populated if we have DROP or SWAP types of Request item.
     * 
     * @return
     */
    public String getExistingLuiId();

    /**
     * Specify the various request (or registration options)
     * 
     * @return
     */
    public List<RequestOption> requestOptions();

}
