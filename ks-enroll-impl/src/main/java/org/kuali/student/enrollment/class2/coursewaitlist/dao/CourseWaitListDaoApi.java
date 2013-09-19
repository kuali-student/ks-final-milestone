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
 * Created by Eswaran on 9/17/13
 */
package org.kuali.student.enrollment.class2.coursewaitlist.dao;

import org.kuali.student.enrollment.class2.coursewaitlist.model.CourseWaitListEntity;
import org.kuali.student.r2.common.dao.EntityDao;

import java.util.List;

/**
 * This makes it easier to provide an CourseWaitListDao mock should the need arise.
 *
 * @author Kuali Student Team
 */
public interface CourseWaitListDaoApi extends EntityDao<String, CourseWaitListEntity>  {

    /**
     * Fetches all CWLEntity within the AO
     * @param activityOfferingIds The AO ID to use
     * @return List of CWLEntity
     */

    public List<CourseWaitListEntity> getCourseWaitListsByActivityOfferingIds(List<String> activityOfferingIds);

}
