package org.kuali.student.r2.lum.lu.infc;

import org.kuali.student.r2.common.infc.HasAttributes;

/**
 * Information about a potential instructor for a clu.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface CluInstructor extends HasAttributes {
    /**
     * Unique identifier for an organization. This indicates which organization
     * this individual is associated with for the purposes of this clu.
     */
    public String getOrgId();

    /**
     * Unique identifier for a person record.
     */
    public String getPersonId();

    /**
     * @return the personInfoOverride
     */
    public String getPersonInfoOverride();
}
