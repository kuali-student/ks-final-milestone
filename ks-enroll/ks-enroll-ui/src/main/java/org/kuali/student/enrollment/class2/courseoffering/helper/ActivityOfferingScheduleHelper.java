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
 * Created by venkat on 11/30/12
 */
package org.kuali.student.enrollment.class2.courseoffering.helper;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;

/**
 * This class defines methods to load and save schedules in Activity Offering schedule logic
 *
 * @author Kuali Student Team
 */
public interface ActivityOfferingScheduleHelper {

    public void loadSchedules(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo);

    public void saveSchedules(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo);

    public List<String> getEndTimes(String days,String startTime,String termTypeKey) throws Exception;

}
