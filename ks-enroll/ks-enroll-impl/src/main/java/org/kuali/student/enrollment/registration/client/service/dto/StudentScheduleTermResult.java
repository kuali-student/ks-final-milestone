/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by vgadiyak on 1/28/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleCourseResult", propOrder = {
        "userId", "term", "registeredCourseOfferings", "waitlistCourseOfferings"})
public class StudentScheduleTermResult {

    private String userId;
    private TermSearchResult term;
    private List<StudentScheduleCourseResult> registeredCourseOfferings;
    private List<StudentScheduleCourseResult> waitlistCourseOfferings;

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public TermSearchResult getTerm() { return term; }

    public void setTerm(TermSearchResult term) { this.term = term; }

    public List<StudentScheduleCourseResult> getRegisteredCourseOfferings() {
        return registeredCourseOfferings;
    }

    public void setRegisteredCourseOfferings(List<StudentScheduleCourseResult> registeredCourseOfferings) {
        this.registeredCourseOfferings = registeredCourseOfferings;
    }

    public List<StudentScheduleCourseResult> getWaitlistCourseOfferings() {
        return waitlistCourseOfferings;
    }

    public void setWaitlistCourseOfferings(List<StudentScheduleCourseResult> waitlistCourseOfferings) {
        this.waitlistCourseOfferings = waitlistCourseOfferings;
    }
}
