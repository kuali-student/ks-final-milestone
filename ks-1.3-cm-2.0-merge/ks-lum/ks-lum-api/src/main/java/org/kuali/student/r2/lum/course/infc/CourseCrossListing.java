package org.kuali.student.r2.lum.course.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

public interface CourseCrossListing extends IdNamelessEntity {

    /**
     * 
     * This method ...
     * 
     * @return
     */
    public String getCode();

    /**
     * 
     * This method ...
     * 
     * @return
     */
    public String getSubjectArea();
    
    /**
     * 
     * This method ...
     * 
     * @return
     */
    public String getDepartment();

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number.
     */
    public String getCourseNumberSuffix();
}
