package org.kuali.student.r2.core.scheduling.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.scheduling.model.ScheduleEntity;
import org.kuali.student.r2.core.scheduling.model.ScheduleRequestEntity;

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

}
