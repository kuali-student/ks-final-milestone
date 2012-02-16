package org.kuali.student.r2.core.class1.process.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.process.model.CheckTypeEntity;

import java.util.List;

public class CheckTypeDao extends GenericEntityDao<CheckTypeEntity>{

    @SuppressWarnings("unchecked")
    public List<CheckTypeEntity> findAll(String refObjectURI) {
        return em.createQuery("from CheckTypeEntity check where check.refObjectURI =:refObjectURI").setParameter("refObjectURI", refObjectURI).getResultList();
    }

}
