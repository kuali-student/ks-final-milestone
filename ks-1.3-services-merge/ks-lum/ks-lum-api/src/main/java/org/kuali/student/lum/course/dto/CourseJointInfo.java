/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.lum.course.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Information about course joints.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue May 18 11:31:06 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/courseJointInfo+Structure">CourseJointInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseJointInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseNumberSuffix;

    @XmlElement
    private String courseTitle;

    @XmlElement
    private String subjectArea;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String courseId;

    @XmlAttribute
    private String relationId;

    /**
     * 
     */
    public String getCourseNumberSuffix() {
        return courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

    /**
     * Abbreviated name of the Course
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * The Study Subject Area is used to identify the area of study associated with the credit course. It may be a general study area (e.g. Chemistry) or very specific (e.g. Naval Architecture).
     */
    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    /**
     * Unique identifier for a learning unit type. Once set at create time, this field may not be updated.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Unique identifier for a Course.
     */
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Unique identifier for a Course Joints.
     */
    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
}