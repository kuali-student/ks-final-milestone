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
import org.kuali.student.r2.core.scheduling.infc.ScheduleTransactionGroup;

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
@XmlType(name = "ScheduleTransactionGroupInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "isAllRequired", "requiredScheduleTransactionIds", "isContiguous", "isSameRoomRequired",
        "meta", "attributes", "_futureElements" })
public class ScheduleTransactionGroupInfo extends IdEntityInfo implements ScheduleTransactionGroup, Serializable {

    ///////////////////
    // Data Variables
    ///////////////////

    @XmlElement
    private Boolean isAllRequired = false;

    @XmlElement
    private List<String> requiredScheduleTransactionIds;

    @XmlElement
    private Boolean isContiguous = false;

    @XmlElement
    private Boolean isSameRoomRequired = false;

    @XmlAnyElement
    private List<Object> _futureElements;

    ////////////////////
    // Constructors
    ////////////////////

    public ScheduleTransactionGroupInfo () {
    }

    public ScheduleTransactionGroupInfo(ScheduleTransactionGroup scheduleTransactionGroup) {
        super (scheduleTransactionGroup);
        if (null != scheduleTransactionGroup) {
            this.isAllRequired = scheduleTransactionGroup.getIsAllRequired();
            this.isContiguous = scheduleTransactionGroup.getIsContiguous();
            this.isSameRoomRequired = scheduleTransactionGroup.getIsSameRoomRequired();
            this.requiredScheduleTransactionIds = new ArrayList<String>();
            for(String requiredId : scheduleTransactionGroup.getRequiredScheduleTransactionIds()) {
                this.requiredScheduleTransactionIds.add(requiredId);
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
    public List<String> getRequiredScheduleTransactionIds() {
        return requiredScheduleTransactionIds;
    }

    public void setRequiredScheduleTransactionIds(List<String> requiredScheduleTransactionIds) {
        this.requiredScheduleTransactionIds = requiredScheduleTransactionIds;
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
}
