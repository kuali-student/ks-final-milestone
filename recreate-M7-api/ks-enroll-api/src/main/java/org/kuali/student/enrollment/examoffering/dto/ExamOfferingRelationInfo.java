/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Mezba Mahtab (mezba.mahtab@utoronto.ca) on 2/8/13
 */
package org.kuali.student.enrollment.examoffering.dto;

import org.kuali.student.enrollment.examoffering.infc.ExamOfferingRelation;
import org.kuali.student.r2.common.dto.RelationshipInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a relationship between a format offering and an
 * exam offering record.
 *
 * @author Mezba Mahtab (mezba.mahtab@utoronto.ca)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExamOfferingRelationInfo", propOrder = {"id", "typeKey", "stateKey", "name",
        "descr", "meta", "attributes",
        "formatOfferingId", "examOfferingId", "activityOfferingIds", "populationIds",
        "_futureElements"})
public class ExamOfferingRelationInfo extends RelationshipInfo implements ExamOfferingRelation, Serializable {

    ///////////////////////////
    // CONSTANTS
    ///////////////////////////

    private static final long serialVersionUID = 1L;

    ////////////////////////
    // DATA VARIABLES
    ////////////////////////

    @XmlElement
    private String formatOfferingId;

    @XmlElement
    private String examOfferingId;

    @XmlElement
    private List<String> activityOfferingIds;

    @XmlElement
    private List<String> populationIds;

    @XmlAnyElement
    private List<Object> _futureElements;

    /////////////////////////
    // CONSTRUCTOR
    /////////////////////////

    public ExamOfferingRelationInfo() {}

    public ExamOfferingRelationInfo(ExamOfferingRelation examOfferingRelation) {
        super(examOfferingRelation);
        if (null != examOfferingRelation) {
            this.formatOfferingId = examOfferingRelation.getFormatOfferingId();
            this.examOfferingId = examOfferingRelation.getExamOfferingId();
            if (examOfferingRelation.getActivityOfferingIds() != null) {
                this.activityOfferingIds = new ArrayList<String>(examOfferingRelation.getActivityOfferingIds());
            }
            if (examOfferingRelation.getPopulationIds() != null) {
                this.populationIds = new ArrayList<String>(examOfferingRelation.getPopulationIds());
            }
        }
    }

    ////////////////////////////////
    // GETTERS AND SETTERS
    ////////////////////////////////

    @Override
    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    @Override
    public String getExamOfferingId() {
        return examOfferingId;
    }

    public void setExamOfferingId(String examOfferingId) {
        this.examOfferingId = examOfferingId;
    }

    @Override
    public List<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    @Override
    public List<String> getPopulationIds() {
        return populationIds;
    }

    public void setPopulationIds(List<String> populationIds) {
        this.populationIds = populationIds;
    }

    public List<Object> get_futureElements() {
        return _futureElements;
    }

    public void set_futureElements(List<Object> _futureElements) {
        this._futureElements = _futureElements;
    }
}
