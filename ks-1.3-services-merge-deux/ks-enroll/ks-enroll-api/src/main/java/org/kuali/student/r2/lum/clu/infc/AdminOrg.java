package org.kuali.student.r2.lum.clu.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Admin Org information
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface AdminOrg extends IdEntity {

    /**
     * Unique identifier for an organization.
     *
     * @name Org Id
     * @readOnly
     * @required
     */
    public String getOrgId();

    /**
     * @name Primary
     */
    public Boolean getIsPrimary();

}
