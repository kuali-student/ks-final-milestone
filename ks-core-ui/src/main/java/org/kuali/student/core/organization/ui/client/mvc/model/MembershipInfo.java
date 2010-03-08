package org.kuali.student.core.organization.ui.client.mvc.model;

import java.io.Serializable;

public class MembershipInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String positionTypeKey;
    private String positionName;
    private String entityNameId ;
    private String nameTypeCode;
    private String firstName ;
    private String middleName;
    private String lastName ;
    private String title ;
    private String suffix;
    
    public String getPositionTypeKey() {
        return positionTypeKey;
    }
    public void setPositionTypeKey(String positionTypeKey) {
        this.positionTypeKey = positionTypeKey;
    }
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public String getEntityNameId() {
        return entityNameId;
    }
    public void setEntityNameId(String entityNameId) {
        this.entityNameId = entityNameId;
    }
    public String getNameTypeCode() {
        return nameTypeCode;
    }
    public void setNameTypeCode(String nameTypeCode) {
        this.nameTypeCode = nameTypeCode;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
