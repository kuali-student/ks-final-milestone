package org.kuali.student.r2.core.class1.process.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.process.model.ProcessTypeEntity;

import java.util.List;

public class ProcessTypeDao extends GenericEntityDao<ProcessTypeEntity>{

    @SuppressWarnings("unchecked")
    public List<ProcessTypeEntity> findAll(String refObjectURI) {
        return em.createQuery("from ProcessTypeEntity process where process.refObjectURI =:refObjectURI").setParameter("refObjectURI", refObjectURI).getResultList();
    }

}
