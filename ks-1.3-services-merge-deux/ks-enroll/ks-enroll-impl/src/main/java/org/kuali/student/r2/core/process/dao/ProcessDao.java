package org.kuali.student.r2.core.process.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.ProcessEntity;

import java.util.List;

public class ProcessDao extends GenericEntityDao<ProcessEntity> {

    public List<ProcessEntity> getByProcessTypeId(String processTypeId) {
        return em.createQuery("from ProcessEntity a where a.processType.id=:processTypeId").setParameter("processTypeId", processTypeId).getResultList();
    }

}
