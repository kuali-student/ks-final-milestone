package org.kuali.student.r2.core.scheduling.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.scheduling.model.ScheduleEntity;

import javax.persistence.Query;
import java.util.List;

/**
 * DAO class for ScheduleEntity
 *
 * @author andrewlubbers
 */
public class ScheduleDao extends GenericEntityDao<ScheduleEntity> {

    public List<ScheduleEntity> getSchedulesByType(String scheduleTypeKey){
        return (List<ScheduleEntity>)em.createNamedQuery("Schedule.getSchedulesByType").setParameter("typeKey", scheduleTypeKey).getResultList();
    }

    public Boolean isExistsTimeSlot(String timeSlotid) {

        Query query = em.createQuery("select ts from ScheduleEntity sr, IN(sr.scheduleComponents) sc, IN(sc.timeSlotIds) ts where ts = :tsId");
        query.setParameter("tsId", timeSlotid);
        query.setMaxResults(1);
        List results = query.getResultList();
        if ((results == null) || results.isEmpty()) {
            return Boolean.FALSE;
        }
        else {
            return Boolean.TRUE;
        }
    }
}
