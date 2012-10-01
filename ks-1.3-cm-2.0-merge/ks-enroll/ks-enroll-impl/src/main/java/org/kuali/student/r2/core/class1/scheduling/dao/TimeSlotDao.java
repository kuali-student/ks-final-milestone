/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.class1.scheduling.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.scheduling.model.TimeSlotEntity;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: andy
 * Date: 6/5/12
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeSlotDao extends GenericEntityDao<TimeSlotEntity> {

    public List<TimeSlotEntity> getByTimeSlotType(String timeSlotType) {
        Query query = em.createNamedQuery("TimeSlotEntity.GetByTimeSlotType");
        query.setParameter("timeSlotType", timeSlotType);

        return query.getResultList();
    }

    public List<TimeSlotEntity> getByTimeSlotTypeWeekdaysAndStartTime(String timeSlotType, String weekdays, Long startTime) {
        Query query = em.createNamedQuery("TimeSlotEntity.GetByTimeSlotTypeDaysAndStartTime");
        query.setParameter("timeSlotType", timeSlotType);
        query.setParameter("weekdays", weekdays);
        query.setParameter("startTimeMillis", startTime);

        return query.getResultList();
    }

    public List<TimeSlotEntity> getByTimeSlotTypeWeekdaysStartTimeAndEndTime(String timeSlotType, String weekdays, Long startTime, Long endTime) {
        Query query = em.createNamedQuery("TimeSlotEntity.GetByTimeSlotTypeDaysStartTimeAndEndTime");
        query.setParameter("timeSlotType", timeSlotType);
        query.setParameter("weekdays", weekdays);
        query.setParameter("startTimeMillis", startTime);
        query.setParameter("endTimeMillis", endTime);

        return query.getResultList();
    }
}
