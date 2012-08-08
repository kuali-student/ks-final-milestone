package org.kuali.student.r2.core.process.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.CheckTypeEntity;

import java.util.List;

public class CheckTypeDao extends GenericEntityDao<CheckTypeEntity>{

    @SuppressWarnings("unchecked")
    public List<CheckTypeEntity> findAll(String refObjectURI) {
        return em.createQuery("from CheckTypeEntity check where check.refObjectURI =:refObjectURI").setParameter("refObjectURI", refObjectURI).getResultList();
    }

}
