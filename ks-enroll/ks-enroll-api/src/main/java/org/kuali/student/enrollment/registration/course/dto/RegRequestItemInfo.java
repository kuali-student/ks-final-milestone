package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.registration.course.infc.RegRequestItem;
import org.kuali.student.enrollment.registration.course.infc.RegResponseItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegRequestItemInfo", propOrder = {"id", "typeKey", "stateKey", "newRegGroupId", "existingRegGroupId",
        "okToWaitlist", "okToHoldList", "okToExceptionList", "gradingOption", "creditOption", "regResponseItem",
        "meta", "attributes", "_futureElements"})
public class RegRequestItemInfo extends IdEntityInfo implements RegRequestItem, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String newRegGroupId;

    @XmlElement
    private String existingRegGroupId;

    @XmlElement
    private Boolean okToWaitlist;

    @XmlElement
    private Boolean okToHoldList;

    @XmlElement
    private String gradingOption;

    @XmlElement
    private String creditOption;

    @XmlAnyElement
    private List<Element> _futureElements;

    public RegRequestItemInfo() {
        super();
        this.newRegGroupId = null;
        this.existingRegGroupId = null;
        this.okToWaitlist = null;
        this.okToHoldList = null;
        this.gradingOption = null;
        this.creditOption = null;
        this._futureElements = null;

    }

    public RegRequestItemInfo(RegRequestItem regRequestItem) {
        super(regRequestItem);

        if (null != regRequestItem) {
            this.newRegGroupId = regRequestItem.getNewRegGroupId();
            this.existingRegGroupId = regRequestItem.getExistingRegGroupId();
            this.okToWaitlist = regRequestItem.getOkToWaitlist();
            this.okToHoldList = regRequestItem.getOkToHoldList();
            this.gradingOption = regRequestItem.getGradingOption();
            this.creditOption = regRequestItem.getCreditOption();
            this._futureElements = null;
        }
    }

    public void setNewRegGroupId(String newRegGroupId) {
        this.newRegGroupId = newRegGroupId;
    }

    public void setExistingRegGroupId(String existingRegGroupId) {
        this.existingRegGroupId = existingRegGroupId;
    }

    public void setOkToWaitlist(Boolean okToWaitlist) {
        this.okToWaitlist = okToWaitlist;
    }

    public void setOkToHoldList(Boolean okToHoldList) {
        this.okToHoldList = okToHoldList;
    }

    public void setGradingOption(String gradingOption) {
        this.gradingOption = gradingOption;
    }

    public void setCreditOption(String creditOption) {
        this.creditOption = creditOption;
    }

    @Override
    public String getNewRegGroupId() {

        return newRegGroupId;
    }

    @Override
    public String getExistingRegGroupId() {

        return existingRegGroupId;
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
    public String getGradingOption() {

        return gradingOption;
    }

    @Override
    public String getCreditOption() {

        return creditOption;
    }

}
