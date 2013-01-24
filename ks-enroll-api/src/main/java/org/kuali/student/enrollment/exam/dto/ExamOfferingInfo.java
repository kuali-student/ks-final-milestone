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
 * Created by Mezba Mahtab (mezba.mahtab@utoronto.ca) on 1/18/13
 */
package org.kuali.student.enrollment.exam.dto;

import org.kuali.student.enrollment.exam.infc.ExamOffering;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.springframework.beans.factory.annotation.Required;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * This class represents information about an ExamOffering that can be scheduled.
 *
 * @author Mezba Mahtab (mezba.mahtab@utoronto.ca)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExamOfferingInfo", propOrder = {"id", "typeKey", "stateKey", "name",
        "descr", "meta", "attributes",
        "termId", "termCode", "canonicalExamId", "formatOfferingId", "courseOfferingId",
        "scheduleId", "schedulingStateKey", "_futureElements"})
public class ExamOfferingInfo extends IdEntityInfo implements ExamOffering, Serializable {

    /////////////////////////////
    // DATA VARIABLES
    /////////////////////////////

    @XmlElement
    private String termId;

    @XmlElement
    private String termCode;

    @XmlElement
    private String canonicalExamId;

    @XmlElement
    private String formatOfferingId;

    @XmlElement
    private String courseOfferingId;

    @XmlElement
    private String scheduleId;

    @XmlElement
    private String schedulingStateKey;

    @XmlAnyElement
    private List<Element> _futureElements;

    //////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////

    /**
     * Constructs an empty ExamOffering.
     */
    public ExamOfferingInfo () { }

    /**
     * Constructs a new ExamOfferingInfo from another
     * ExamOffering.
     *
     * @param offering the exam offering to copy
     */
    public ExamOfferingInfo (ExamOffering offering) {
        super(offering);

        if (offering == null) {
            return;
        }

        this.termId = offering.getTermId();
        this.termCode= offering.getTermCode();
        this.canonicalExamId = offering.getCanonicalExamId();
        this.formatOfferingId = offering.getFormatOfferingId();
        this.courseOfferingId = offering.getCourseOfferingId();
        this.scheduleId= offering.getScheduleId();
        this.schedulingStateKey= offering.getSchedulingStateKey();
    }


    //////////////////////////////
    // GETTERS AND SETTERS
    //////////////////////////////


    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getCanonicalExamId() {
        return canonicalExamId;
    }

    public void setCanonicalExamId(String canonicalExamId) {
        this.canonicalExamId = canonicalExamId;
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getSchedulingStateKey() {
        return schedulingStateKey;
    }

    public void setSchedulingStateKey(String schedulingStateKey) {
        this.schedulingStateKey = schedulingStateKey;
    }

    public List<Element> get_futureElements() {
        return _futureElements;
    }

    public void set_futureElements(List<Element> _futureElements) {
        this._futureElements = _futureElements;
    }
}
