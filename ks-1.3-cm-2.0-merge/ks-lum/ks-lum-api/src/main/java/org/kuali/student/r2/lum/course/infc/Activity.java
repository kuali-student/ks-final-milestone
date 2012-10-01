package org.kuali.student.r2.lum.course.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.Amount;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.TimeAmount;

/**
 * Detailed information about a single course activity.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface Activity extends IdEntity {
    /**
     * The standard duration of the Course.
     * 
     * @name Duration
     */
    public TimeAmount getDuration();

    /**
     * The organizations that represents the Subject area of the course.
     * 
     * @name Units Content Owner
     */
    public List<String> getUnitsContentOwner();

    /**
     * Default enrollment estimate for this CLU.
     * 
     * @name Enrollment Estimate
     */
    public Integer getDefaultEnrollmentEstimate();

    /**
     * Contact Hours for an Activity.
     * 
     * @name Contact Hours
     */
    public Amount getContactHours();

}
