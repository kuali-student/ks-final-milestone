package org.kuali.student.ap.planner;

import org.kuali.student.ap.common.infc.HasUniqueId;

import java.io.Serializable;
import java.util.Date;

public class PlannerTermNote implements HasUniqueId, Serializable {

	private static final long serialVersionUID = 5885238483581189013L;

	private String parentUniqueId;
	private String uniqueId;
	private String termId;
	private String termNote;
	private Date date;

	public String getParentUniqueId() {
		return parentUniqueId;
	}

	public void setParentUniqueId(String parentUniqueId) {
		this.parentUniqueId = parentUniqueId;
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTermNote() {
		return termNote;
	}

	public void setTermNote(String termNote) {
		this.termNote = termNote;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
