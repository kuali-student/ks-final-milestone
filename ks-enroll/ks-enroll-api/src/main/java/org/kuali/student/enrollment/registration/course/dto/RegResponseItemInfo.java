package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.enrollment.registration.course.infc.RegResponseItem;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.w3c.dom.Element;

public class RegResponseItemInfo extends HasAttributesAndMetaInfo implements RegResponseItem, Serializable {

    private static final long serialVersionUID = 1L;

    private String courseRegistrationId;

    private String courseWaitlistEntryId;

    private List<String> itemRegMessages;

    private String itemRegStatus;

    private List<String> itemRegWarnings;

    private List<String> itemRegErrors;

    private List<Element> _futureElements;

    @Override
    public List<String> getItemRegMessages() {
        return itemRegMessages;
    }

    @Override
    public String getItemRegStatus() {
        return itemRegStatus;
    }

    @Override
    public List<String> getItemRegWarnings() {
        return itemRegWarnings;
    }

    @Override
    public List<String> getItemRegErrors() {
        return itemRegErrors;
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

    public void setItemRegMessages(List<String> itemRegMessages) {
        this.itemRegMessages = itemRegMessages;
    }

    public void setItemRegStatus(String itemRegStatus) {
        this.itemRegStatus = itemRegStatus;
    }

    public void setItemRegWarnings(List<String> itemRegWarnings) {
        this.itemRegWarnings = itemRegWarnings;
    }

    public void setItemRegErrors(List<String> itemRegErrors) {
        this.itemRegErrors = itemRegErrors;
    }

    public void setCourseWaitlistEntryId(String courseWaitlistEntryId) {
        this.courseWaitlistEntryId = courseWaitlistEntryId;
    }

}
