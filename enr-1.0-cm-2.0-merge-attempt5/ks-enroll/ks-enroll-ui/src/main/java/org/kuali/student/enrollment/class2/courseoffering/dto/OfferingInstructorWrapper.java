/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;

import java.io.Serializable;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class OfferingInstructorWrapper implements Serializable {
    private OfferingInstructorInfo offeringInstructorInfo;
    private String sEffort;
    private String typeName;

    public OfferingInstructorWrapper(){
        offeringInstructorInfo = new OfferingInstructorInfo();
    }

    public OfferingInstructorInfo getOfferingInstructorInfo() {
        return offeringInstructorInfo;
    }

    public void setOfferingInstructorInfo(OfferingInstructorInfo offeringInstructorInfo) {
        this.offeringInstructorInfo = offeringInstructorInfo;
    }

    public OfferingInstructorWrapper(OfferingInstructorInfo offeringInstructorInfo){
        super();
        this.offeringInstructorInfo =  offeringInstructorInfo;
    }

    public String getsEffort() {
        return sEffort;
    }

    public void setsEffort(String sEffort) {
        this.sEffort = sEffort;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
