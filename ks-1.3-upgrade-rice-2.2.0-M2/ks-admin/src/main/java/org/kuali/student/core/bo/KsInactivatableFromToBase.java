package org.kuali.student.core.bo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.kuali.rice.krad.bo.InactivatableFromTo;
import org.kuali.student.core.bo.util.InactivatableFromToHelper;

public abstract class KsInactivatableFromToBase extends KsBusinessObjectBase implements InactivatableFromTo {

    private static final long serialVersionUID = 1L;

    @Column(name = "EFF_DT")
    protected Timestamp activeFromDate;
    
    @Column(name = "EXPIR_DT")
    protected Timestamp activeToDate;
    
    @Transient
    protected Timestamp activeAsOfDate;
    

    /**
     * Returns active if the {@link #getActiveAsOfDate()} (current time used if not set) is between
     * the from and to dates. Null dates are considered to indicate an open range.
     */
    public boolean isActive() {
        return InactivatableFromToHelper.isActive(this);
    }
    
    public void setActive(boolean active) {
        // do nothing
    }

    public void setActiveFromDate(Timestamp from) {
        this.activeFromDate = from;
    }

    public void setActiveToDate(Timestamp to) {
        this.activeToDate = to;
    }

    public Timestamp getActiveFromDate() {
        return this.activeFromDate;
    }

    public Timestamp getActiveToDate() {
        return this.activeToDate;
    }

    public Timestamp getActiveAsOfDate() {
        return this.activeAsOfDate;
    }

    public void setActiveAsOfDate(Timestamp activeAsOfDate) {
        this.activeAsOfDate = activeAsOfDate;
    }

}
