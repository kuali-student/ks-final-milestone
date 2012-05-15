package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;

public class EnrollmentFeeFormObject implements Serializable{

    private ActivityOfferingInfo aoInfo;
    private FormatOfferingInfo formatOffering;
    private TermInfo term;

    public EnrollmentFeeFormObject(){
        aoInfo = new ActivityOfferingInfo();
        //FIXME: As we dont have Lui types in DB and in mock, just assigning term state for now to get the ui working...
        aoInfo.setStateKey(AcademicCalendarServiceConstants.TERM_DRAFT_STATE_KEY);
        aoInfo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
//        aoInfo.setActivityId("CLU-4");
//        aoInfo.setName("Activity Offering");
        formatOffering = new FormatOfferingInfo();
        term = new TermInfo();
    }

    public EnrollmentFeeFormObject(ActivityOfferingInfo info){
        super();
        aoInfo = info;
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

    public ActivityOfferingInfo getAoInfo() {
        return aoInfo;
    }

    public void setAoInfo(ActivityOfferingInfo aoInfo) {
        this.aoInfo = aoInfo;
    }

}
