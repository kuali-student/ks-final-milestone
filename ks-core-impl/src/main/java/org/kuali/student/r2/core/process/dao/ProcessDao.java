package org.kuali.student.r2.core.process.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.ProcessEntity;

import javax.persistence.Query;
import java.util.List;

public class ProcessDao extends GenericEntityDao<ProcessEntity> {

    public List<ProcessEntity> getByProcessTypeId(String processTypeId) {
        Query query = em.createNamedQuery("ProcessEntity.getByProcessTypeId");
        query.setParameter("processTypeId", processTypeId);
        return query.getResultList();
    }

}
