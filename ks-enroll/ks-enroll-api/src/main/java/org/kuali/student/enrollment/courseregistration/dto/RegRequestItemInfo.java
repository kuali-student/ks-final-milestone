package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseregistration.infc.RegRequestItem;
import org.kuali.student.r2.common.dto.EntityInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegRequestItemInfo", propOrder = {"id", "name", "descr", "typeKey", "stateKey", "studentId",
        "newRegGroupId", "existingRegGroupId", "okToWaitlist", "okToHoldList", "gradingOptionKey", "creditOptionKey",
        "meta", "attributes", "_futureElements"})
public class RegRequestItemInfo extends IdEntityInfo implements RegRequestItem, Serializable {

    private static final long serialVersionUID = 1L;
  
    @XmlElement
    private String studentId;

    @XmlElement
    private String newRegGroupId;

    @XmlElement
    private String existingRegGroupId;

    @XmlElement
    private Boolean okToWaitlist;

    @XmlElement
    private Boolean okToHoldList;

    @XmlElement
    private String gradingOptionKey;

    @XmlElement
    private String creditOptionKey;

    @XmlAnyElement
    private List<Element> _futureElements;

    public RegRequestItemInfo() {
        super();
        this.newRegGroupId = null;
        this.existingRegGroupId = null;
        this.okToWaitlist = null;
        this.okToHoldList = null;
        this.gradingOptionKey = null;
        this.creditOptionKey = null;
        this._futureElements = null;

    }

    public RegRequestItemInfo(RegRequestItem regRequestItem) {
        super(regRequestItem);

        if (null != regRequestItem) {
            this.newRegGroupId = regRequestItem.getNewRegGroupId();
            this.existingRegGroupId = regRequestItem.getExistingRegGroupId();
            this.okToWaitlist = regRequestItem.getOkToWaitlist();
            this.okToHoldList = regRequestItem.getOkToHoldList();
            this.gradingOptionKey = regRequestItem.getGradingOptionKey();
            this.creditOptionKey = regRequestItem.getCreditOptionKey();
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

    public void setGradingOptionKey(String gradingOptionKey) {
        this.gradingOptionKey = gradingOptionKey;
    }

    public void setCreditOptionKey(String creditOptionId) {
        this.creditOptionKey = creditOptionId;
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
    public String getGradingOptionKey() {

        return gradingOptionKey;
    }

    @Override
    public String getCreditOptionKey() {

        return creditOptionKey;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }
  
}
