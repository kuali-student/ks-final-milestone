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
 * Created by Charles on 10/29/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dao;

import org.kuali.student.enrollment.class2.courseoffering.model.ActivityOfferingClusterEntity;
import org.kuali.student.r2.common.dao.EntityDao;

import java.util.List;

/**
 * This makes it easier to provide an ActivityOfferingClusterDao mock should the need arise.
 *
 * @author Kuali Student Team
 */
public interface ActivityOfferingClusterDaoApi extends EntityDao<String, ActivityOfferingClusterEntity>  {
    /**
     * Fetch all ActivityOfferingClusterEntity objects associated with the formatOfferingId
     * @param formatOfferingId ID for a format offering
     * @return List of ActivityOfferingClusterEntity objects
     */
    List<ActivityOfferingClusterEntity> getByFormatOffering(String formatOfferingId);

    /**
     * Fetches all AO Ids within the cluster
     * @param activityOfferingClusterId The AO cluster ID to use
     * @return List of AO Ids (as strings)
     */
    List<String> getActivityOfferingIdsByClusterId(String activityOfferingClusterId);
}
