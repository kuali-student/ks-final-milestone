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
package org.kuali.student.enrollment.courseofferingset.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseofferingset.infc.Soc;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "name",
    "descr",
    "termId",
    "subjectArea",
    "unitsContentOwnerId",
    "schedulingStateKey",
    "lastSchedulingRunStarted",
    "lastSchedulingRunCompleted",
    "publishingStarted",
    "publishingCompleted",
    "meta",
    "attributes",
    "_futureElements"})
public class SocInfo
        extends IdEntityInfo
        implements Soc {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String termId;
    
    @XmlElement
    private String subjectArea;
    
    @XmlElement
    private String unitsContentOwnerId;
    
    @XmlElement
    private String schedulingStateKey;
    
    @XmlElement
    private Date lastSchedulingRunStarted;    
    
    @XmlElement
    private Date lastSchedulingRunCompleted;  
    
    @XmlElement
    private Date publishingStarted; 
    
    @XmlElement
    private Date publishingCompleted;
    
    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     * Constructs a new CourseOffering Set object.
     */
    public SocInfo() {
    }

    /**
     * Constructs a new Set of Offered Courses from an existing Set.
     *
     * @param soc The Set of Offered Courses to copy
     */
    public SocInfo(Soc soc) {

        super(soc);

        if (soc == null) {
            return;
        }

        this.termId = soc.getTermId();
        this.subjectArea = soc.getSubjectArea();
        this.unitsContentOwnerId = soc.getUnitsContentOwnerId();
        
        this.lastSchedulingRunCompleted = soc.getLastSchedulingRunCompleted();
        this.lastSchedulingRunStarted = soc.getLastSchedulingRunStarted();
        this.publishingCompleted = soc.getPublishingCompleted();
        this.publishingStarted = soc.getPublishingStarted();
        
        this.schedulingStateKey = soc.getSchedulingStateKey();


    }

    @Override
    public String getTermId() {
        return this.termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    @Override
    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    @Override
    public String getUnitsContentOwnerId() {
        return unitsContentOwnerId;
    }

    public void setUnitsContentOwnerId(String unitsContentOwnerId) {
        this.unitsContentOwnerId = unitsContentOwnerId;
    }

    @Override
    public Date getLastSchedulingRunCompleted() {
        return lastSchedulingRunCompleted;
    }

    public void setLastSchedulingRunCompleted(Date lastSchedulingRunCompleted) {
        this.lastSchedulingRunCompleted = lastSchedulingRunCompleted;
    }

    @Override
    public Date getLastSchedulingRunStarted() {
        return lastSchedulingRunStarted;
    }

    public void setLastSchedulingRunStarted(Date lastSchedulingRunStarted) {
        this.lastSchedulingRunStarted = lastSchedulingRunStarted;
    }

    @Override
    public Date getPublishingCompleted() {
        return publishingCompleted;
    }

    public void setPublishingCompleted(Date publishingCompleted) {
        this.publishingCompleted = publishingCompleted;
    }

    @Override
    public Date getPublishingStarted() {
        return publishingStarted;
    }

    public void setPublishingStarted(Date publishingStarted) {
        this.publishingStarted = publishingStarted;
    }

	public String getSchedulingStateKey() {
		return schedulingStateKey;
	}

	public void setSchedulingStateKey(String schedulingStateKey) {
		this.schedulingStateKey = schedulingStateKey;
	}
    
    
    
}
