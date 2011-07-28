package org.kuali.student.enrollment.class2.acal.dto;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;

public class AcademicCalendarWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	AcademicCalendarInfo academicCalendarInfo;
	List<TermWrapper> termWrapperList;
	
	public AcademicCalendarWrapper (){
		termWrapperList = new ArrayList<TermWrapper>();
	}
	/**
	 * @return the academicCalendarInfo
	 */
	public AcademicCalendarInfo getAcademicCalendarInfo() {
		return academicCalendarInfo;
	}
	/**
	 * @param academicCalendarInfo the academicCalendarInfo to set
	 */
	public void setAcademicCalendarInfo(AcademicCalendarInfo academicCalendarInfo) {
		this.academicCalendarInfo = academicCalendarInfo;
	}
	/**
	 * @return the termWrapperList
	 */
	public List<TermWrapper> getTermWrapperList() {
		return termWrapperList;
	}
	/**
	 * @param termWrapperList the termWrapperList to set
	 */
	public void setTermWrapperList(List<TermWrapper> termWrapperList) {
		this.termWrapperList = termWrapperList;
	}
	
}
