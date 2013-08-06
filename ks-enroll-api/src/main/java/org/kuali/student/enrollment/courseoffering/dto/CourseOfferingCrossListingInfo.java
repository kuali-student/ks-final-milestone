/*
 * Copyright 2012 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOfferingCrossListing;

import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import java.util.List;

import java.io.Serializable;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingCrossListingInfo", propOrder = {
                "id", "typeKey", "stateKey", "code", "subjectArea",
                "subjectOrgId", "courseNumberSuffix",
                "meta", "attributes", "_futureElements"
})

public class CourseOfferingCrossListingInfo 
    extends IdNamelessEntityInfo 
    implements CourseOfferingCrossListing, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String code;

    @XmlElement
    private String subjectArea;

    @XmlElement
    private String subjectOrgId;

    @XmlElement
    private String courseNumberSuffix;

    @XmlAnyElement
    private List<Element> _futureElements;

    
    /**
     *  Constructs a new CourseOfferingCrossListingInfo.
     */

    public CourseOfferingCrossListingInfo() {

    }

    /**
     *  Constructs a new CourseOfferingCrossListingInfo 
     *  from another CourseOfferingCrossListing.
     *
     *  @param crossListing the CourseOfferingCrossListing to copy
     */

    public CourseOfferingCrossListingInfo(CourseOfferingCrossListing crossListing) {
        super(crossListing);
        if (crossListing != null) {
            this.code = crossListing.getCode();
            this.subjectArea = crossListing.getSubjectArea();
            this.subjectOrgId = crossListing.getSubjectOrgId();
            this.courseNumberSuffix = crossListing.getCourseNumberSuffix();
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
    public String getDepartmentOrgId() {
        return getSubjectOrgId();
    }

    /**
     * Replaced by setSubjectOrgId (no longer refers to the admin org).
     * @param subjectOrgId
     */
    @Deprecated
    public void setDepartmentOrgId(String subjectOrgId) {
        setSubjectOrgId(subjectOrgId);
    }

    @Override
    public String getSubjectOrgId() {
        return subjectOrgId;
    }

    public void setSubjectOrgId(String subjectOrgId) {
        this.subjectOrgId = subjectOrgId;
    }

    @Override
    public String getCourseNumberSuffix() {
        return courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }
}