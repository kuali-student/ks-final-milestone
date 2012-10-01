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
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.dto.HasAttributes;
import org.kuali.student.common.dto.Idable;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Detailed information about the human readable form of a Variation
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue May 18 11:31:09 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/courseCrossListingInfo+Structure">CourseCrossListingInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseCrossListingInfo implements Serializable, Idable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String code;
    
    @XmlElement
    private String subjectArea;

    @XmlElement
    private String department;

    @XmlElement
    private String courseNumberSuffix;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;
    
    @XmlAttribute
    private String type;

    @XmlAttribute
    private String id;


    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
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