package org.kuali.student.r2.lum.course.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.lum.lu.infc.AffiliatedOrg;

public interface CourseRevenue extends IdNamelessEntity {
    /**
     * A code that identifies the type of the fee with which this revenue is
     * associated with.
     * 
     * @name Fee Type
     */
    public String getFeeType();

    /**
     * List of affiliated organizations.
     * 
     * @name Affiliated Org
     */
    public List<? extends AffiliatedOrg> getAffiliatedOrgs();
}
