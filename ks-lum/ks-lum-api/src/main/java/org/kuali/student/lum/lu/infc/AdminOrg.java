package org.kuali.student.lum.lu.infc;

import org.kuali.student.common.infc.IdEntity;

public interface AdminOrg extends IdEntity {

    /**
     * Unique identifier for an organization.
     * 
     * @required
     * @name Organization Id
     */
    public String getOrgId();

    /**
     * @name Primary
     */
    public boolean isPrimary();

}
