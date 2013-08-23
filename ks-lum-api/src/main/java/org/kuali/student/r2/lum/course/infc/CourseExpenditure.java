package org.kuali.student.r2.lum.course.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.HasAttributes;
import org.kuali.student.r2.lum.clu.infc.AffiliatedOrg;

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
