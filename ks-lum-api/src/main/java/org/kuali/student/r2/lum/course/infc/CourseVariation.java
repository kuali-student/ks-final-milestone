package org.kuali.student.r2.lum.course.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

public interface CourseVariation extends IdNamelessEntity {

    /**
     * Full name of the variation, commonly used on catalogues
     */
    public String getVariationTitle();

    /**
     * 
     */
    public String getSubjectArea();

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number.
     */
    public String getCourseNumberSuffix();

    /**
     * A number that indicates the sequence or order of variation in cases where
     * several different variations have the same offical Identifier
     */
    public String getVariationCode();

}
