/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.lum.clu.infc.CluCredit;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluCreditInfo", propOrder = {"repeatCount", "repeatTime", "repeatUnits", "minTotalUnits", "maxTotalUnits", "instructorUnits", "minTimeToComplete", "maxTimeToComplete", "maxAllowableInactivity", "maxTimeResultsRecognized" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class CluCreditInfo implements Serializable, CluCredit {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String repeatCount;

    @XmlElement
    private TimeAmountInfo repeatTime;

    @XmlElement
    private String repeatUnits;

    @XmlElement
    private Integer minTotalUnits;

    @XmlElement
    private Integer instructorUnits;

    @XmlElement
    private Integer maxTotalUnits;

    @XmlElement
    private TimeAmountInfo minTimeToComplete;

    @XmlElement
    private TimeAmountInfo maxTimeToComplete;

    @XmlElement
    private TimeAmountInfo maxAllowableInactivity;

    @XmlElement
    private TimeAmountInfo maxTimeResultsRecognized;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public CluCreditInfo() {

    }

    public CluCreditInfo(CluCredit cluCredit) {
        if (null != cluCredit) {
            this.repeatCount = cluCredit.getRepeatCount();
            this.repeatTime = (null != cluCredit.getRepeatTime()) ? new TimeAmountInfo(cluCredit.getRepeatTime()) : null;
            this.repeatUnits = cluCredit.getRepeatUnits();
            this.minTotalUnits = cluCredit.getMinTotalUnits();
            this.instructorUnits = cluCredit.getInstructorUnits();
            this.maxTotalUnits = cluCredit.getMaxTotalUnits();
            this.minTimeToComplete = (null != cluCredit.getMinTimeToComplete()) ? new TimeAmountInfo(cluCredit.getMinTimeToComplete()) : null;
            this.maxTimeToComplete = (null != cluCredit.getMaxTimeToComplete()) ? new TimeAmountInfo(cluCredit.getMaxTimeToComplete()) : null;
            this.maxAllowableInactivity = (null != cluCredit.getMaxAllowableInactivity()) ? new TimeAmountInfo(cluCredit.getMaxAllowableInactivity()) : null;
            this.maxTimeResultsRecognized = (null != cluCredit.getMaxTimeResultsRecognized()) ? new TimeAmountInfo(cluCredit.getMaxTimeResultsRecognized()) : null;
        }
    }

    @Override
    public String getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(String repeatCount) {
        this.repeatCount = repeatCount;
    }

    @Override
    public TimeAmountInfo getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(TimeAmountInfo repeatTime) {
        this.repeatTime = repeatTime;
    }

    @Override
    public String getRepeatUnits() {
        return repeatUnits;
    }

    public void setRepeatUnits(String repeatUnits) {
        this.repeatUnits = repeatUnits;
    }

    @Override
    public Integer getMinTotalUnits() {
        return minTotalUnits;
    }

    public void setMinTotalUnits(Integer minTotalUnits) {
        this.minTotalUnits = minTotalUnits;
    }

    @Override
    public Integer getMaxTotalUnits() {
        return maxTotalUnits;
    }

    public void setMaxTotalUnits(Integer maxTotalUnits) {
        this.maxTotalUnits = maxTotalUnits;
    }

    @Override
    public Integer getInstructorUnits() {
        return instructorUnits;
    }

    public void setInstructorUnits(Integer instructorUnits) {
        this.instructorUnits = instructorUnits;
    }

    @Override
    public TimeAmountInfo getMinTimeToComplete() {
        return minTimeToComplete;
    }

    public void setMinTimeToComplete(TimeAmountInfo minTimeToComplete) {
        this.minTimeToComplete = minTimeToComplete;
    }

    @Override
    public TimeAmountInfo getMaxTimeToComplete() {
        return maxTimeToComplete;
    }

    public void setMaxTimeToComplete(TimeAmountInfo maxTimeToComplete) {
        this.maxTimeToComplete = maxTimeToComplete;
    }

    @Override
    public TimeAmountInfo getMaxAllowableInactivity() {
        return maxAllowableInactivity;
    }

    public void setMaxAllowableInactivity(TimeAmountInfo maxAllowableInactivity) {
        this.maxAllowableInactivity = maxAllowableInactivity;
    }

    @Override
    public TimeAmountInfo getMaxTimeResultsRecognized() {
        return maxTimeResultsRecognized;
    }

    public void setMaxTimeResultsRecognized(TimeAmountInfo maxTimeResultsRecognized) {
        this.maxTimeResultsRecognized = maxTimeResultsRecognized;
    }
}
