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

package org.kuali.student.r2.core.scheduling.dao;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.scheduling.model.TimeSlotEntity;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
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
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<TimeSlotEntity> query = builder.createQuery(TimeSlotEntity.class);
        Root<TimeSlotEntity> root = query.from(TimeSlotEntity.class);
        query.select(root);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get("timeSlotType"), timeSlotType));

        if (StringUtils.isNotBlank(weekdays)) {
            predicateList.add(builder.equal(root.get("weekdays"), weekdays));
        } else {
            predicateList.add(builder.isNull(root.get("weekdays")));
        }

        if (startTime != null){
            predicateList.add(builder.equal(root.get("startTimeMillis"), startTime));
        } else {
            predicateList.add(builder.isNull(root.get("startTimeMillis")));
        }

        if (endTime != null){
            predicateList.add(builder.equal(root.get("endTimeMillis"), endTime));
        } else {
            predicateList.add(builder.isNull(root.get("endTimeMillis")));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);

        query.where(predicates);

        return em.createQuery(query).getResultList();
    }

    public String getCurrentMaxTimeSlotCode(){
        Query query = em.createNamedQuery("TimeSlotEntity.getCurrentMaxTimeSlotCode");

        String maxCode = (String) query.getSingleResult();

        return StringUtils.defaultString(maxCode);
    }

}
