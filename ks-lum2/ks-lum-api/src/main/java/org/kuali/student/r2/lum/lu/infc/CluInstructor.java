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
     *
     * @name Org Id
     * @readOnly
     * @required
     */
    public String getOrgId();

    /**
     * Unique identifier for a person record.
     *
     * @name Person Id
     * @readOnly
     * @required
     */
    public String getPersonId();

    /**
     * Any override information for the person
     *
     * @name Person Info Override
     */
    public String getPersonInfoOverride();
}
