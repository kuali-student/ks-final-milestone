/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.course.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.lum.course.infc.CourseCrossListing;

/**
 * This is a description of what this class does - sambit don't forget to fill
 * this in.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */

@XmlType(name = "CourseCrossListingInfo", propOrder = {"id", "typeKey", "stateKey", "code", "subjectArea", "department", "courseNumberSuffix", "meta", "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseCrossListingInfo extends IdNamelessEntityInfo implements CourseCrossListing, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String code;

    @XmlElement
    private String subjectArea;

    @XmlElement
    private String department;

    @XmlElement
    private String courseNumberSuffix;

    public CourseCrossListingInfo() {

    }

    public CourseCrossListingInfo(CourseCrossListing courseCrossListing) {
        super(courseCrossListing);
        if (courseCrossListing != null) {
            this.code = courseCrossListing.getCode();
            this.subjectArea = courseCrossListing.getSubjectArea();
            this.department = courseCrossListing.getDepartment();
            this.courseNumberSuffix = courseCrossListing.getCourseNumberSuffix();
        }
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String getCourseNumberSuffix() {
        return courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

}