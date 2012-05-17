package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;

import java.io.Serializable;

public class EnrollmentFeeFormObject implements Serializable{

    private EnrollmentFeeInfo efInfo;
   // private FormatOfferingInfo formatOffering; Tanveer
   // private TermInfo term; Tanveer

    public EnrollmentFeeFormObject(){
        efInfo = new EnrollmentFeeInfo();
        //FIXME: As we dont have Lui types in DB and in mock, just assigning term state for now to get the ui working...
        efInfo.setStateKey(AcademicCalendarServiceConstants.TERM_DRAFT_STATE_KEY);
        efInfo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
//        aoInfo.setActivityId("CLU-4");
//        aoInfo.setName("Activity Offering");


        // formatOffering = new FormatOfferingInfo();  Tanveer
        // term = new TermInfo(); Tanveer
    }

    public EnrollmentFeeFormObject(EnrollmentFeeInfo info){
        super();
        efInfo = info;
    }

    public EnrollmentFeeInfo getEfInfo() {
        return efInfo;
    }

    public void setEfInfo(EnrollmentFeeInfo efInfo) {
        this.efInfo = efInfo;
    }

    /*  Tanveer Start comments
 public EnrollmentFeeInfo getDescr() {
      return descr;
 }

public FormatOfferingInfo getFormatOffering() {
     return formatOffering;
 }

 public void setFormatOffering(FormatOfferingInfo formatOffering) {
     this.formatOffering = formatOffering;
 }

 public TermInfo getTerm() {
     return term;
 }

 public void setTerm(TermInfo term) {
     this.term = term;
 }

 Tanveer End comments */
}
