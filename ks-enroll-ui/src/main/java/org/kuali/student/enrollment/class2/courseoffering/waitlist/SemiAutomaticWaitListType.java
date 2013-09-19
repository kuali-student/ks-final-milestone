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
 *  semi-automatic -> automaticallyProcessed = true, confirmationRequired = true
 *   successor ->  automatic
 *
 */
public class SemiAutomaticWaitListType  implements WaitListTypeWrapper, Serializable {

    public String waitListTypeForUI  = "Semi-Automatic";
    public String waitListType = LuiServiceConstants.SEMIAUTOMATIC_WAITLIST_TYPE_KEY;
    private WaitListTypeWrapper successor = new AutomaticWaitListType();

    @Override
    public void waitListTypeFinder(ActivityOfferingWrapper aoWrapper) {

        if((aoWrapper.getCourseWaitListInfo().getAutomaticallyProcessed()) && (aoWrapper.getCourseWaitListInfo().getConfirmationRequired())){
            aoWrapper.setWaitListTypeWrapper(this);
        }
        else {
            successor.waitListTypeFinder(aoWrapper);
        }
    }

    @Override
    public void autoProcessedConfReqFinder(CourseWaitListInfo cowlInfo, String waitListType) {
        if(getWaitListType().equals(waitListType)) {
            cowlInfo.setConfirmationRequired(true);
            cowlInfo.setAutomaticallyProcessed(true);
        }
        else{
            successor.autoProcessedConfReqFinder(cowlInfo, waitListType);
        }
    }

    public String getWaitListType() {
        return waitListType;
    }

    public void setWaitListType(String waitListType) {
        this.waitListType = waitListType;
    }

    public String getWaitListTypeForUI() {
        return waitListTypeForUI;
    }

    public void setWaitListTypeForUI(String waitListTypeForUI) {
        this.waitListTypeForUI = waitListTypeForUI;
    }
}
