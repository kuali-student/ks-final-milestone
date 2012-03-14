package org.kuali.student.r2.core.class1.process.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.process.model.CheckEntity;

import java.util.List;

public class CheckDao extends GenericEntityDao<CheckEntity> {

    public List<CheckEntity> getByCheckTypeId(String checkTypeId) {
        return em.createQuery("from CheckEntity a where a.checkType.id=:checkTypeId").setParameter("checkTypeId", checkTypeId).getResultList();
    }
}
