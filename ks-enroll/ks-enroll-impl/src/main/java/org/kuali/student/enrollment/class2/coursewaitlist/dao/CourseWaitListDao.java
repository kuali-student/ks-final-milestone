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
 */
package org.kuali.student.enrollment.class2.coursewaitlist.dao;

import org.kuali.student.common.util.query.QueryUtil;
import org.kuali.student.enrollment.class2.coursewaitlist.model.CourseWaitListEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

import javax.persistence.TypedQuery;
import java.util.List;

public class CourseWaitListDao extends GenericEntityDao<CourseWaitListEntity> implements CourseWaitListDaoApi {
    // what do we need besides basic CRUD provided by parent class?
    /**
     * Get list of CourseWaitlists given a course offering ID
     * @param activityOfferingIds The id of an activity offering (which course waitlists will be fetched) -- have to put it in a list
     * @return List of CourseWaitListEntity objects
     */
    public List<CourseWaitListEntity> getCourseWaitListsByActivityOfferingIds(List<String> activityOfferingIds) {

        StringBuilder queryStringRef = new StringBuilder();
        queryStringRef.append("FROM CourseWaitListEntity cwl, IN(cwl.activityOfferingIds) a WHERE ");

        String primaryKeyMemberName = "a";

        TypedQuery<CourseWaitListEntity> query = QueryUtil.buildQuery(em, maxInClauseElements, queryStringRef, null, primaryKeyMemberName, activityOfferingIds, CourseWaitListEntity.class);
        return query.getResultList();
    }

    public List<CourseWaitListEntity> getCourseWaitListsByFormatOfferingIds(List<String> formatOfferingIds) {

        StringBuilder queryStringRef = new StringBuilder();
        queryStringRef.append("FROM CourseWaitListEntity cwl, IN(cwl.formatOfferingIds) a WHERE ");

        String primaryKeyMemberName = "a";

        TypedQuery<CourseWaitListEntity> query = QueryUtil.buildQuery(em, maxInClauseElements, queryStringRef, null, primaryKeyMemberName, formatOfferingIds, CourseWaitListEntity.class);
        return query.getResultList();
    }

}
