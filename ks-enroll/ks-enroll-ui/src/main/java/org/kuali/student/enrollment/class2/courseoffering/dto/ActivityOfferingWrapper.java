package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;

public class ActivityOfferingWrapper implements Serializable{

    private ActivityOfferingInfo aoInfo;
    private FormatOfferingInfo formatOffering;
    private TermInfo term;
    private boolean readOnlyView;
    private boolean isChecked;

    public ActivityOfferingWrapper(){
        aoInfo = new ActivityOfferingInfo();
        aoInfo.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        aoInfo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        formatOffering = new FormatOfferingInfo();
        term = new TermInfo();
        this.setReadOnlyView(false);
        this.setIsChecked(false);
    }

    public ActivityOfferingWrapper(ActivityOfferingInfo info){
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

    public boolean getReadOnlyView() {
        return readOnlyView;
    }

    public void setReadOnlyView(boolean readOnlyView) {
        this.readOnlyView = readOnlyView;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        this.isChecked = checked;
    }

}
