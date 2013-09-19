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

import java.io.Serializable;

/**
 *
 *  successor is ManualWaitListType
 */
public class FromUIWaitListType implements WaitListTypeWrapper, Serializable {

    private String waitListType;
    private String waitListTypeForUI ;
    private WaitListTypeWrapper successor = new ManualWaitListType();

    @Override
    public void waitListTypeFinder(ActivityOfferingWrapper aoWrapper) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void autoProcessedConfReqFinder(CourseWaitListInfo cowlInfo, String waitListType) {
        successor.autoProcessedConfReqFinder(cowlInfo,waitListType);
    }

    public String getWaitListTypeForUI () {
        return waitListTypeForUI ;
    }

    public void setWaitListTypeForUI(String waitListTypeForUI ) {
        this.waitListTypeForUI  = waitListTypeForUI ;
    }

    public String getWaitListType() {
        return waitListType;
    }

    public void setWaitListType(String waitListType) {
        this.waitListType = waitListType;
    }
}
