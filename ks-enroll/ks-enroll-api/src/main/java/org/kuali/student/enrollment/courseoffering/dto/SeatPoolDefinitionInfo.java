/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.courseoffering.dto;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.courseoffering.infc.SeatPoolDefinition;
import org.kuali.student.r2.common.dto.IdEntityInfo;

/**
 * 
 * @author Kuali Student Team (Kamal)
 *
 */
public class SeatPoolDefinitionInfo extends IdEntityInfo implements SeatPoolDefinition {

    private static final long serialVersionUID = 1L;

    private String courseOfferingId;
    
    private List<String> registrationGroupIdList;
    
    private Integer maximumSeatCount;
    
    private Integer processingPriority;
    
    
    @Override
    public String getCourseOfferingId() {
        return this.courseOfferingId;
    }

    @Override
    public List<String> getRegistrationGroupIdList() {
        if(null == this.registrationGroupIdList) {
            this.registrationGroupIdList = new ArrayList<String>();
        }
        return this.registrationGroupIdList;
    }

    @Override
    public Integer getMaximumSeatCount() {
        return this.maximumSeatCount;
    }

    @Override
    public Integer getProcessingPriority() {
        return this.processingPriority;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public void setRegistrationGroupIdList(List<String> registrationGroupIdList) {
        this.registrationGroupIdList = registrationGroupIdList;
    }

    public void setMaximumSeatCount(Integer maximumSeatCount) {
        this.maximumSeatCount = maximumSeatCount;
    }

    public void setProcessingPriority(Integer processingPriority) {
        this.processingPriority = processingPriority;
    }        
}
