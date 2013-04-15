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

import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingSet;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingSetInfo", propOrder = {
                "id", "activityOfferingType", "activityOfferingIds", 
                "_futureElements"})

public class ActivityOfferingSetInfo
    implements ActivityOfferingSet, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String id;

    @XmlElement
    private String activityOfferingType;

    @XmlElement
    private List<String> activityOfferingIds;

    @XmlAnyElement
    private List<Element> _futureElements;

    
    /**
     * Constructs a new ActivityOfferingSetInfo.
     */
    public ActivityOfferingSetInfo() {
    }


    /**
     * Constructs a new ActivityOfferingSetInfo from another
     * ActivityOfferingSet.
     *
     * @param activityOfferingSet the registration group activityOfferingSet to copy
     */
    public ActivityOfferingSetInfo(ActivityOfferingSet activityOfferingSet) {
        this.id = activityOfferingSet.getId();

        if (activityOfferingSet == null) {
            return;      
        }

        this.activityOfferingType = activityOfferingSet.getActivityOfferingType();

        if (activityOfferingSet.getActivityOfferingIds() != null) {
            this.activityOfferingIds = new ArrayList<String>(activityOfferingSet.getActivityOfferingIds());
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getActivityOfferingType() {
        return this.activityOfferingType;
    }

    public void setActivityOfferingType(String activityOfferingType) {
        this.activityOfferingType = activityOfferingType;
    }

    @Override
    public List<String> getActivityOfferingIds() {
        if (activityOfferingIds == null) {
            activityOfferingIds = new ArrayList<String>();
        }

        return activityOfferingIds;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }
}
