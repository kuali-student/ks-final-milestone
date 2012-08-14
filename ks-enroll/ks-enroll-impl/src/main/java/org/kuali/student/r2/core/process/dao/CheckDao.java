package org.kuali.student.r2.core.process.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.CheckEntity;

import javax.persistence.Query;
import java.util.List;

public class CheckDao extends GenericEntityDao<CheckEntity> {

    public List<CheckEntity> getByCheckType(String checkType) {
        Query query = em.createNamedQuery("CheckEntity.getByCheckType");
        query.setParameter("checkType", checkType);
        return query.getResultList();
    }

    public List<CheckEntity> getByName(String name) {
        Query query = em.createNamedQuery("CheckEntity.getByName");
        query.setParameter("name", name);
        return query.getResultList();
    }
}
