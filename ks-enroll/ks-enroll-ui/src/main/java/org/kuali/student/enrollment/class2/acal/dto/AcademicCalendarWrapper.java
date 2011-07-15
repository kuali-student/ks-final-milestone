package org.kuali.student.enrollment.class2.acal.dto;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;

public class AcademicCalendarWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	AcademicCalendarInfo academicCalendarInfo;
	List<TermInfo> termInfoList;
	
	public AcademicCalendarWrapper (){
		termInfoList = new ArrayList<TermInfo>();
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
	 * @return the termInfoList
	 */
	public List<TermInfo> getTermInfoList() {
		return termInfoList;
	}
	/**
	 * @param termInfoList the termInfoList to set
	 */
	public void setTermInfoList(List<TermInfo> termInfoList) {
		this.termInfoList = termInfoList;
	}	
	
}
