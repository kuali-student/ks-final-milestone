package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.enrollment.registration.course.infc.RegResponseItem;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.w3c.dom.Element;

public class RegResponseItemInfo extends HasAttributesAndMetaInfo implements RegResponseItem, Serializable {

    private static final long serialVersionUID = 1L;

    private String courseRegistrationId;

    private String courseWaitlistEntryId;

    private OperationStatusInfo operationStatus;

    private List<Element> _futureElements;

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
        this.operationStatus=operationStatus;
    }

}
