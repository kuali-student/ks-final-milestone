package org.kuali.student.r2.lum.lu.infc;

import org.kuali.student.r2.common.infc.IdEntity;

public interface AdminOrg  extends IdEntity{
    
    /**
     * Unique identifier for an organization.
     * 
     * @required
     * @name Organization Id
     */
    public String getOrgId();

    /**
     * 
     * 
     * @name Primary
     */
    public boolean isPrimary();

}
