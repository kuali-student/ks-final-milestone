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

import org.kuali.student.core.dto.Idable;

/**
 * Detailed information about the human readable form of a Variation
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Fri May 07 11:40:19 EDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/courseVariationInfo+Structure">CourseVariationInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseVariationInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String variationTitle;

    @XmlElement
    private String subjectArea;

    @XmlElement
    private String courseNumberSuffix;

    @XmlElement
    private String variationCode;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String id;

    /**
     * Full name of the variation, commonly used on catalogues
     */
    public String getVariationTitle() {
        return variationTitle;
    }

    public void setVariationTitle(String variationTitle) {
        this.variationTitle = variationTitle;
    }

    /**
     * 
     */
    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    /**
     * The "extra" portion of the code, which usually corresponds with the most detailed part of the number.
     */
    public String getCourseNumberSuffix() {
        return courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

    /**
     * A number that indicates the sequence or order of variation in cases where several different variations have the same offical Identifier
     */
    public String getVariationCode() {
        return variationCode;
    }

    public void setVariationCode(String variationCode) {
        this.variationCode = variationCode;
    }

    /**
     * This is the CluIdentifier Type. It can only have a single value for VariationType
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Identifies the particular identifier structure. This is set by the service to be able to determine changes and alterations to the structure as well as provides a handle for searches. This structure is not accessible through unique operations, and it is strongly recommended that no external references to this particular identifier be maintained.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}