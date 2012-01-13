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

import org.kuali.student.r2.common.dto.EntityInfo;
import org.kuali.student.r2.lum.course.infc.CourseJoint;
import org.w3c.dom.Element;

@XmlType(name = "CourseJointInfo", propOrder = {"typeKey", "stateKey", "descr", "courseNumberSuffix", "courseTitle", "subjectArea", "courseId", "relationId", "meta", "attributes",
        "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseJointInfo extends EntityInfo implements CourseJoint, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseNumberSuffix;

    @XmlElement
    private String courseTitle;

    @XmlElement
    private String subjectArea;

    @XmlAttribute
    private String courseId;

    @XmlAttribute
    private String relationId;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseJointInfo() {

    }

    public CourseJointInfo(CourseJoint courseJoint) {
        super(courseJoint);
        if (courseJoint != null) {
            this.courseNumberSuffix = courseJoint.getCourseNumberSuffix();
            this.courseTitle = courseJoint.getCourseTitle();
            this.subjectArea = courseJoint.getSubjectArea();
            this.courseId = courseJoint.getCourseId();
            this.relationId = courseJoint.getRelationId();
        }

    }

    @Override
    public String getCourseNumberSuffix() {
        return courseNumberSuffix;
    }

    public void setCourseNumberSuffix(String courseNumberSuffix) {
        this.courseNumberSuffix = courseNumberSuffix;
    }

    @Override
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @Override
    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    @Override
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
}