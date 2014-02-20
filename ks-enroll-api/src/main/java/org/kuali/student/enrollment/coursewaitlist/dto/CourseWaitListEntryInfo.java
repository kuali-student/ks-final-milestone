/**
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.coursewaitlist.dto;

import org.kuali.student.enrollment.coursewaitlist.infc.CourseWaitListEntry;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseWaitListEntryInfo", propOrder = {
        "id", "typeKey", "stateKey", "effectiveDate", "expirationDate",
        "courseWaitListId", "studentId", "registrationRequestItemId", "position", "lastCheckIn",
        "meta", "attributes", "_futureElements"})

public class CourseWaitListEntryInfo extends RelationshipInfo implements CourseWaitListEntry, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseWaitListId;
    @XmlElement
    private String studentId;
    @XmlElement
    private String registrationRequestItemId;
    @XmlElement
    private Integer position;
    @XmlElement
    private Date lastCheckIn;
    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseWaitListEntryInfo() {
    }

    public CourseWaitListEntryInfo(CourseWaitListEntry entry) {
        super(entry);

        if(entry != null) {
            setCourseWaitListId(entry.getCourseWaitListId());
            setStudentId(entry.getStudentId());
            setPosition(entry.getPosition());
            setRegistrationRequestItemId(entry.getRegistrationRequestItemId());
            if(entry.getLastCheckIn() != null) {
                setLastCheckIn(new Date(entry.getLastCheckIn().getTime()));
            }
        }
    }

    @Override
    public String getCourseWaitListId() {
        return courseWaitListId;
    }

    public void setCourseWaitListId(String courseWaitListId) {
        this.courseWaitListId = courseWaitListId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getRegistrationRequestItemId() {
        return registrationRequestItemId;
    }

    public void setRegistrationRequestItemId(String registrationRequestItemId) {
        this.registrationRequestItemId= registrationRequestItemId;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public Date getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(Date lastCheckIn) {
        this.lastCheckIn = lastCheckIn;
    }
}
