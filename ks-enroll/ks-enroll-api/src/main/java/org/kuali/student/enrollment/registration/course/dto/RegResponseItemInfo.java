package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.enrollment.registration.course.infc.RegResponseItem;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.w3c.dom.Element;

public class RegResponseItemInfo extends HasAttributesAndMetaInfo implements RegResponseItem, Serializable {

    private static final long serialVersionUID = 1L;
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
        super(regResponseItem);
        if (null != regResponseItem) {
            this.courseRegistrationId = regResponseItem.getCourseRegistrationId();
            this.courseWaitlistEntryId = regResponseItem.getCourseWaitlistEntryId();
            this.operationStatus = regResponseItem.getOperationStatusInfo();
            this._futureElements = null;
        }
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
    public OperationStatusInfo getOperationStatusInfo() {
        return operationStatus;
    }

    public void setOperationStatusInfo(OperationStatusInfo operationStatus) {
        this.operationStatus = operationStatus;
    }

}
