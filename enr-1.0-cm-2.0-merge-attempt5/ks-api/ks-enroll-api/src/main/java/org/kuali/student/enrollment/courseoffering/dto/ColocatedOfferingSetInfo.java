/**
 * Copyright 2012 The Kuali Foundation 
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
 *
 * Created by mahtabme (Mezba Mahtab) on 10/4/12
 */
package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.ColocatedOfferingSet;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents a colocated set of activity offerings.
 *
 * @author Kuali Student Team
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ColocatedOfferingSetInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "isMaxEnrollmentShared", "activityOfferingIds", 
        "effectiveDate", "expirationDate", "maximumEnrollment",
        "meta", "attributes", "_futureElements"})

public class ColocatedOfferingSetInfo 
    extends IdEntityInfo implements ColocatedOfferingSet {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private Boolean isMaxEnrollmentShared = null;

    @XmlElement
    private List<String> activityOfferingIds;

    @XmlElement
    private Integer maximumEnrollment;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     *  Constructs a new CoLocatedOfferingSetInfo.
     */
    public ColocatedOfferingSetInfo() {
    }

    /**
     *  Constructs a new CoLocatedOfferingSetInfo from another
     *  CoLocatedOfferingSet.
     * 
     *  @param colocatedOfferingSet the CoLocatedOfferingSet to copy
     */
    public ColocatedOfferingSetInfo(ColocatedOfferingSet colocatedOfferingSet) {
        super(colocatedOfferingSet);
        if (colocatedOfferingSet!=null) {
            this.isMaxEnrollmentShared = new Boolean(colocatedOfferingSet.getIsMaxEnrollmentShared());
            this.activityOfferingIds = new ArrayList<String>(colocatedOfferingSet.getActivityOfferingIds());
            this.maximumEnrollment = colocatedOfferingSet.getMaximumEnrollment();

            this.effectiveDate = (null != colocatedOfferingSet.getEffectiveDate()) ? new Date(colocatedOfferingSet.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != colocatedOfferingSet.getExpirationDate()) ? new Date(colocatedOfferingSet.getExpirationDate().getTime()) : null;
        }
    }

    @Override
    public Boolean getIsMaxEnrollmentShared() {
        return isMaxEnrollmentShared;
    }

    public void setIsMaxEnrollmentShared(Boolean maxEnrollmentShared) {
        isMaxEnrollmentShared = maxEnrollmentShared;
    }

    @Override
    public List<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

    public void setActivityOfferingIds(List<String> activityOferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    @Deprecated
    public List<String> getOfferingIds() {
        return getActivityOfferingIds();
    }

    @Deprecated
    public void setOfferingIds(List<String> activityOferingIds) {
        setActivityOfferingIds(activityOfferingIds);
    }

    @Override
    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
