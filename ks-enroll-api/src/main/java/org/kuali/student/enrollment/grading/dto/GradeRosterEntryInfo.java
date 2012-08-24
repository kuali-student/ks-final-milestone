/**
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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.grading.infc.GradeRosterEntry;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GradeRosterEntryInfo", propOrder = {"id", "studentId", "validGradeGroupKeys", "activityOfferingId", "assignedGradeKey",
        "administrativeGradeKey", "calculatedGradeKey", "creditsEarnedKey", "meta", "attributes", "_futureElements"})
public class GradeRosterEntryInfo extends HasAttributesAndMetaInfo implements GradeRosterEntry, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlElement
    private String studentId;

    @XmlElement
    private String activityOfferingId;

    @XmlElement
    private String assignedGradeKey;

    @XmlElement
    private String administrativeGradeKey;

    @XmlElement
    private String calculatedGradeKey;

    @XmlElement
    private String creditsEarnedKey;
    
    @XmlElement
    private List<String> validGradeGroupKeys;

    @XmlAnyElement
    private List<Element> _futureElements;

    public GradeRosterEntryInfo() {
        super();
        this.activityOfferingId = null;
        this.studentId = null;
        this.assignedGradeKey = null;
        this.administrativeGradeKey = null;
        this.calculatedGradeKey = null;
        this.creditsEarnedKey = null;
        this.validGradeGroupKeys = new ArrayList<String>();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String getAssignedGradeKey() {
        return assignedGradeKey;
    }

    public void setAssignedGradeKey(String assignedGradeId) {
        this.assignedGradeKey = assignedGradeId;
    }

    @Override
    public String getCalculatedGradeKey() {
        return calculatedGradeKey;
    }

    public void setCalculatedGradeKey(String calculatedGradeId) {
        this.calculatedGradeKey = calculatedGradeId;
    }

    @Override
    public String getCreditsEarnedKey() {
        return creditsEarnedKey;
    }

    public void setCreditsEarnedKey(String creditsEarnedKey) {
        this.creditsEarnedKey = creditsEarnedKey;
    }

    @Override
    public String getAdministrativeGradeKey() {
        return administrativeGradeKey;
    }

    public void setAdministrativeGradeKey(String administrativeGradeKey) {
        this.administrativeGradeKey = administrativeGradeKey;
    }

    public void setValidGradeGroupKeys(List<String> validGradeGroupKeys) {
        this.validGradeGroupKeys = validGradeGroupKeys;
    }
    @Override
    public List<String> getValidGradeGroupKeys() {
        return validGradeGroupKeys;
    }
}
