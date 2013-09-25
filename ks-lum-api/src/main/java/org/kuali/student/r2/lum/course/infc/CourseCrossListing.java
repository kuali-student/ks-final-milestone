package org.kuali.student.r2.lum.course.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

public interface CourseCrossListing extends IdNamelessEntity {

    /**
     * 
     * This is the concatenation of subject area/code plus the course
     * number suffix of the cross-listed course (i.e., not the original
     * course)
     * E.g., ENGL100, CHEM250A
     * 
     * @return
     */
    public String getCode();

    /**
     * 
     * This is the subject area also known as the subject code.
     * See comments in getCode().
     * E.g., from ENGL101A, it's ENGL.
     * 
     * @return
     */
    public String getSubjectArea();
    
    /**
     * This method has been replaced by getSubjectOrgId()
     */
    @Deprecated
    public String getDepartment();

    /**
     * This is the ID for that subject code as an org within the Org Service.
     * Subject codes are stored in the Org table as an organization.
     * Note: subject org id and subject area/code should be kept aligned (in case
     * the subject code changes, but its ID stays the same).
     *
     * @impl In general, subject org ID should take precedence over subject area
     * since the subject area can be found as the short name of the org corresponding
     * to the subject org ID.
     *
     * @impl Reference implementation has the ID as ORGID-[subject area]
     * For example, ORGID-ENGL
     * This is not a requirement, but makes it easier to create the Org Id
     */
    public String getSubjectOrgId();

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number (i.e., everything besides the subject area/code).
     * See comments in getCode().
     * E.g., from ENGL101A, it's 101A.
     * E.g., from CHEM200, it's 200.
     */
    public String getCourseNumberSuffix();
}
