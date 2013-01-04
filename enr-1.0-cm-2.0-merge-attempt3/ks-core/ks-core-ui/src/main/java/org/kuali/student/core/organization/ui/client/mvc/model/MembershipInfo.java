/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
