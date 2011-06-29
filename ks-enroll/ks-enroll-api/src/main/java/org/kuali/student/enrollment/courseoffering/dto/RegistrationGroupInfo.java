/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.courseoffering.dto;

import java.util.List;

import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.TimeAmount;

/**
 * @author Kuali Student Team (Kamal)
 */
public class RegistrationGroupInfo extends IdEntityInfo implements RegistrationGroup {

    private static final long serialVersionUID = 1L;

    @Override
    public Integer getMaximumEnrollment() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getMinimumEnrollment() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getFormatId() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getRegistrationCode() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public Boolean hasWaitlist() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getWaitlistTypeKey() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getWaitlistMaximum() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean getIsWaitlistCheckinRequired() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TimeAmount getWaitlistCheckFrequency() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getActivityOfferingIds() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getCourseOfferingId() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
