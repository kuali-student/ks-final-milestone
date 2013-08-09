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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingCluster;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingSet;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (Kamal)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingClusterInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr",
                "privateName", "formatOfferingId", "activityOfferingSets",
                "meta", "attributes", "_futureElements"})

public class ActivityOfferingClusterInfo
    extends IdEntityInfo 
    implements ActivityOfferingCluster {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String privateName;

    @XmlElement
    private String formatOfferingId;

    @XmlElement
    private List<ActivityOfferingSetInfo> activityOfferingSets;

    @XmlAnyElement
    private List<Element> _futureElements;

    
    /**
     * Constructs a new ActivityOfferingClusterInfo.
     */
    public ActivityOfferingClusterInfo() {
    }

    /**
     * Constructs a new ActivityOfferingClusterInfo from another
     * ActivityOfferingCluster.
     *
     * @param template the registration group template to copy
     */
    public ActivityOfferingClusterInfo(ActivityOfferingCluster template) {
        super(template); 
        
        if (template == null) {
            return;      
        }

        this.formatOfferingId = template.getFormatOfferingId();
        if (template.getActivityOfferingSets() != null) {
            this.activityOfferingSets = new ArrayList<ActivityOfferingSetInfo>(template.getActivityOfferingSets().size());
            for (ActivityOfferingSet aotemplate : template.getActivityOfferingSets()) {
                this.activityOfferingSets.add(new ActivityOfferingSetInfo(aotemplate));
            }
        }
    }

    @Override
    public String getPrivateName() {
        return this.privateName;
    }

    public void setPrivateName(String privateName) {
        this.privateName = privateName;
    }

    @Override
    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    @Override
    public List<ActivityOfferingSetInfo> getActivityOfferingSets() {
        if (activityOfferingSets == null) {
            activityOfferingSets = new ArrayList<ActivityOfferingSetInfo>();
        }

        return activityOfferingSets;
    }

    public void setActivityOfferingSets(List<ActivityOfferingSetInfo> activityOfferingSets) {
        this.activityOfferingSets = activityOfferingSets;
    }
}
