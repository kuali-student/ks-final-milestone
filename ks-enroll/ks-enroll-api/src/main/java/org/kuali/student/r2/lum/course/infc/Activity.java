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
    public int getDefaultEnrollmentEstimate();

    /**
     * Contact Hours for an Activity.
     * 
     * @name Contact Hours
     */
    public Amount getContactHours();

    /**
     * The current status of the course. The values for this field are
     * constrained to those in the luState enumeration. A separate setup
     * operation does not exist for retrieval of the meta data around this
     * value. This field may not be updated through updating this structure and
     * must instead be updated through a dedicated operation.
     * 
     * @name State
     */
    public String getState();
}
