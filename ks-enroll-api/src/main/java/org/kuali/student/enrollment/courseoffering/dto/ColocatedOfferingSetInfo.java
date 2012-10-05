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
 * Created by mahtabme (Mezba Mahtab) on 10/4/12
 */
package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.ColocatedOfferingSet;
import org.kuali.student.enrollment.lui.dto.LuiSetInfo;

import java.util.List;

/**
 * This class represents a colocated set of activity offerings
 *
 * @author Kuali Student Team
 */
public class ColocatedOfferingSetInfo extends LuiSetInfo implements ColocatedOfferingSet {

    //////////////////////////
    // Constants
    //////////////////////////

    private static final long serialVersionUID = 1L;

    ////////////////////////
    // Data Variables
    ////////////////////////

    private Boolean isMaxEnrollmentShared = null;

    //////////////////////////
    // Constructors
    //////////////////////////

    public ColocatedOfferingSetInfo() {

    }

    public ColocatedOfferingSetInfo(ColocatedOfferingSet colocatedOfferingSet) {
        super (colocatedOfferingSet);
        if (colocatedOfferingSet!=null) {
            this.isMaxEnrollmentShared = new Boolean(colocatedOfferingSet.getIsMaxEnrollmentShared());
        }
    }

    ////////////////////////
    // Getters and Setters
    ////////////////////////

    @Override
    public Boolean getIsMaxEnrollmentShared() {
        return isMaxEnrollmentShared;
    }

    public void setIsMaxEnrollmentShared(Boolean maxEnrollmentShared) {
        isMaxEnrollmentShared = maxEnrollmentShared;
    }

    ////////////////////////
    // Implementing Methods
    ////////////////////////

    @Override
    public List<String> getOfferingIds() {
        return getLuiIds();
    }

    public void setOfferingIds(List<String> offeringIds) {
        setLuiIds(offeringIds);
    }

}
