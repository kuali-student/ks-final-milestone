package org.kuali.student.ap.framework.context;

import org.kuali.student.r2.lum.course.dto.CourseInfo;

public interface CreditsFormatter {

    /**
     * Formats credit options list as a String.
     *
     * @param courseInfo
     * @return
     */
    public String formatCredits(CourseInfo courseInfo);

    /**
     * Drop the decimal point and and trailing zero from credits.
     *
     * @return The supplied value minus the trailing ".0"
     */
    public String trimCredits(String credits);

}
