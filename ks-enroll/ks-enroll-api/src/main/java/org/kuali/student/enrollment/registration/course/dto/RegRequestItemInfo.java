package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.enrollment.registration.course.infc.RegRequestItem;
import org.kuali.student.enrollment.registration.course.infc.RegResponseItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;

public class RegRequestItemInfo extends IdEntityInfo implements RegRequestItem, Serializable {

    private static final long serialVersionUID = 1L;

    private String regGroupId;

    private Boolean okToWaitlist;

    private Boolean okToHoldList;

    private Boolean okToExceptionList;

    private String gradingOption;

    private String creditOption;

    private RegResponseItem regResponseItem;

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public void setOkToWaitlist(Boolean okToWaitlist) {
        this.okToWaitlist = okToWaitlist;
    }

    public void setOkToHoldList(Boolean okToHoldList) {
        this.okToHoldList = okToHoldList;
    }

    public void setOkToExceptionList(Boolean okToExceptionList) {
        this.okToExceptionList = okToExceptionList;
    }

    public void setGradingOption(String gradingOption) {
        this.gradingOption = gradingOption;
    }

    public void setCreditOption(String creditOption) {
        this.creditOption = creditOption;
    }

    public void setRegResponseItem(RegResponseItem regResponseItem) {
        this.regResponseItem = regResponseItem;
    }

    @Override
    public String getRegGroupId() {

        return regGroupId;
    }

    @Override
    public Boolean getOkToWaitlist() {

        return okToWaitlist;
    }

    @Override
    public Boolean getOkToHoldList() {

        return okToHoldList;
    }

    @Override
    public Boolean getOkToExceptionList() {

        return okToExceptionList;
    }

    @Override
    public RegResponseItem getRegResponseItem() {

        return regResponseItem;
    }

    @Override
    public String getGradingOption() {

        return gradingOption;
    }

    @Override
    public String getCreditOption() {

        return creditOption;
    }

}
