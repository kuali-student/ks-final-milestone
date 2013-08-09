/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by mahtabme (Mezba Mahtab) on 10/12/12
 */
package org.kuali.student.r2.lum.course.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.lum.course.infc.CourseOfferingCrossListing;

/**
 * This class represents information of a Course Offering Cross Listing.
 *
 * @author Kuali Student Team
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseCrossListingInfo", propOrder = {"id", "code", "subjectArea", "department", "courseNumberSuffix", "meta", "attributes" , "_futureElements" }) 
public class CourseOfferingCrossListingInfo extends HasAttributesAndMetaInfo implements CourseOfferingCrossListing, Serializable {

    ///////////////////////////////////
    // Constants
    ///////////////////////////////////

    private static final long serialVersionUID = 1L;

    ///////////////////////////
    // Data Variables
    ///////////////////////////

    @XmlElement
    private String code;

    @XmlElement
    private String subjectArea;

    @XmlElement
    private String department;

    @XmlElement
    private String courseNumberSuffix;

    @XmlAttribute
    private String id;

    @XmlAttribute
    private List<Object>_futureElements;
    
    /////////////////////////////////
    // Constructors
    /////////////////////////////////

    public CourseOfferingCrossListingInfo() {

    }

    public CourseOfferingCrossListingInfo (CourseOfferingCrossListing courseOfferingCrossListing) {
        super(courseOfferingCrossListing);
        if (courseOfferingCrossListing != null) {
            this.code = courseOfferingCrossListing.getCode();
            this.subjectArea = courseOfferingCrossListing.getSubjectArea();
            this.department = courseOfferingCrossListing.getDepartment();
            this.courseNumberSuffix = courseOfferingCrossListing.getCourseNumberSuffix();
            this.id = courseOfferingCrossListing.getId();
        }
    }

    ////////////////////////////
    // Getters and Setters
    ////////////////////////////

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

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
