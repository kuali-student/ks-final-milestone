package org.kuali.student.enrollment.class1.lpr.model;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import org.kuali.student.common.entity.TimeAmount;

public class LprRosterEntity {
    
    private List<String> associatedLuiIds;

    @Column(name = "MAXIMUM_CAPACITY")
    private Integer maximumCapacity;

    @Column(name = "CHECK_IN_REQUIRED")
    private Boolean checkInRequired;

    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(Integer maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public Boolean getCheckInRequired() {
        return checkInRequired;
    }

    public void setCheckInRequired(Boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    public TimeAmount getCheckInFrequency() {
        return checkInFrequency;
    }

    public void setCheckInFrequency(TimeAmount checkInFrequency) {
        this.checkInFrequency = checkInFrequency;
    }

    @Embedded
    @Column(name = "CHECK_IN_FREQUENCY")
    private TimeAmount checkInFrequency;
    
    
    
}
