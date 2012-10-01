package org.kuali.student.core.bo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.kuali.rice.kns.bo.InactivateableFromTo;
import org.kuali.student.core.bo.util.InactivatableFromToHelper;

public class KsMetaInactivatableFromToBase extends KsMetaBusinessObjectBase
		implements InactivateableFromTo {

	private static final long serialVersionUID = 6285786348480193634L;
	
	@Column(name = "EFF_DT")
    protected Timestamp activeFromDate;
    
    @Column(name = "EXPIR_DT")
    protected Timestamp activeToDate;
    
    @Transient
    protected Timestamp activeAsOfDate;
    

	@Override
	public Timestamp getActiveAsOfDate() {
		return activeAsOfDate;
	}

	@Override
	public Timestamp getActiveFromDate() {
		return activeFromDate;
	}

	@Override
	public Timestamp getActiveToDate() {
		return activeToDate;
	}

	@Override
	public void setActiveAsOfDate(Timestamp activeAsOfDate) {
		this.activeAsOfDate = activeAsOfDate;
	}

	@Override
	public void setActiveFromDate(Timestamp from) {
		this.activeFromDate = from;
	}

	@Override
	public void setActiveToDate(Timestamp to) {
		this.activeToDate = to;
	}

	@Override
	public boolean isActive() {
		return InactivatableFromToHelper.isActive(this);
	}

	@Override
	public void setActive(boolean active) {
		// do nothing
	}

}
