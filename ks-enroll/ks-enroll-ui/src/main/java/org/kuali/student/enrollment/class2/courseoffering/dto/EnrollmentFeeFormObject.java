package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.util.constants.FeeServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;

import java.io.Serializable;

public class EnrollmentFeeFormObject implements Serializable{

    private EnrollmentFeeInfo efInfo;

    public EnrollmentFeeFormObject(){
        efInfo = new EnrollmentFeeInfo();
        efInfo.setStateKey(FeeServiceConstants.FEE_ACTIVE_STATE_KEY);
        efInfo.setTypeKey(FeeServiceConstants.FEE_ENROLLMENT_TYPE_KEY);

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

}
