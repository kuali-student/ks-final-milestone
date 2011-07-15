/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.grading.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.grading.infc.GradeRoster;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GradeRosterInfo", propOrder = {"gradeRosterEntryIds", "graderIds", "activityOfferingIds",
        "courseOfferingId", "id", "typeKey", "stateKey", "name", "descr", "meta", "attributes", "_futureElements"})
public class GradeRosterInfo extends IdEntityInfo implements GradeRoster, Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> gradeRosterEntryIds;

    private List<String> graderIds;

    private List<String> activityOfferingIds;

    private String courseOfferingId;

    @XmlAnyElement
    private List<Element> _futureElements;

    @Override
    public List<String> getGraderIds() {
        return this.getGraderIds();
    }

    public void setGraderIds(List<String> graderIds) {
        this.graderIds = graderIds;
    }

    public void setGradeRosterEntryIds(List<String> graderRosterEntryIds) {
        this.gradeRosterEntryIds = graderRosterEntryIds;
    }

    @Override
    public List<String> getGradeRosterEntryIds() {
        return gradeRosterEntryIds;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setActivityOfferingIds(List<String> activityOfferings) {
        this.activityOfferingIds = activityOfferings;
    }

    public List<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

}
