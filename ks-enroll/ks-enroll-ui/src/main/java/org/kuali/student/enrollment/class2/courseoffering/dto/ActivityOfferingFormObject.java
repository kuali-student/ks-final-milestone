package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;

public class ActivityOfferingFormObject implements Serializable{

    private ActivityOfferingInfo aoInfo;
    private FormatOfferingInfo formatOffering;
    private TermInfo term;

    private boolean waitListIndicator;
    private AttributeInfo waitListAttribute;

    public ActivityOfferingFormObject(){
        aoInfo = new ActivityOfferingInfo();
        //FIXME: As we dont have Lui types in DB and in mock, just assigning term state for now to get the ui working...
        aoInfo.setStateKey(AcademicCalendarServiceConstants.TERM_DRAFT_STATE_KEY);
        aoInfo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
//        aoInfo.setActivityId("CLU-4");
//        aoInfo.setName("Activity Offering");
        formatOffering = new FormatOfferingInfo();
        term = new TermInfo();
    }

    public ActivityOfferingFormObject(ActivityOfferingInfo info){
        super();
        aoInfo = info;
        for(AttributeInfo attribute : aoInfo.getAttributes()){
            if (StringUtils.equals(attribute.getKey(), CourseOfferingServiceConstants.WAIT_LIST_INDICATOR_ATTR)){
                waitListIndicator = BooleanUtils.toBoolean(attribute.getKey());
                waitListAttribute = attribute;
                break;
            }
        }
    }

    public void prepareForSave(){
        if (waitListAttribute == null){
            waitListAttribute = new AttributeInfo();
            waitListAttribute.setKey(CourseOfferingServiceConstants.WAIT_LIST_INDICATOR_ATTR);
            aoInfo.getAttributes().add(waitListAttribute);
        }
        waitListAttribute.setValue(BooleanUtils.toStringTrueFalse(waitListIndicator));

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

    public boolean isWaitListIndicator() {
        return waitListIndicator;
    }

    public void setWaitListIndicator(boolean waitListIndicator) {
        this.waitListIndicator = waitListIndicator;
    }

}
