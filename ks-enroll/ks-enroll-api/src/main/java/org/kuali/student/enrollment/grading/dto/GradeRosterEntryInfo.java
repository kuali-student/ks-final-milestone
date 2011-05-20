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
package org.kuali.student.enrollment.grading.dto;

import java.io.Serializable;
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
 * 
 * @author Kuali Student Team (Kamal)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GradeRosterEntryInfo", propOrder = {"id", "studentId", "activityOfferingId", "gradeRosterId", "assignedGrade", "calculatedGrade", "creditsEarned", "meta", "attributes", "_futureElements"})
public class GradeRosterEntryInfo  extends HasAttributesAndMetaInfo implements GradeRosterEntry, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlAttribute
    private String id;
    
    @XmlElement    
    private String studentId;

    @XmlElement    
    private String activityOfferingId;
    
    @XmlElement    
    private String gradeRosterId;
    
    @XmlElement    
    private AssignedGradeInfo assignedGrade;
    
    @XmlElement    
    private CalculatedGradeInfo calculatedGrade;
    
    @XmlElement
    private CreditsEarnedInfo creditsEarned;
        
    @XmlAnyElement
    private List<Element> _futureElements;  
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getActivityOfferingId() {
        return activityOfferingId;
    }

    public void setActivityOfferingId(String activityOfferingId) {
        this.activityOfferingId = activityOfferingId;
    }

    public String getGradeRosterId() {
        return gradeRosterId;
    }

    public void setGradeRosterId(String gradeRosterId) {
        this.gradeRosterId = gradeRosterId;
    }

    public AssignedGradeInfo getAssignedGrade() {
        return assignedGrade;
    }

    public void setAssignedGrade(AssignedGradeInfo assignedGrade) {
        this.assignedGrade = assignedGrade;
    }

    public CalculatedGradeInfo getCalculatedGrade() {
        return calculatedGrade;
    }

    public void setCalculatedGrade(CalculatedGradeInfo calculatedGrade) {
        this.calculatedGrade = calculatedGrade;
    }

    public CreditsEarnedInfo getCreditsEarned() {
        return creditsEarned;
    }

    public void setCreditsEarned(CreditsEarnedInfo creditsEarned) {
        this.creditsEarned = creditsEarned;
    }
}
