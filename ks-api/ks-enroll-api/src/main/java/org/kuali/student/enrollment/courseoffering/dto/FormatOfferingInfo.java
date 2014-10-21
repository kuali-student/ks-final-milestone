/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.courseoffering.infc.FormatOffering;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormatOfferingInfo", propOrder = {
         "id", "typeKey", "stateKey", "name", "descr",
         "courseOfferingId", "formatId", "termId", "shortName",
         "activityOfferingTypeKeys", "gradeRosterLevelTypeKey", 
         "finalExamLevelTypeKey",
         "meta", "attributes", "_futureElements"})

public class FormatOfferingInfo 
    extends IdEntityInfo 
    implements FormatOffering, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseOfferingId;

    @XmlElement
    private String formatId;

    @XmlElement
    private String termId;

    @XmlElement
    private String shortName;

    @XmlElement
    private List<String> activityOfferingTypeKeys;

    @XmlElement
    private String gradeRosterLevelTypeKey;

    @XmlElement
    private String finalExamLevelTypeKey;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new FormatOffering.
     */
    public FormatOfferingInfo() {
    }

    /**
     * Constructs a new FormatOfferingInfo from another
     * FormatOffering.
     *
     * @param offering the activity offering to copy
     */
    public FormatOfferingInfo(FormatOffering offering) {
        super(offering);

        if (offering == null) {
            return;
        }

        this.courseOfferingId = offering.getCourseOfferingId();
        this.formatId = offering.getFormatId();
        this.termId = offering.getTermId();
        this.shortName = offering.getShortName();

        if (offering.getActivityOfferingTypeKeys() != null) {
            this.activityOfferingTypeKeys = new ArrayList<String>(offering.getActivityOfferingTypeKeys());
        }

        this.gradeRosterLevelTypeKey = offering.getGradeRosterLevelTypeKey();
        this.finalExamLevelTypeKey = offering.getFinalExamLevelTypeKey ();
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    @Override
    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    @Override
    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public List<String> getActivityOfferingTypeKeys() {
        if (activityOfferingTypeKeys == null) {
            activityOfferingTypeKeys = new ArrayList<String>();
        }

        return activityOfferingTypeKeys;
    }

    public void setActivityOfferingTypeKeys(List<String> activityOfferingTypeKeys) {
        this.activityOfferingTypeKeys = activityOfferingTypeKeys;
    }

    @Override
    public String getGradeRosterLevelTypeKey() {
        return this.gradeRosterLevelTypeKey;
    }

    public void setGradeRosterLevelTypeKey(String gradeRosterLevelTypeKey) {
        this.gradeRosterLevelTypeKey = gradeRosterLevelTypeKey;
    }

    @Override
    public String getFinalExamLevelTypeKey() {
        return this.finalExamLevelTypeKey;
    }

    public void setFinalExamLevelTypeKey(String finalExamLevelTypeKey) {
        this.finalExamLevelTypeKey = finalExamLevelTypeKey;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FormatOfferingInfo [courseOfferingId=");
        builder.append(courseOfferingId);
        builder.append(", formatId=");
        builder.append(formatId);
        builder.append(", termId=");
        builder.append(termId);
        builder.append("]");
        return builder.toString();
    }
}
