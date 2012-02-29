package org.kuali.student.lum.course.infc;

import java.util.List;

import org.kuali.student.common.infc.HasAttributes;
import org.kuali.student.lum.lu.infc.AffiliatedOrg;

/**
 * Detailed information about expenditure for the course.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface CourseExpenditure extends HasAttributes {

    /**
     * List of affiliated organizations.
     */
    public List<? extends AffiliatedOrg> getAffiliatedOrgs();
}
