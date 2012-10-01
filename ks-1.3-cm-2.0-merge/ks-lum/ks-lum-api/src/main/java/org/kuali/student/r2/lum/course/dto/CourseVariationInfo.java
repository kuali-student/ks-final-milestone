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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.lum.course.infc.CourseVariation;
//import org.w3c.dom.Element;

@XmlType(name = "CourseVariationInfo", propOrder = {"id", "typeKey", "stateKey", "variationTitle", "subjectArea", "courseNumberSuffix", "variationCode", "meta", "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseVariationInfo extends IdNamelessEntityInfo implements CourseVariation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String variationTitle;

    @XmlElement
    private String subjectArea;

    @XmlElement
    private String courseNumberSuffix;

    @XmlElement
    private String variationCode;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public CourseVariationInfo() {

    }

    public CourseVariationInfo(CourseVariation courseVariation) {
        super(courseVariation);

        if (courseVariation != null) {
            this.variationTitle = courseVariation.getVariationTitle();
            this.subjectArea = courseVariation.getSubjectArea();
            this.courseNumberSuffix = courseVariation.getCourseNumberSuffix();
            this.variationCode = courseVariation.getVariationCode();

        }
    }

    /**
     * Full name of the variation, commonly used on catalogues
     */
    @Override
    public String getVariationTitle() {
        return variationTitle;
    }

    public void setVariationTitle(String variationTitle) {
        this.variationTitle = variationTitle;
    }

    /**
     * 
     */
    @Override
    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number.
     */
    @Override
    public String getCourseNumberSuffix() {
        return courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

    /**
     * A number that indicates the sequence or order of variation in cases where
     * several different variations have the same offical Identifier
     */
    @Override
    public String getVariationCode() {
        return variationCode;
    }

    public void setVariationCode(String variationCode) {
        this.variationCode = variationCode;
    }

}