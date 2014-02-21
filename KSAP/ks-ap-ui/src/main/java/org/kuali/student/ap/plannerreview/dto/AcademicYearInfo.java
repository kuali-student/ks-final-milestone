package org.kuali.student.ap.plannerreview.dto;

import java.util.ArrayList;
import java.util.List;

public class AcademicYearInfo implements Comparable<AcademicYearInfo> {

	private String id;
	private String yearDisplay;
	private List<PlanTermInfo> terms = new ArrayList<PlanTermInfo>();
	
	public AcademicYearInfo() {
		
	}
	
	public AcademicYearInfo(String id, String yearDisplay) {
		this.id = id;
		this.yearDisplay = yearDisplay;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYearDisplay() {
		return yearDisplay;
	}
	public void setYearDisplay(String yearDisplay) {
		this.yearDisplay = yearDisplay;
	}
	public List<PlanTermInfo> getTerms() {
		return terms;
	}
	public void setTerms(List<PlanTermInfo> terms) {
		this.terms = terms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcademicYearInfo other = (AcademicYearInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AcademicYearInfo [id=" + id + ", yearDisplay=" + yearDisplay
				+ ", terms=" + terms + "]";
	}
	
	public void addOrUpdateTerm(PlanTermInfo term) {
		if (terms == null) {
			terms = new ArrayList<PlanTermInfo>();
			terms.add(term);
		}
		else {
			PlanTermInfo lookupTerm = getTermById(term.getTermId());
			if (lookupTerm == null) {
				terms.add(term);
			}
			else {
				lookupTerm = term;
			}			
		}
	}
	
	private PlanTermInfo getTermById(String termId) {
		for (PlanTermInfo term : terms) {
			if (term.getTermId().equals(termId))
				return term;
		}
		return null;
	}

	@Override
	public int compareTo(AcademicYearInfo o) {
		return id.compareTo(o.getId());
	}
	
}
