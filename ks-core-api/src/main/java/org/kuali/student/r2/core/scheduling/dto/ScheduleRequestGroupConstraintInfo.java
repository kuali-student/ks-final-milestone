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
 * Created by mahtabme on 11/5/13
 */
package org.kuali.student.r2.core.scheduling.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestGroupConstraint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a ScheduleRequestGroupConstraint.
 *
 * @author Kuali Student Team
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleRequestGroupConstraintInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "isAllRequired", "requiredScheduleRequestIds", "isContiguous", "isSameRoomRequired", "scheduleRequestIds",
        "meta", "attributes", "_futureElements" })
public class ScheduleRequestGroupConstraintInfo extends IdEntityInfo implements ScheduleRequestGroupConstraint, Serializable {

    ///////////////////
    // Data Variables
    ///////////////////

    @XmlElement
    private Boolean isAllRequired = false;

    @XmlElement
    private List<String> requiredScheduleRequestIds;

    @XmlElement
    private Boolean isContiguous = false;

    @XmlElement
    private Boolean isSameRoomRequired = false;

    @XmlElement
    private List<String> scheduleRequestIds;

    @XmlAnyElement
    private List<Object> _futureElements;

    ////////////////////
    // Constructors
    ////////////////////

    public ScheduleRequestGroupConstraintInfo () {
    }

    public ScheduleRequestGroupConstraintInfo(ScheduleRequestGroupConstraint scheduleRequestGroupConstraint) {
        super (scheduleRequestGroupConstraint);
        if (null != scheduleRequestGroupConstraint) {
            this.isAllRequired = scheduleRequestGroupConstraint.getIsAllRequired();
            this.isContiguous = scheduleRequestGroupConstraint.getIsContiguous();
            this.isSameRoomRequired = scheduleRequestGroupConstraint.getIsSameRoomRequired();
            this.requiredScheduleRequestIds = new ArrayList<String>();
            for(String requiredId : scheduleRequestGroupConstraint.getRequiredScheduleRequestIds()) {
                this.requiredScheduleRequestIds.add(requiredId);
            }
            this.scheduleRequestIds = new ArrayList<String>();
            for(String schedReqId : scheduleRequestGroupConstraint.getScheduleRequestIds()) {
                this.scheduleRequestIds.add(schedReqId);
            }
        }
    }

    ///////////////////
    // Functionals
    ///////////////////

    @Override
    public Boolean getIsAllRequired() {
        return isAllRequired;
    }

    public void setIsAllRequired(Boolean allRequired) {
        isAllRequired = allRequired;
    }

    @Override
    public List<String> getRequiredScheduleRequestIds() {
        return requiredScheduleRequestIds;
    }

    public void setRequiredScheduleRequestIds(List<String> requiredScheduleRequestIds) {
        this.requiredScheduleRequestIds = requiredScheduleRequestIds;
    }

    @Override
    public Boolean getIsContiguous() {
        return isContiguous;
    }

    public void setIsContiguous(Boolean contiguous) {
        isContiguous = contiguous;
    }

    @Override
    public Boolean getIsSameRoomRequired() {
        return isSameRoomRequired;
    }

    public void setIsSameRoomRequired(Boolean sameRoomRequired) {
        isSameRoomRequired = sameRoomRequired;
    }

    @Override
    public List<String> getScheduleRequestIds() {
        return scheduleRequestIds;
    }

    public void setScheduleRequestIds(List<String> scheduleRequestIds) {
        this.scheduleRequestIds = scheduleRequestIds;
    }
}
