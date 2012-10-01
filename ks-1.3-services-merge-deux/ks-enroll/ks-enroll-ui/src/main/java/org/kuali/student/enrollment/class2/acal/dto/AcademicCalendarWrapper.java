package org.kuali.student.enrollment.class2.acal.dto;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;

//This code is for core slice
public class AcademicCalendarWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

    String id;
	AcademicCalendarInfo academicCalendarInfo;
	List<TermWrapper> termWrapperList;

	public AcademicCalendarWrapper (){
		termWrapperList = new ArrayList<TermWrapper>();
	}
    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set. It equals to academicCalendarInfo.getId()
	 */
	public void setId(String id) {
		this.id = id;
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
        this.id = academicCalendarInfo.getId();
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

    public boolean isOfficial() {
        return StringUtils.equals(academicCalendarInfo.getStateKey(), AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY);
    }

    public boolean isNew() {
        return StringUtils.isBlank(academicCalendarInfo.getId());
    }

}
