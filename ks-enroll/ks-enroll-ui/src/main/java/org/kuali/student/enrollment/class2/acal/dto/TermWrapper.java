package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;

import java.io.Serializable;

//This code is for core slice
//Core slice class.
@Deprecated
public class TermWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String id;
    TermInfo termInfo;
    KeyDateInfo classesMeetDates;
    KeyDateInfo registrationPeriod;
    KeyDateInfo dropPeriodEndsDate;
    KeyDateInfo finalExaminationsDates;
    KeyDateInfo gradesDueDate;
    
	public TermWrapper (){
		termInfo = new TermInfo();
		classesMeetDates = new KeyDateInfo();
		registrationPeriod = new KeyDateInfo();
		dropPeriodEndsDate = new KeyDateInfo();
		finalExaminationsDates = new KeyDateInfo();
		gradesDueDate = new KeyDateInfo();
	}

    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set. It equals to termInfo.getId()
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the termInfo
	 */
	public TermInfo getTermInfo() {
		return termInfo;
	}
	/**
	 * @param termInfo the termInfo to set
	 */
	public void setTermInfo(TermInfo termInfo) {
		this.termInfo = termInfo;
        this.id = termInfo.getId();
	}
	/**
	 * @return the classesMeetDates
	 */
	public KeyDateInfo getClassesMeetDates() {
		return classesMeetDates;
	}
	/**
	 * @param classesMeetDates the classesMeetDates to set
	 */
	public void setClassesMeetDates(KeyDateInfo classesMeetDates) {
		this.classesMeetDates = classesMeetDates;
	}
	/**
	 * @return the registrationPeriod
	 */
	public KeyDateInfo getRegistrationPeriod() {
		return registrationPeriod;
	}
	/**
	 * @param registrationPeriod the registrationPeriod to set
	 */
	public void setRegistrationPeriod(KeyDateInfo registrationPeriod) {
		this.registrationPeriod = registrationPeriod;
	}
	/**
	 * @return the dropPeriodEndsDate
	 */
	public KeyDateInfo getDropPeriodEndsDate() {
		return dropPeriodEndsDate;
	}
	/**
	 * @param dropPeriodEndsDate the dropPeriodEndsDate to set
	 */
	public void setDropPeriodEndsDate(KeyDateInfo dropPeriodEndsDate) {
		this.dropPeriodEndsDate = dropPeriodEndsDate;
	}
	/**
	 * @return the finalExaminationsDates
	 */
	public KeyDateInfo getFinalExaminationsDates() {
		return finalExaminationsDates;
	}
	/**
	 * @param finalExaminationsDates the finalExaminationsDates to set
	 */
	public void setFinalExaminationsDates(KeyDateInfo finalExaminationsDates) {
		this.finalExaminationsDates = finalExaminationsDates;
	}
	/**
	 * @return the gradesDueDate
	 */
	public KeyDateInfo getGradesDueDate() {
		return gradesDueDate;
	}
	/**
	 * @param gradesDueDate the gradesDueDate to set
	 */
	public void setGradesDueDate(KeyDateInfo gradesDueDate) {
		this.gradesDueDate = gradesDueDate;
	}
    
}
