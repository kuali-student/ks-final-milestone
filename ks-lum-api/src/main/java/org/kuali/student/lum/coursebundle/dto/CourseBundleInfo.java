/*
 * Copyright 2013 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.coursebundle.dto;

import org.kuali.student.lum.coursebundle.infc.CourseBundle;

import org.kuali.student.r2.common.dto.IdEntityInfo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseBundleInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "courseBundleCode", "subjectAreaOrgId", "courseBundleCodeSuffix",
        "adminOrgId", "courseIds",
        "meta", "attributes", "_futureElements" })

public class CourseBundleInfo
    extends IdEntityInfo 
    implements CourseBundle {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseBundleCode;

    @XmlElement
    private String subjectAreaOrgId;

    @XmlElement
    private String courseBundleCodeSuffix;

    @XmlElement
    private String adminOrgId;

    @XmlAnyElement
    private List<String> courseIds;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new CourseBundleInfo.
     */
    public CourseBundleInfo() {
    }

    /**
     * Constructs a new CourseBundleInfo from another
     * CourseBundle.
     *
     * @param bundle the CourseBundle to copy
     */
    public CourseBundleInfo(CourseBundle bundle) {
        super(bundle);

        if (bundle != null) {
            this.courseBundleCode = bundle.getCourseBundleCode();
            this.subjectAreaOrgId = bundle.getSubjectAreaOrgId();
            this.courseBundleCodeSuffix = bundle.getCourseBundleCodeSuffix();
            this.adminOrgId = bundle.getAdminOrgId();

            if (bundle.getCourseIds() != null) {
                this.courseIds = new ArrayList<String>(bundle.getCourseIds());
            }
        }
    }

    
    @Override
    public String getCourseBundleCode() {
        return (this.courseBundleCode);
    }

    public void setCourseBundleCode(String courseBundleCode) {
        this.courseBundleCode = courseBundleCode;
    }

    @Override
    public String getSubjectAreaOrgId() {
        return (this.subjectAreaOrgId);
    }

    public void setSubjectAreaOrgId(String subjectAreaOrgId) {
        this.subjectAreaOrgId = subjectAreaOrgId;
    }

    @Override
    public String getCourseBundleCodeSuffix() {
        return (this.courseBundleCodeSuffix);
    }

    public void setCourseBundleCodeSuffix(String courseBundleCodeSuffix) {
        this.courseBundleCodeSuffix = courseBundleCodeSuffix;
    }

    @Override
    public String getAdminOrgId() {
        return (this.adminOrgId);
    }

    public void setAdminOrgId(String adminOrgId) {
        this.adminOrgId = adminOrgId;
    }

    @Override
    public List<String> getCourseIds() {
        if (this.courseIds == null) {
            this.courseIds = new ArrayList<String>(0);
        }

        return (this.courseIds);
    }

    public void setCourseIds(List<String> courseIds) {
        this.courseIds = courseIds;
    }
}
