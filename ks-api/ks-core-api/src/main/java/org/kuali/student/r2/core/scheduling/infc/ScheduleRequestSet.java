package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;

/**
 * @Author Kuali Student ks.collab@kuali.org
 * This set captures the 'grouping of clients around shared resources'
 */
public interface ScheduleRequestSet extends IdEntity {
    /**
     *  Reference object identifiers for entities that have a shared interest in
     *  the associated Schedule Request components
     *
     * @name Ref Object Ids
     */
    public List<String> getRefObjectIds();

    /**
     * Reference objects type key
     *
     * @name Ref Object Type Key
     * @required
     * @readOnly
     */
    public String getRefObjectTypeKey();

    /**
     * A flag that holds whether the max enrollment is shared across
     * the AOs or each AO is responsible for an individual max
     * enrollment that is then summed up for the colocation.
     *
     * @return true if the max enrollment is shared across the AOs
     * @name Is Max Enrollment Shared
     */
    public Boolean getIsMaxEnrollmentShared();

    /**
     * Maximum enrollment for the set of AOs (either shared or
     * calculated).  If the flag getIsMaxEnrollmentShared is true then
     * this is a shared maximum enrollment, else it's a calculated
     * maximum enrollment.
     *
     * @name Maximum Enrollment
     */
    public Integer getMaximumEnrollment();
}
