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
 * Created by Eswaran on 9/18/13
 */
package org.kuali.student.enrollment.class2.courseoffering.waitlist;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;

/**
 *  @author Kuali Student Team
 *
 *  automatic -> automaticallyProcessed = true, confirmationRequired = false
 *  no successor, automatic is last in chain (automatic is default)
 */
public class AutomaticWaitListType implements WaitListTypeWrapper, Serializable {

    public String waitListTypeForUI  = "Automatic";
    public String waitListType = LuiServiceConstants.AUTOMATIC_WAITLIST_TYPE_KEY;

    @Override
    public void waitListTypeFinder(ActivityOfferingWrapper aoWrapper) {
        if((aoWrapper.getCourseWaitListInfo().getAutomaticallyProcessed()) && !(aoWrapper.getCourseWaitListInfo().getConfirmationRequired())){
            aoWrapper.setWaitListTypeWrapper(this);
        }
        else {  // automatic is default.
            aoWrapper.setWaitListTypeWrapper(this);
        }
    }

    @Override
    public void autoProcessedConfReqFinder(CourseWaitListInfo cowlInfo , String waitListType) {
        if(getWaitListType().equals(waitListType)) {
            cowlInfo.setConfirmationRequired(false);
            cowlInfo.setAutomaticallyProcessed(true);
        }
        else{ // the default value is automatic.
            cowlInfo.setConfirmationRequired(false);
            cowlInfo.setAutomaticallyProcessed(true);
        }
    }

    public String getWaitListType() {
        return waitListType;
    }

    public void setWaitListType(String waitListType) {
        this.waitListType = waitListType;
    }

    public String getWaitListTypeForUI() {
        return waitListTypeForUI ;
    }

    public void setWaitListTypeForUI(String waitListTypeForUI) {
        this.waitListTypeForUI  = waitListTypeForUI;
    }
}
