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
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.form;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */

public interface ActivityOfferingDisplayUI {

    public String getCourseOfferingId();

    public List<ActivityOfferingWrapper> getActivityWrapperList();

    public void setActivityWrapperList(List<ActivityOfferingWrapper> activityWrapperList);

    public List<ActivityOfferingClusterWrapper> getClusterResultList();

    public void setClusterResultList(List<ActivityOfferingClusterWrapper> clusterResultList);

    public List<RegistrationGroupWrapper> getRgResultList();

    public void setRgResultList(List<RegistrationGroupWrapper> rgResultList) ;

    public Map<String,FormatOfferingInfo> getFoId2aoTypeMap();

    public void setFoId2aoTypeMap(Map<String, FormatOfferingInfo> foId2aoTypeMap);

    public boolean isHasMoreThanOneCluster();

    public void setHasMoreThanOneCluster(boolean hasMoreThanOneCluster);
}
