package org.kuali.student.lum.course.infc;

import java.util.List;

import org.kuali.student.common.infc.Amount;
import org.kuali.student.common.infc.Attribute;
import org.kuali.student.common.infc.IdEntity;
import org.kuali.student.common.infc.TimeAmount;

/**
 * Detailed information about a single course activity.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface Activity extends IdEntity, Attribute {
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
    public int getDefaultEnrollmentEstimate();

    /**
     * Contact Hours for an Activity.
     * 
     * @name Contact Hours
     */
    public Amount getContactHours();

}
