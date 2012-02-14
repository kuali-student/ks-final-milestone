package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseregistration.infc.RegResponseItem;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegResponseItemInfo", propOrder = {"regRequestItemId","courseRegistrationId", "courseWaitlistEntryId", "operationStatus",
        "_futureElements"})
public class RegResponseItemInfo implements RegResponseItem, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String regRequestItemId;
   
    @XmlElement
    private String courseRegistrationId;
    @XmlElement
    private String courseWaitlistEntryId;
    
    @XmlElement
    private OperationStatusInfo operationStatus;
    @XmlAnyElement
    private List<Element> _futureElements;

    public RegResponseItemInfo() {
        super();
        this.courseRegistrationId = null;
        this.courseWaitlistEntryId = null;
        this.operationStatus = null;
        this._futureElements = null;

    }

    public RegResponseItemInfo(RegResponseItem regResponseItem) {

        if (null != regResponseItem) {
            this.courseRegistrationId = regResponseItem.getCourseRegistrationId();
            this.courseWaitlistEntryId = regResponseItem.getCourseWaitlistEntryId();
            if (regResponseItem.getOperationStatus() != null) {
                this.operationStatus = new OperationStatusInfo(regResponseItem.getOperationStatus());
            }
            this._futureElements = null;
        }
    }
    @Override
    public String getRegRequestItemId() {
        return regRequestItemId;
    }

    public void setRegRequestItemId(String regRequestItemId) {
        this.regRequestItemId = regRequestItemId;
    }
    @Override
    public String getCourseRegistrationId() {
        return courseRegistrationId;
    }
 
    @Override
    public String getCourseWaitlistEntryId() {
        return courseWaitlistEntryId;
    }

    public void setCourseRegistrationId(String courseRegistrationId) {
        this.courseRegistrationId = courseRegistrationId;
    }

    public void setCourseWaitlistEntryId(String courseWaitlistEntryId) {
        this.courseWaitlistEntryId = courseWaitlistEntryId;
    }

    @Override
    public OperationStatusInfo getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatusInfo operationStatus) {
        this.operationStatus = operationStatus;
    }

}
