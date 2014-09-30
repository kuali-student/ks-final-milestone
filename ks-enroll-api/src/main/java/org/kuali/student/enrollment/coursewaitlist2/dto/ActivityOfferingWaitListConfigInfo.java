/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by Charles on 9/24/2014
 */
package org.kuali.student.enrollment.coursewaitlist2.dto;

import org.kuali.student.enrollment.coursewaitlist2.infc.ActivityOfferingWaitListConfig;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Impl for ActivityOfferingWaitListConfig interface
 *
 * @author Kuali Student Team
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseWaitListInfo", propOrder = {
        "id", "typeKey", "stateKey","activityOfferingIds",
        "formatOfferingIds", "maxSize",
        "effectiveDate", "expirationDate",
        "meta", "attributes", "_futureElements"})
public class ActivityOfferingWaitListConfigInfo extends IdNamelessEntityInfo implements ActivityOfferingWaitListConfig,
        Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> activityOfferingIds;
    @XmlElement
    private List<String> formatOfferingIds;
    @XmlElement
    private Integer maxSize;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlAnyElement
    private List<Object> _futureElements;

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    public void setFormatOfferingIds(List<String> formatOfferingIds) {
        this.formatOfferingIds = formatOfferingIds;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public List<String> getFormatOfferingIds() {
        return formatOfferingIds;
    }

    @Override
    public List<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

    @Override
    public Integer getMaxSize() {
        return maxSize;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }
}
