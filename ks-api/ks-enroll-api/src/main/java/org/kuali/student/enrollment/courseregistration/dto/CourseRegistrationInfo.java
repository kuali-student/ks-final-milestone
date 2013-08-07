/*
 * Copyright 2011 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseregistration.infc.CourseRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseRegistrationInfo", propOrder = {
        "id", "typeKey", "stateKey", 
        "studentId", "courseOfferingId", "credits", "gradingOptionId", 
        "effectiveDate", "expirationDate", "meta", "attributes", 
        "_futureElements"})

public class CourseRegistrationInfo 
    extends RelationshipInfo 
    implements CourseRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String studentId;

    @XmlElement
    private String courseOfferingId;

    @XmlElement
    private String credits;

    @XmlElement
    private String gradingOptionId;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new CourseRegistrationInfo.
     */
    public CourseRegistrationInfo() {
    }

    /**
     * Constructs a new CourseRegistrationInfo from another
     * CourseRegistration.
     *
     * @param courseRegistration the CourseRegistration to copy
     */
    public CourseRegistrationInfo(CourseRegistration courseRegistration) {
        super(courseRegistration);

        if (courseRegistration != null) {
            this.studentId = courseRegistration.getStudentId();
            this.courseOfferingId = courseRegistration.getCourseOfferingId();
            this.credits = courseRegistration.getCredits();
            this.gradingOptionId = courseRegistration.getGradingOptionId();
         }
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    @Override
    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    @Override
    public String getGradingOptionId() {
        return gradingOptionId;
    }

    public void setGradingOptionId(String gradingOptionId) {
        this.gradingOptionId = gradingOptionId;
    }
}
