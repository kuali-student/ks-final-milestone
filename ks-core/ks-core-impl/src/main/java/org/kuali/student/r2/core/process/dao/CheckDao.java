package org.kuali.student.r2.core.process.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.CheckEntity;

import java.util.List;

public class CheckDao extends GenericEntityDao<CheckEntity> {

    public List<CheckEntity> getByCheckTypeId(String checkTypeId) {
        return em.createQuery("from CheckEntity a where a.checkType.id=:checkTypeId").setParameter("checkTypeId", checkTypeId).getResultList();
    }
}
