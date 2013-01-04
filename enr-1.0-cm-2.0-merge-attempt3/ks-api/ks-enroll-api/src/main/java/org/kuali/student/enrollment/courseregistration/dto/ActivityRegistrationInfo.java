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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseregistration.infc.ActivityRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityRegistrationInfo", propOrder = {
                "id", "typeKey", "stateKey", 
                "studentId", "activityOfferingId",
                "effectiveDate", "expirationDate", "meta", "attributes", 
                "_futureElements"})

public class ActivityRegistrationInfo 
    extends RelationshipInfo 
    implements ActivityRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String studentId;

    @XmlElement
    private String activityOfferingId;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new ActivityRegistrationInfo.
     */
    public ActivityRegistrationInfo() {
    }

    /**
     * Constructs a new ActivityRegistrationInfo from another
     * ActivityRegistration.
     *
     * @param activityRegistration the ActivityRegistration to copy
     */

    public ActivityRegistrationInfo(ActivityRegistration activityRegistration) {
        super(activityRegistration);

        if (activityRegistration != null) {
            this.studentId = activityRegistration.getStudentId();
            this.activityOfferingId = activityRegistration.getActivityOfferingId();
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
    public String getActivityOfferingId() {
        return activityOfferingId;
    }

    public void setActivityOfferingId(String activityOfferingId) {
        this.activityOfferingId = activityOfferingId;
    }
}
