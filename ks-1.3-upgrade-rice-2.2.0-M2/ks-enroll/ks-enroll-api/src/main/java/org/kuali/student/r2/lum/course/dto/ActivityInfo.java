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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.lum.course.infc.Activity;
import org.w3c.dom.Element;

@XmlType(name = "ActivityInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "duration", "unitsContentOwner", "defaultEnrollmentEstimate", "contactHours", "meta", "attributes",
        "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ActivityInfo extends IdEntityInfo implements Activity, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private TimeAmountInfo duration;

    @XmlElement
    private List<String> unitsContentOwner;

    @XmlElement
    private Integer defaultEnrollmentEstimate;

    @XmlElement
    private AmountInfo contactHours;

    @XmlAnyElement
    private List<Element> _futureElements;

    public ActivityInfo() {}

    public ActivityInfo(Activity activity) {
        super(activity);
        if (activity != null) {
            this.duration = new TimeAmountInfo(activity.getDuration());
            this.unitsContentOwner = new ArrayList<String>(activity.getUnitsContentOwner());
            this.defaultEnrollmentEstimate = activity.getDefaultEnrollmentEstimate();
            this.contactHours = new AmountInfo(activity.getContactHours());
        }
    }

    @Override
    public TimeAmountInfo getDuration() {
        return duration;
    }

    public void setDuration(TimeAmountInfo duration) {
        this.duration = duration;
    }

    @Override
    public List<String> getUnitsContentOwner() {
        if (unitsContentOwner == null) {
            unitsContentOwner = new ArrayList<String>(0);
        }
        return unitsContentOwner;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    @Override
    public Integer getDefaultEnrollmentEstimate() {
        return defaultEnrollmentEstimate;
    }

    public void setDefaultEnrollmentEstimate(Integer defaultEnrollmentEstimate) {
        this.defaultEnrollmentEstimate = defaultEnrollmentEstimate;
    }

    public void setContactHours(AmountInfo contactHours) {
        this.contactHours = contactHours;
    }

    @Override
    public AmountInfo getContactHours() {
        return contactHours;
    }

}