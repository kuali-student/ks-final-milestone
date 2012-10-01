package org.kuali.student.r2.core.process.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.CheckEntity;

import java.util.List;

public class CheckDao extends GenericEntityDao<CheckEntity> {

    public List<CheckEntity> getByCheckType(String checkType) {
        return em.createQuery("from CheckEntity a where a.checkType=:checkType").setParameter("checkType", checkType).getResultList();
    }

    public List<CheckEntity> getByName(String name) {
        return em.createQuery("from CheckEntity a where a.name=:name").setParameter("name", name).getResultList();
    }
}
