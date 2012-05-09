package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

public class ActivityOfferingFormObject {

    private ActivityOfferingInfo dto;
    private FormatOfferingInfo formatOffering;
    private TermInfo term;

    public ActivityOfferingFormObject(){
        dto = new ActivityOfferingInfo();
        formatOffering = new FormatOfferingInfo();
        term = new TermInfo();
    }

    public ActivityOfferingFormObject(ActivityOfferingInfo info){
        super();
        dto = info;
    }

    public ActivityOfferingInfo getDto() {
        return dto;
    }

    public void setDto(ActivityOfferingInfo dto) {
        this.dto = dto;
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

}
