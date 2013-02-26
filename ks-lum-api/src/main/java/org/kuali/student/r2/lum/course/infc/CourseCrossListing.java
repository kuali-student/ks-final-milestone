package org.kuali.student.r2.lum.course.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

public interface CourseCrossListing extends IdNamelessEntity {

    /**
     * 
     * This is the course code, e.g. from ENGL101A it's ENGL101
     * 
     * @return
     */
    public String getCode();

    /**
     * 
     * This is the subject area also known as the subject code.
     * E.g., from ENGL101A, it's ENGL.
     * 
     * @return
     */
    public String getSubjectArea();
    
    /**
     * 
     * This method has been replaced by getSubjectOrgId()
     * 
     * @return
     */
    @Deprecated
    public String getDepartment();

    /**
     * This is the org ID associated with the subject code in the Org service.
     * Reference implementation uses ORGID-[subject code] as the ID, e.g.
     * ORGID-ENGL to refer to the ENGL subject code.  This need not be the case
     * among implementing institutions.  It's expected this ID and the subject area
     * (aka subject code) should be kept in alignment.  That is, the subject area
     * should match with the short name of the Org entity with this subject org id.
     * @return
     */
    public String getSubjectOrgId();

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number.
     * E.g., from ENGL101A, it's A.
     * If there is no suffix, it's null.
     */
    public String getCourseNumberSuffix();
}
