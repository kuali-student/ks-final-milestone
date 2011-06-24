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

import java.util.Date;
import java.util.List;

import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.dto.IdEntityInfo;

/**
 *
 * @author Kuali Student Team (Kamal)
 *
 */
public class CourseOfferingInfo extends IdEntityInfo implements CourseOffering {

    private static final long serialVersionUID = 1L;

    @Override
    public Date getEffectiveDate() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Date getExpirationDate() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Term getTerm() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getCourseId() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getSubjectArea() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getGradingOptions() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getUnitsDeployment() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getUnitsContentOwner() {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean getFinalExamStatus() {
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

}
